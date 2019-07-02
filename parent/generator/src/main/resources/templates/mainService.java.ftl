package ${package.MainService};

import ${package.ReqDto}.${table.reqDtoName};
import ${package.RespDto}.${table.respDtoName};
import com.minivision.common.framework.facade.exception.BusinessException;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.mainServiceName} {

   int add(${table.reqDtoName} request) throws BusinessException;

    int update(${table.reqDtoName} request) throws BusinessException;

    int deleteById(String id) throws BusinessException;

    int batchDelete(List<String> id) throws BusinessException;

    ${table.respDtoName} findById(String id) throws BusinessException;

    List<${table.respDtoName}> query(${table.reqDtoName} request) throws BusinessException;

}
