package ${package.MainServiceImpl};

import org.springframework.beans.factory.annotation.Autowired;
import ${package.MainService}.${table.mainServiceName};
import ${package.ReqDto}.${table.reqDtoName};
import ${package.Mapper}.${table.mapperName};
import ${package.RespDto}.${table.respDtoName};
import ${package.Entity}.${entity};
import org.springframework.stereotype.Service;
import com.minivision.common.framework.facade.exception.BusinessException;
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
public class ${table.mainServiceImplName} implements ${table.mainServiceName} {

   @Autowired
   private ${table.mapperName} mapper;

    @Override
    public int add(${table.reqDtoName} request) throws BusinessException {
        return mapper.insert(toEntity(request));
    }

    @Override
    public int update(${table.reqDtoName} request) throws BusinessException {
        return mapper.updateByPrimaryKeySelective(toEntity(request));
    }

    @Override
    public int deleteById(String id) throws BusinessException {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int batchDelete(List<String> id) throws BusinessException {
    Example example = new Example(${table.reqDtoName}.class);
    example.createCriteria().andIn("device_id", id);

    return mapper.deleteByExample(example);
    }

    @Override
    public ${table.respDtoName} findById(String id) throws BusinessException {
    return toDto(mapper.selectByPrimaryKey(id));
    }

    @Override
    public List<${table.respDtoName}> query(${table.reqDtoName} request) throws BusinessException {
        return toDto(mapper.select(toEntity(request)));
    }

    //TODO 抽象成公共方法
    private Device toEntity(DeviceReqDTO request) {
        ${entity} entity = new ${entity}();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    //TODO 抽象成公共方法
    private ${table.respDtoName} toDto(${entity} entity) {
        ${table.respDtoName} respDto = new ${table.respDtoName}();
        BeanUtils.copyProperties(entity, respDto);
        return respDto;
    }

    //TODO 抽象成公共方法
    private List<${table.respDtoName}> toDto(List<${entity}> list) {
       return list.stream().map(n -> {
            ${table.respDtoName} respDto = new ${table.respDtoName}();
            BeanUtils.copyProperties(n, respDto);
            return respDto;
       }).collect(Collectors.toList());
    }

}
