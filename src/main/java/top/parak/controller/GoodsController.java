package top.parak.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.parak.domain.User;
import top.parak.service.GoodsService;
import top.parak.vo.GoodsVO;

import java.util.List;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 商品控制器
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private GoodsService goodsService;

    /**
     * 商品列表
     * @param model UI
     * @return 商品列表页面
     */
    @RequestMapping("/list")
    public String list(Model model) {
        List<GoodsVO> goodsVOList = goodsService.getGoodsVOList();
        model.addAttribute("goodsList", goodsVOList);
        return "GoodsList";
    }

    /**
     * 商品详情
     * @param model   UI
     * @param user    当前用户信息
     * @param goodsId 商品ID
     * @return 商品详情页面
     */
    @RequestMapping("/detail/{goodsId}")
    public String detail(Model model, User user, @PathVariable("goodsId") long goodsId) {
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
        model.addAttribute("user", user);
        model.addAttribute("goods", goodsVO);
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        // log.info("秒杀状态：{}，倒计时：{}", seckillStatus, remainSeconds);
        return "GoodsDetail";
    }

}
