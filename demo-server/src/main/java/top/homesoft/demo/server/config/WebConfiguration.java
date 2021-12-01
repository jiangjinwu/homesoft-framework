package top.homesoft.demo.server.config;


//import jodd.typeconverter.impl.LocalDateTimeConverter;
import com.jubaozan.c3.framework.service.plugins.serialize.LocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *     //http://t.zoukankan.com/Cassie-wang-p-11597794.html
 *     https://www.cnblogs.com/Cassie-wang/p/11597794.html
 */
public class WebConfiguration {

    @Autowired
    RequestMappingHandlerAdapter requestMappingHandlerAdapter;
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("");
    public void initEditable(){

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");
        ConfigurableWebBindingInitializer initializer =
                (ConfigurableWebBindingInitializer) requestMappingHandlerAdapter.getWebBindingInitializer();

        if(Objects.nonNull( initializer.getConversionService())){
            GenericConversionService genericConversionService = (GenericConversionService)initializer.getConversionService();
            genericConversionService.addConverter(String.class, LocalDateTime.class,new LocalDateTimeConverter(dateTimeFormatter));
        }
    }
}
