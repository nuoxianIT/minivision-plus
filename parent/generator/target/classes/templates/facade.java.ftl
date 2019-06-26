package ${package.Service};

/**
 * <p>
 * ${table.comment!} Dubbo类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.facadeName} : ${superFacadeClass}<${entity}>
<#else>
public interface ${table.facadeName} {

}
</#if>
