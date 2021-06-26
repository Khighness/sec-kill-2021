package top.parak.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import top.parak.annotation.AccessLimit;
import top.parak.redis.GoodsKey;
import top.parak.redis.RedisService;
import top.parak.response.ServerResponse;
import top.parak.service.GoodsService;
import top.parak.vo.GoodsDetailVO;
import top.parak.vo.GoodsVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 商品控制器
 */
@Controller
@RequestMapping("/goods")
public class GoodsController{
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 商品列表
     * <p>先从缓存中获取，不存在则手动渲染
     * @param request   请求
     * @param response  响应
     * @param model     UI
     * @return 商品列表页面
     */
    @RequestMapping(value = "/list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        String html = redisService.get(GoodsKey.getList, "", String.class);
        if (StringUtils.isNotBlank(html)) {
            return html;
        }
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        model.addAttribute("goodsList", goodsVOList);
        ISpringTemplateEngine templateEngine = thymeleafViewResolver.getTemplateEngine();
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = templateEngine.process("GoodsList", webContext);
        if (StringUtils.isNotBlank(html)) {
            redisService.set(GoodsKey.getList, "", html);
        }
        return html;
    }

    /**
     * 商品详情
     * @param goodsId 商品ID
     * @return 商品详情信息
     */
    @AccessLimit(seconds = 5, maxCount = 5)
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public ServerResponse<GoodsDetailVO> detail(@PathVariable("goodsId") long goodsId) {
        GoodsVO goodsVO = goodsService.getGoodsVOById(goodsId);
        long startTime = goodsVO.getStartDate().getTime();
        long endTime = goodsVO.getEndDate().getTime();
        long now = System.currentTimeMillis();
        // 秒杀活动状态，秒杀活动倒计时
        int seckillStatus = 0, remainSeconds = 0;
        if (now < startTime) { // 秒杀还未开始
            remainSeconds = (int) (startTime - now) / 1000;
        } else if (now > endTime) { // 秒杀已经结束
            seckillStatus = 2;
            remainSeconds = -1;
        } else { // 秒杀正在进行
            seckillStatus = 1;
        }
        log.info("秒杀状态 => [商品名称: {}, 秒杀状态: {}，倒计时: {}]", goodsVO.getGoodsName(), seckillStatus, remainSeconds);
        GoodsDetailVO goodsDetailVO = new GoodsDetailVO(goodsVO, seckillStatus, remainSeconds);
        return ServerResponse.success(goodsDetailVO);
    }

}
