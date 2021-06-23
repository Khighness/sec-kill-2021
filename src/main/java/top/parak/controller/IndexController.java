package top.parak.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.parak.response.ServerResponse;

/**
 * @author KHighness
 * @since 2021-06-20
 * @apiNote 测试控制器
 */
@Controller
public class IndexController {

    @GetMapping("/index")
    @ResponseBody
    public ServerResponse<String> index() {
        return ServerResponse.success("Hello");
    }

}
