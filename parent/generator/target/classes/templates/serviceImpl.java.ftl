package ${package.ServiceImpl};

import ${package.Facade}.${table.facadeName};
import ${package.Service}.${table.serviceName};
import ${package.ReqDto}.${table.reqDtoName};
import ${package.RespDto}.${table.respDtoName};
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import java.util.List;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${table.serviceImplName} implements ${table.serviceName} {

   @Reference
   private ${table.facadeName} facade;

   @Override
    public int add(${table.reqDtoName} request) {
        return facade.add(request).getData();
    }

    @Override
    public int update(${table.reqDtoName} request) {
        return facade.update(request).getData();
    }

    @Override
    public int deleteById(String id) {
        return facade.deleteById(id).getData();
    }

    @Override
    public int batchDelete(List<String> id) {
    return facade.batchDelete(id).getData();
    }

    @Override
    public ${table.respDtoName} findById(String id) {
    return facade.findById(id).getData();
    }

    @Override
    public List<${table.respDtoName}> query(${table.reqDtoName} request) {
        return facade.query(request).getData();
    }

}
