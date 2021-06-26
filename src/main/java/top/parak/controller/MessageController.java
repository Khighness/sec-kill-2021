package top.parak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.parak.rabbit.MQSender;
import top.parak.response.ServerResponse;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote MQ测试控制器
 */
@Controller
@RequestMapping("/index")
public class MessageController {

    @Autowired
    private MQSender mqSender;

    @GetMapping("/direct/{msg}")
    @ResponseBody
    public ServerResponse<String> direct(@PathVariable("msg") String msg) {
        mqSender.sendDirect(msg);
        return ServerResponse.success("Hello " + msg);
    }

    @GetMapping("/fanout/{msg}")
    @ResponseBody
    public ServerResponse<String> fanout(@PathVariable("msg") String msg) {
        mqSender.sendFanout(msg);
        return ServerResponse.success("Hello " + msg);
    }

    @GetMapping("/topic/{msg}")
    @ResponseBody
    public ServerResponse<String> topic(@PathVariable("msg") String msg) {
        mqSender.sendTopic(msg);
        return ServerResponse.success("Hello " + msg);
    }

    @GetMapping("/header/{msg}")
    @ResponseBody
    public ServerResponse<String> header(@PathVariable("msg") String msg) {
        mqSender.sendHeader(msg);
        return ServerResponse.success("Hello " + msg);
    }

}
