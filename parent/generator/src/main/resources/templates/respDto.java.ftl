package ${package.RespDto};

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
public class ${table.respDtoName} implements Serializable {

    private static final long serialVersionUID = 1L;
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as dtoField>

    <#if dtoField.keyFlag>
        <#assign keyPropertyName="${dtoField.propertyName}"/>
    </#if>

    <#if dtoField.comment!?length gt 0>
    /**
     * ${dtoField.comment}
     */
    </#if>
    <#-- 判断时间类型格式 -->
    <#if dtoField.propertyType == "LocalDateTime">
    private Date ${dtoField.propertyName};
    <#else>
    <#-- 普通字段 -->
    private ${dtoField.propertyType} ${dtoField.propertyName};
    </#if>
</#list>
<#------------  END 字段循环遍历  ---------->
}
