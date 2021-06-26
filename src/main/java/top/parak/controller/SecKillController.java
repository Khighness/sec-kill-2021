package top.parak.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.parak.annotation.AccessLimit;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.rabbit.MQSender;
import top.parak.rabbit.SeckillMessage;
import top.parak.redis.GoodsKey;
import top.parak.redis.RedisService;
import top.parak.response.CodeMessage;
import top.parak.response.ServerResponse;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.service.SeckillService;
import top.parak.service.UserService;
import top.parak.vo.GoodsVO;
import top.parak.vo.UserVO;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀控制器
 */
@Controller
@RequestMapping("/seckill")
public class SecKillController implements InitializingBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;

    /**
     * 标记商品是否购完
     */
    private final Map<Long, Boolean> localGoodsOverMap = new HashMap<>();

    /**
     * 将商品库存预先放到redis中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        if (!ObjectUtils.isEmpty(goodsVOList)) {
            for (GoodsVO goodsVO : goodsVOList) {
                redisService.set(GoodsKey.getSeckillGoodsStock, "" + goodsVO.getId(), goodsVO.getStockCount());
                localGoodsOverMap.put(goodsVO.getId(), false);
            }
        }
    }

    /**
     * 验证码图片
     * @param response  响应
     * @param userVO   当前用户
     * @param goodId   商品ID
     * @return
     */
    @GetMapping("/verify")
    @ResponseBody
    public ServerResponse<String> verify(HttpServletResponse response, UserVO userVO,
                                         @RequestParam("goodsId") long goodId) {
        BufferedImage image = seckillService.createVerifyCode(userVO.getId(), goodId);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "JPG", outputStream);
            outputStream.flush();
            outputStream.close();
            return null;
        } catch (IOException e) {
            return new ServerResponse<>(CodeMessage.SEC_KILL_FAILED);
        }
    }

    /**
     * 获取秒杀地址
     * @param userVO     当前用户
     * @param goodsId    商品ID
     * @param verifyCode 验证码
     * @return 路径
     */
    @GetMapping("/path")
    @ResponseBody
    public ServerResponse<String> path(UserVO userVO, @RequestParam("goodsId") long goodsId,
                                       @RequestParam(value = "verifyCode") int verifyCode) {
        if (!seckillService.checkVerifyCode(userVO.getId(), goodsId, verifyCode))
            return new ServerResponse<>(CodeMessage.SEC_KILL_VERIFY_CODE_WRONG);
        String seckillPath = seckillService.createSeckillPath(userVO.getId(), goodsId);
        return ServerResponse.success(seckillPath);
    }

    /**
     * 购买
     * @param userVO   当前用户
     * @param goodsId  商品ID
     * @return 检验成功则返回等待状态
     */
    @AccessLimit(seconds = 10, maxCount = 2)
    @PostMapping("/{path}/buy")
    @ResponseBody
    public ServerResponse buy(UserVO userVO, @RequestParam("goodsId") long goodsId, @PathVariable("path") String path) {
        // 验证路径
        if (!seckillService.checkSeckillPath(userVO.getId(), goodsId, path))
            return new ServerResponse<>(CodeMessage.SEC_KILL_REQUEST_PATH_WRONG);
        // 是否购完
        if (localGoodsOverMap.get(goodsId))
            return new ServerResponse<>(CodeMessage.SEC_KILL_OVER);
        // 预减库存
        Long stock = redisService.decrement(GoodsKey.getSeckillGoodsStock, "" + goodsId);
        if (stock <= 0) {
            localGoodsOverMap.put(goodsId, true);
            return new ServerResponse<>(CodeMessage.SEC_KILL_FAILED);
        }
        // 检验重复
        SecKillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(userVO.getId(), goodsId);
        if (!ObjectUtils.isEmpty(order))
            return new ServerResponse<>(CodeMessage.REPEATE_CLICK);
        // 进入队列
        SeckillMessage sm = new SeckillMessage(userVO, goodsId);
        mqSender.sendSeckillMessage(sm);
        return ServerResponse.success("Waiting");
    }

    /**
     * 秒杀结果
     * @param userVO  当前用户
     * @param goodsId 商品ID
     * @return 秒杀状态
     */
    @GetMapping("/res")
    @ResponseBody
    public ServerResponse<Long> res(UserVO userVO, @RequestParam("goodsId") long goodsId) {
        long res = seckillService.getSeckillResult(userVO.getId(), goodsId);
        return ServerResponse.success(res);
    }

}
