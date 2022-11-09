package top.homesoft.framework.rabbit.exception;

public class PlayBackMessageNotExistsException extends RuntimeException{
    public PlayBackMessageNotExistsException(){
        super("回放消息不存在");
    }
}
