package top.parak.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.parak.domain.OrderInfo;
import top.parak.domain.SecKillOrder;
import top.parak.domain.User;
import top.parak.response.CodeMessage;
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
    @PostMapping("/buy")
    public String bug(Model model, User user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
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
        model.addAttribute("goods", goods);
        model.addAttribute("orderInfo", orderInfo);
        return "OrderDetail";
    }

}
