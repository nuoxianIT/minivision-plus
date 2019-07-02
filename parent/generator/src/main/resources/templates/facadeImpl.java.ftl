package ${package.FacadeImpl};

import ${package.Facade}.${table.facadeName};
import ${package.MainService}.${table.mainServiceName};
import ${package.ReqDto}.${table.reqDtoName};
import ${package.RespDto}.${table.respDtoName};
import com.minivision.common.framework.facade.FacadeResp;
import com.minivision.common.framework.facade.FacadeResps;
import com.minivision.common.framework.facade.exception.BusinessException;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@Component
public class ${table.facadeImplName} implements ${table.facadeName} {
    
    @Autowired
    private ${table.mainServiceName} service;

    @Override
    public FacadeResp<Integer> add(${table.reqDtoName} request) {
      try {
        return FacadeResps.newSuccessResp(service.add(request));
      } catch (BusinessException e) {
        return FacadeResps.newResp(e.getCode());
      } catch (Exception e) {
        return FacadeResps.newErrorResp();
      }
    }

    @Override
    public FacadeResp<Integer> update(${table.reqDtoName} request) {
        try {
           return FacadeResps.newSuccessResp(service.update(request));
        } catch (BusinessException e) {
           return FacadeResps.newResp(e.getCode());
        } catch (Exception e) {
           return FacadeResps.newErrorResp();
        }
    }

    @Override
    public FacadeResp<Integer> deleteById(String id) {
        try {
            return FacadeResps.newSuccessResp(service.deleteById(id));
        } catch (BusinessException e) {
            return FacadeResps.newResp(e.getCode());
        } catch (Exception e) {
            return FacadeResps.newErrorResp();
        }
    }

    @Override
    public FacadeResp<Integer> batchDelete(List<String> id) {
        try {
            return FacadeResps.newSuccessResp(service.batchDelete(id));
        } catch (BusinessException e) {
            return FacadeResps.newResp(e.getCode());
        } catch (Exception e) {
            return FacadeResps.newErrorResp();
        }
    }

    @Override
    public FacadeResp<${table.respDtoName}> findById(String id) {
        try {
            return FacadeResps.newSuccessResp(service.findById(id));
        } catch (BusinessException e) {
            return FacadeResps.newResp(e.getCode());
        } catch (Exception e) {
            return FacadeResps.newErrorResp();
        }
    }

    @Override
    public FacadeResp<List<${table.respDtoName}>> query(${table.reqDtoName} query) {
        try {
            return FacadeResps.newSuccessResp(service.query(query));
        } catch (BusinessException e) {
            return FacadeResps.newResp(e.getCode());
        } catch (Exception e) {
            return FacadeResps.newErrorResp();
        }
    }

   
}
