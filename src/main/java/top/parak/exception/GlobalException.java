package top.parak.exception;

import top.parak.response.CodeMessage;

/**
 * @author KHighness
 * @since 2021-06-21
 * @apiNote 全局异常
 */
public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = -3082602955725158929L;

    private CodeMessage codeMessage;

    public GlobalException(CodeMessage codeMessage) {
        this.codeMessage = codeMessage;
    }

    public CodeMessage getCodeMessage() {
        return codeMessage;
    }
}
