
<#include "/macro.include"/>
<#include "/java_copyright.include">
<#assign className = table.className>
<#assign classNameLower = className?uncap_first>
package ${basepackage}.modules.${aggregate}.domain.types;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ${className}Id implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long value;
}
