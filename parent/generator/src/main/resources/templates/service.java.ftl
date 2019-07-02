package ${package.Service};

import ${package.ReqDto}.${table.reqDtoName};
import ${package.RespDto}.${table.respDtoName};
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} {

    int add(${table.reqDtoName} request);

    int update(${table.reqDtoName} request);

    int deleteById(String id);

    int batchDelete(List<String> id);

    ${table.respDtoName} findById(String id);

    List<${table.respDtoName}> query(${table.reqDtoName} request);

}
</#if>
