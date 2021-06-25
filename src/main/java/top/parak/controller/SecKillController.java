package top.parak.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.response.CodeMessage;
import top.parak.response.ServerResponse;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.service.SeckillService;
import top.parak.service.UserService;
import top.parak.vo.GoodsVO;

/**
 * @author KHighness
 * @since 2021-06-22
 * @apiNote 秒杀控制器
 */
@Controller
@RequestMapping("/seckill")
public class SecKillController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SeckillService seckillService;

    /**
     * 购买
     * @param model    UI
     * @param user     当前用户
     * @param goodsId  商品ID
     * @return 成功则跳转到订单详情页面，失败则跳转到秒杀失败页面
     */
//    @PostMapping("/buy")
    public String buy(Model model, User user, @RequestParam("goodsId") long goodsId) {

        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount == 0) { // 库存不足
            model.addAttribute("errormsg", CodeMessage.SEC_KILL_OVER);
            return "SeckillFail";
        }
        SecKillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (!ObjectUtils.isEmpty(order)) { // 重复秒杀
            model.addAttribute("errormsg", CodeMessage.REPEATE_CLICK);
            return "SeckillFail";
        }
        OrderInfo orderInfo = seckillService.kill(user, goods);
        model.addAttribute("user", user);
        model.addAttribute("goods", goods);
        model.addAttribute("orderInfo", orderInfo);
        return "OrderDetail";
    }

    /**
     * 购买
     * @param model    UI
     * @param user     当前用户
     * @param goodsId  商品ID
     * @return 成功则跳转到订单详情页面，失败则跳转到秒杀失败页面
     */
    @PostMapping("/buy")
    @ResponseBody
    public ServerResponse<OrderInfo> buy2(Model model, User user, @RequestParam("goodsId") long goodsId) {
        GoodsVO goods = goodsService.getGoodsVOById(goodsId);
        Integer stockCount = goods.getStockCount();
        if (stockCount == 0) { // 库存不足
            return new ServerResponse<>(CodeMessage.SEC_KILL_OVER);
        }
        SecKillOrder order = orderService.getSeckillOrderByUserIdAndGoodsId(user.getId(), goodsId);
        if (!ObjectUtils.isEmpty(order)) { // 重复秒杀
            return new ServerResponse<>(CodeMessage.REPEATE_CLICK);
        }
        OrderInfo orderInfo = seckillService.kill(user, goods);
        log.info("秒杀成功 => [用户电话: {}, 商品名称: {}]", user.getMobile(), goods.getGoodsName());
        return ServerResponse.success(orderInfo);
    }

}
