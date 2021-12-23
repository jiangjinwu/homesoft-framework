package top.homesoft.framework.log.domain.systemlog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.Map;

@Data
@TableName(value = "t_system_log")
public class SystemLogPO {
    private static final long serialVersionUID = 1L;
    public static final String TYPE_INFO = "info";
    public static final String TYPE_ERROR = "error";
    @TableId
    private Long logId;      //日志主键
    private String type;      //日志类型
    private String title;      //日志标题
    private String remoteAddr;      //请求地址
    private String requestUri;      //URI
    private String method;      //请求方式
    private String params;      //提交参数
    private String exception;      //异常
    private Date operateDate;      //开始时间
    private Long timeout;      //结束时间
    private Long userId;      //用户ID


    /**
     * 设置请求参数 // 使用buildRequestParams更好 可以获取请求体中的参数
     * @param paramMap
     */
    @Deprecated
    public void setMapToParams(Map<String, String[]> paramMap) {
        if (paramMap == null){
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>)paramMap).entrySet()){
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append( paramValue);
           // params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        this.params = params.toString();
    }



    @Override
    public String toString() {
        return "Log [logId=" + logId + ", type=" + type + ", title=" + title
                + ", remoteAddr=" + remoteAddr + ", requestUri=" + requestUri
                + ", method=" + method + ", params=" + params + ", exception="
                + exception + ", operateDate=" + operateDate + ", timeout="
                + timeout + ", userId=" + userId + "]";
    }
}
