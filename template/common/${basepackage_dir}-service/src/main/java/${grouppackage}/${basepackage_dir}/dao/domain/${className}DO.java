<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
<#assign shortName = table.shortName>
package ${grouppackage}.${basepackage}.dao.mapper;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * ${table.tableAlias}
 *
 * @author jjw
 * @version 1.0,
 *@data {DATE}
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("${table.sqlName}")
@ApiModel(value = "${table.tableAlias}", description = "")
public class ${className}DO implements Serializable{

    <#list table.columns as column>
    @ApiModelProperty(value = "${column.columnAlias}")
    <#if column.sqlTypeName=='JSON'>
     @TableField(typeHandler = FastjsonTypeHandler.class)
    </#if>
    private ${column.simpleJavaType} ${column.columnNameLower};

    </#list>
}
