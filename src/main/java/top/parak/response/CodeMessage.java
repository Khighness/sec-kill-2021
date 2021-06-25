package top.parak.response;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 自定义状态码
 */
public enum  CodeMessage {
    /** 成功 */
    SUCCESS(0, "成功"),

    /** 通用错误 */
    SERVER_ERROR(500100, "服务器异常"),
    PARAM_VALIDATE_ERROR(500101, "参数校验异常"),
    REQUEST_METHOD_ERROR(500102, "请求方式非法"),
    ACCESS_LIMIT_REACHED(500103, "访问过于频繁"),

    /** 登录模块 */
    TOKEN_ERROR(500210, "Token不存在或者已失效，请重新登录"),
    PASSWORD_EMPTY(500211, "密码不能为空"),
    MOBILE_EMPTY(500212, "手机号不能为空"),
    MOBILE_ERROR(500213, "手机号码格式错误"),
    MOBILE_NOT_EXISTS(500214, "手机号不存在"),
    PASSWORD_WRONG(500215, "密码错误"),

    /** 商品模块 */


    /** 订单模块 */
    ORDER_NOT_EXISTS(500400, "订单不存在"),

    /** 秒杀模块  */
    SEC_KILL_OVER(500500, "秒杀活动已结束"),
    REPEATE_CLICK(500501, "不能重复秒杀"),
    SEC_KILL_FAILED(500502, "秒杀失败");

    private int code;
    private String msg;

    CodeMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return "CodeMessage [" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ']';
    }
}
