/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.minivision.plus.generator.config;

import com.minivision.plus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * 定义常量
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-31
 */
public interface ConstVal {

    String MODULE_NAME = "ModuleName";

    String ENTITY = "Entity";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAIN_SERVICE = "MainService";
    String MAIN_SERVICE_IMPL = "MainServiceImpl";
    String MAIN_SERVICE_KEY = "Service";
    String MAIN_SERVICE_IMPL_KEY = "ServiceImpl";
    String MAPPER = "Mapper";
    String REQDTO = "ReqDto";
    String RESPDTO = "RespDto";
    String FACADE = "Facade";
    String FACADE_IMPL = "FacadeImpl";
    String XML = "Xml";
    String CONTROLLER = "Controller";

    String ENTITY_PATH = "entity_path";
    String SERVICE_PATH = "service_path";
    String SERVICE_IMPL_PATH = "service_impl_path";
    String MAIN_SERVICE_PATH = "main_service_path";
    String MAIN_SERVICE_IMPL_PATH = "main_service_impl_path";
    String MAPPER_PATH = "mapper_path";
    String XML_PATH = "xml_path";
    String CONTROLLER_PATH = "controller_path";
    String REQDTO_PATH = "reqDto_path";
    String RESPDTO_PATH = "respDto_path";
    String FACADE_PATH = "facade_path";
    String FACADE_IMPL_PATH = "facade_impl_path";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String UNDERLINE = "_";

    String JAVA_SUFFIX = StringPool.DOT_JAVA;
    String KT_SUFFIX = ".kt";
    String XML_SUFFIX = ".xml";

    String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
    String TEMPLATE_ENTITY_KT = "/templates/entity.kt";
    String TEMPLATE_MAPPER = "/templates/mapper.java";
    String TEMPLATE_XML = "/templates/mapper.xml";
    String TEMPLATE_SERVICE = "/templates/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    String TEMPLATE_MAIN_SERVICE = "/templates/mainService.java";
    String TEMPLATE_MAIN_SERVICE_IMPL = "/templates/mainServiceImpl.java";
    String TEMPLATE_CONTROLLER = "/templates/controller.java";
    String TEMPLATE_FACADE = "/templates/facade.java";
    String TEMPLATE_FACADE_IMPL = "/templates/facadeImpl.java";
    String TEMPLATE_REQDTO = "/templates/reqDto.java";
    String TEMPLATE_RESPDTO = "/templates/respDto.java";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "com.minivision.common.mapper.IMapper";
    String SUPER_SERVICE_CLASS = "com.minivision.plus.extension.service.Service";
    String SUPER_SERVICE_IMPL_CLASS = "com.minivision.plus.extension.service.impl.ServiceImpl";

}
