package ${package.Facade};

import com.minivision.common.framework.facade.FacadeResp;
import ${package.ReqDto}.${table.reqDtoName};
import ${package.RespDto}.${table.respDtoName};

import java.util.List;

/**
 * <p>
 * ${table.comment!} Dubbo服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.facadeName} {

    FacadeResp<Integer> add(${table.reqDtoName} request);

    FacadeResp<Integer> update(${table.reqDtoName} request);

    FacadeResp<Integer> deleteById(String id);

    FacadeResp<Integer> batchDelete(List<String> id);

    FacadeResp<${table.respDtoName}> findById(String id);

    FacadeResp<List<${table.respDtoName}>> query(${table.reqDtoName} query);

}
