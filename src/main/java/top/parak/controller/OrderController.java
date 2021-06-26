package top.parak.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import top.parak.domain.OrderInfo;
import top.parak.domain.User;
import top.parak.response.CodeMessage;
import top.parak.response.ServerResponse;
import top.parak.service.GoodsService;
import top.parak.service.OrderService;
import top.parak.vo.GoodsVO;
import top.parak.vo.OrderDetailVO;

/**
 * @author KHighness
 * @since 2021-06-25
 * @apiNote 订单控制器
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/detail")
    @ResponseBody
    public ServerResponse<OrderDetailVO> detail(@RequestParam("orderId") long orderId) {
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (ObjectUtils.isEmpty(orderInfo)) {
            return new ServerResponse<>(CodeMessage.ORDER_NOT_EXISTS);
        }
        GoodsVO goodsVO = goodsService.getGoodsVOById(orderInfo.getGoodsId());
        return ServerResponse.success(new OrderDetailVO(goodsVO, orderInfo));
    }

}
