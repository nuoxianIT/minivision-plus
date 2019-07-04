package ${package.Controller};


import org.springframework.web.bind.annotation.RequestMapping;
import com.minivision.common.framework.validation.exception.ValidateException;
import com.minivision.common.framework.validation.util.ValidateUtil;
import com.minivision.common.framework.web.ControllerResps;
import com.minivision.common.framework.web.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ${package.Service}.${table.serviceName};
import ${package.ReqDto}.${table.reqDtoName};
import java.util.List;

<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

/**
 * <p>
 * ${table.comment!} 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("/${package.ModuleName}")
@Slf4j
public class ${table.controllerName} {

    @Autowired
    private ${table.serviceName} service;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestBody ${table.reqDtoName} request) {
        try {
            ValidateUtil.validate(request);
        } catch (ValidateException ex) {
            return JsonUtil.getErrorJson(ex.getCode(), ex.getMsg());
        }
        int result = service.add(request);
        if (result > 0) {
            return ControllerResps.newSuccResp();
        } else {
            return ControllerResps.newFailResp();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestBody ${table.reqDtoName} request) {
        try {
            ValidateUtil.validate(request);
        } catch (ValidateException ex) {
            return JsonUtil.getErrorJson(ex.getCode(), ex.getMsg());
        }
        int result = service.update(request);
        if (result > 0) {
            return ControllerResps.newSuccResp();
        } else {
            return ControllerResps.newFailResp();
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestBody String id) {
        if (StringUtils.isEmpty(id)) {
            return ControllerResps.paramIllegalResp();
        }

        int result = service.deleteById(id);
        if (result > 0) {
            return ControllerResps.newSuccResp();
        } else {
            return ControllerResps.newFailResp();
        }
    }

    @RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
    public String batchDelete(@RequestBody List<String> id) {
    if (id == null || id.size() == 0) {
    return ControllerResps.paramIllegalResp();
    }

    int result = service.batchDelete(id);
    if (result > 0) {
    return ControllerResps.newSuccResp();
    } else {
    return ControllerResps.newFailResp();
    }
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public String find(@RequestBody String id) {
    return ControllerResps.newSuccResp(service.findById(id));
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String queryMember(@RequestBody ${table.reqDtoName} request) {
    return ControllerResps.newSuccResp(service.query(request));
    }

}
