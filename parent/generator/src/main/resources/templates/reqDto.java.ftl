package ${package.ReqDto};

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 *  Description: ${table.comment!} 实体类
 *
 * @author ${author}
 * @CreateTime ${date}
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ${table.reqDtoName} implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field>
    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.comment!?length gt 0>
    /**
     * ${field.comment}
     */
    </#if>
    <#-- 判断时间类型格式 -->
    <#if field.propertyType == "LocalDateTime">
    private Date ${field.propertyName};
    <#else>
    <#-- 普通字段 -->
    private ${field.propertyType} ${field.propertyName};
    </#if>
</#list>
}
