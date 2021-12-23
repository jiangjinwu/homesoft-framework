package top.homesoft.framework.http;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;


@Data
public class ResultHolder<T>  implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(ResultHolder.class);
    private static final long serialVersionUID = 9043010987931787261L;
    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "message";
    private static final String SUCCESS_MESSAGE = "操作成功";
    private static final String FAILURE_MESSAGE = "操作失败";
    private static final String SUCCESS_CODE = "0000";
    @ApiModelProperty(
            value = "返回码, 0-成功 其它-失败",
            example = "0"
    )
    private String code;

    private String rid;
    @ApiModelProperty("错误信息")
    private String message;
    @ApiModelProperty("上下文信息")
    private String description;
    @ApiModelProperty("返回内容，成功时可能没有body")
    private T body;
    @ApiModelProperty("服务器时间戳")
    private Integer timestamp;
    @ApiModelProperty("重定向地址")
    private String redirect;
    private Object links;
    private Map<String, String> environments;
    private List<String> messages;

    public ResultHolder() {
    }

    public ResultHolder(String code, String message, T body, String description) {
        this.code = code;
        this.message = message;
        this.body = body;
        this.description = description;
        this.timestamp = (int)(System.currentTimeMillis() / 1000L);
        if (SUCCESS_CODE.equals(code)) {
            logger.error("{}", JSON.toJSONString(this));
        } else if (logger.isTraceEnabled()) {
            logger.trace("Body->{}", body);
        }
        this.rid = MDC.get("x-request-id");
    }

    public ResultHolder(String status, String message) {
        this(status, message,  null, (String)null);
    }

    public ResultHolder(T body) {
        this(SUCCESS_CODE, "操作成功", body, (String)null);
    }

    public static <T> ResultHolder<T> create(T body, boolean isSuccess, String errorCode) {
        return isSuccess ? new ResultHolder(SUCCESS_CODE, "操作成功", body, (String)null) : new ResultHolder(errorCode, "操作失败", body, (String)null);
    }

    public static <P> ResultHolder<P> error(String error, String message) {
        return new ResultHolder(error, message);
    }

    public static <P> ResultHolder<P> error(String error, String message, String extra) {
        return new ResultHolder(error, message, (Object)null, extra);
    }

    public ResultHolder<T> body(T body) {
        this.setBody(body);
        return this;
    }

    public ResultHolder<T> code(String code) {
        this.setCode(code);
        return this;
    }

    public ResultHolder<T> message(String message) {
        this.setMessage(message);
        return this;
    }

    public ResultHolder<T> description(String description) {
        this.setDescription(description);
        return this;
    }

    /**
     *
     * @param body 包体数据
     * @param <T> 包体类型
     * @return 是否成功
     */
    public static <T> ResultHolder<T> success(T body) {
        return new ResultHolder(body);
    }

    public boolean success() {
        return SUCCESS_CODE.equals(this.getCode());
    }



    public <R> Optional<R> map(Function<T, R> func) {
        if (!this.success()) {
            logger.error("{}", JSON.toJSONString(this));
        }

        return Optional.ofNullable(this.body).filter((bd) -> {
            return this.success();
        }).map(func);
    }

    public void peek(Consumer<T> consumer) {
        Optional.ofNullable(this.body).filter((bd) -> {
            return this.success();
        }).ifPresent(consumer);
    }
}
