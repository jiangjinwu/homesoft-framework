package top.homesoft.framework.rabbit.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MQProcessTemplate {

    protected final Log logger = LogFactory.getLog(this.getClass());

    public  void  execute(MQProcessCallback action){
        try {
            action.doProcess();
        }catch (Exception e){

        }
    }
}
