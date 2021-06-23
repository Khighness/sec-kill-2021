package top.parak.response;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 服务器返回消息
 * @param <T> 泛型
 */
public class ServerResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ServerResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ServerResponse(CodeMessage codeMessage) {
        this(codeMessage.getCode(), codeMessage.getMsg(), null);
    }

    public ServerResponse(CodeMessage codeMessage, T data) {
        this(codeMessage.getCode(), codeMessage.getMsg(), data);
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<T>(CodeMessage.SUCCESS, data);
    }

    public static <T> ServerResponse<T> error(T data) {
        return new ServerResponse<T>(CodeMessage.SERVER_ERROR, data);
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
