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
package com.minivision.plus.generator.engine;

import com.minivision.plus.core.toolkit.CollectionUtils;
import com.minivision.plus.core.toolkit.StringPool;
import com.minivision.plus.core.toolkit.StringUtils;
import com.minivision.plus.extension.toolkit.PackageHelper;
import com.minivision.plus.generator.InjectionConfig;
import com.minivision.plus.generator.config.ConstVal;
import com.minivision.plus.generator.config.FileOutConfig;
import com.minivision.plus.generator.config.GlobalConfig;
import com.minivision.plus.generator.config.TemplateConfig;
import com.minivision.plus.generator.config.builder.ConfigBuilder;
import com.minivision.plus.generator.config.po.TableInfo;
import com.minivision.plus.generator.config.rules.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);

    private static final int BUFFER_SIZE = 2 * 1024;

    /**
     * 配置信息
     */
    private ConfigBuilder configBuilder;

    /**
     * 模板引擎初始化
     */
    public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }

    /**
     * 输出 java xml 文件
     */
    public AbstractTemplateEngine batchOutput() {
        try {
            List<TableInfo> tableInfoList = getConfigBuilder().getTableInfoList();
            for (TableInfo tableInfo : tableInfoList) {
                Map<String, Object> objectMap = getObjectMap(tableInfo);
                Map<String, String> pathInfo = getConfigBuilder().getPathInfo();
                TemplateConfig template = getConfigBuilder().getTemplate();
                // 自定义内容
                InjectionConfig injectionConfig = getConfigBuilder().getInjectionConfig();
                if (null != injectionConfig) {
                    injectionConfig.initMap();
                    objectMap.put("cfg", injectionConfig.getMap());
                    List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
                    if (CollectionUtils.isNotEmpty(focList)) {
                        for (FileOutConfig foc : focList) {
                            if (isCreate(FileType.OTHER, foc.outputFile(tableInfo))) {
                                writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
                            }
                        }
                    }
                }
                // Mp.java
                String entityName = tableInfo.getEntityName();
                if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
                    String entityFile = String.format((pathInfo.get(ConstVal.ENTITY_PATH) + File.separator + "%s" + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.ENTITY, entityFile)) {
                        writer(objectMap, templateFilePath(template.getEntity(getConfigBuilder().getGlobalConfig().isKotlin())), entityFile);
                    }
                }
                // MpMapper.java
                if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
                    String mapperFile = String
                            .format((pathInfo.get(ConstVal.MAPPER_PATH) + File.separator + tableInfo.getMapperName() + suffixJavaOrKt()), entityName);
                    if (isCreate(FileType.MAPPER, mapperFile)) {
                        writer(objectMap, templateFilePath(template.getMapper()), mapperFile);
                    }
                }
                // MpMapper.xml
                if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
                    String xmlFile = String
                            .format((pathInfo.get(ConstVal.XML_PATH) + File.separator + tableInfo.getXmlName() + ConstVal.XML_SUFFIX), entityName);
                    if (isCreate(FileType.XML, xmlFile)) {
                        writer(objectMap, templateFilePath(template.getXml()), xmlFile);
                    }
                }
                // IMpService.java
                if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
                    String serviceFile = String
                            .format((pathInfo.get(ConstVal.SERVICE_PATH) + File.separator + tableInfo.getServiceName() + suffixJavaOrKt()),
                                    entityName);
                    if (isCreate(FileType.SERVICE, serviceFile)) {
                        writer(objectMap, templateFilePath(template.getService()), serviceFile);
                    }
                }
                // MpServiceImpl.java
                if (null != tableInfo.getServiceImplName() && null != pathInfo.get(ConstVal.SERVICE_IMPL_PATH)) {
                    String implFile = String
                            .format((pathInfo.get(ConstVal.SERVICE_IMPL_PATH) + File.separator + tableInfo.getServiceImplName() + suffixJavaOrKt()),
                                    entityName);
                    if (isCreate(FileType.SERVICE_IMPL, implFile)) {
                        writer(objectMap, templateFilePath(template.getServiceImpl()), implFile);
                    }
                }
                // MpController.java
                if (null != tableInfo.getControllerName() && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
                    String controllerFile = String
                            .format((pathInfo.get(ConstVal.CONTROLLER_PATH) + File.separator + tableInfo.getControllerName() + suffixJavaOrKt()),
                                    entityName);
                    if (isCreate(FileType.CONTROLLER, controllerFile)) {
                        writer(objectMap, templateFilePath(template.getController()), controllerFile);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("无法创建文件，请检查配置信息！", e);
        }
        return this;
    }

    /**
     * 将模板转化成为文件
     *
     * @param objectMap    渲染对象 MAP 信息
     * @param templatePath 模板文件
     * @param outputFile   文件生成的目录
     */
    public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile) throws Exception;

    /**
     * 处理输出目录
     */
    public AbstractTemplateEngine mkdirs() {
        getConfigBuilder().getPathInfo().forEach((key, value) -> {
            File dir = new File(value);
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
                if (result) {
                    logger.debug("创建目录： [" + value + "]");
                }
            }
        });
        return this;
    }

    /**
     * 打开输出目录
     */
    public String open() {
        String outDir = getConfigBuilder().getGlobalConfig().getOutputDir();
        String value = getConfigBuilder().getPackageInfo().get("ModuleName");
        if (getConfigBuilder().getGlobalConfig().isOpen()
                && StringUtils.isNotEmpty(outDir)) {
            try {
                String osName = System.getProperty("os.name");
                logger.info("AbstractTemplateEngine System getProperty is ", osName);
                if (osName != null) {
                    String file = null;
                    // 指定输出路径
                    if (outDir.contains("/zip")) {
                        file = outDir.replaceAll("/zip", "/file");
                    }
                    logger.info("[AbstarctTemplateEngine]  outDir is {}", outDir);
                    OutputStream outputStream = new FileOutputStream(file + value + ".zip");
                    logger.info("[AbstarctTemplateEngine] outputStream is {}", file + value + ".zip");
                    // 创建zip压缩包
                    toZip(outDir, outputStream, true);
                    // 删掉普通文件夹
                    //deleteDir(new File(outDir));
                    logger.debug("文件输出目录:" + outDir);
                    return value + ".zip";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Description: 压缩文件方法 <br>
     *
     * @param srcDir           被压缩文件目录 <br>
     * @param out              压缩文件输出流 <br>
     * @param KeepDirStructure 是否保持原有的文件格式 <br>
     * @throws RuntimeException 异常 <br>
     * @author chenmin <br>
     */
    public static void toZip(String srcDir, OutputStream out, boolean KeepDirStructure)
            throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(out);
            File sourceFile = new File(srcDir);
            compress(sourceFile, zos, sourceFile.getName(), KeepDirStructure);
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) + " ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils", e);
        } finally {
            if (zos != null) {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Description: 递归压缩文件 <br>
     *
     * @param sourceFile       文件内容 <br>
     * @param zos              zip输入输出流 <br>
     * @param name             输出名字 <br>
     * @param KeepDirStructure 是否保持原有的路径格式 <br>
     * @throws Exception 异常 <br>
     * @author chenmin <br>
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name,
            boolean KeepDirStructure) throws Exception {
        byte[] buf = new byte[BUFFER_SIZE];
        if (sourceFile.isFile()) {
            // 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
            zos.putNextEntry(new ZipEntry(name));
            // copy文件到zip输出流中
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while ((len = in.read(buf)) != -1) {
                zos.write(buf, 0, len);
            }
            // Complete the entry
            zos.closeEntry();
            in.close();
        } else {
            File[] listFiles = sourceFile.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                // 需要保留原来的文件结构时,需要对空文件夹进行处理
                if (KeepDirStructure) {
                    // 空文件夹的处理
                    zos.putNextEntry(new ZipEntry(name + "/"));
                    // 没有文件，不需要文件的copy
                    zos.closeEntry();
                }
            } else {
                for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
                    if (KeepDirStructure) {
                        // 压缩包中保留原来的文件结构,斜杠则是保留原有包结构
                        compress(file, zos, name + "/" + file.getName(), KeepDirStructure);
                    } else {
                        // 压缩包中将所有的文件整到同个目录下
                        compress(file, zos, file.getName(), KeepDirStructure);
                    }
                }
            }
        }
    }

    /**
     * Description: 删除指定的目录以及目录下的所有子文件 <br>
     *
     * @param file 当前目录文件 <br>
     * @return 是否成功 <br>
     * @author chenmin <br>
     */
    public static boolean deleteDir(File file) {
        try {
            if (file.isFile()) {// 表示该文件不是文件夹
                file.delete();
            } else {
                // 首先得到当前的路径
                String[] childFilePaths = file.list();
                for (String childFilePath : childFilePaths) {
                    File childFile = new File(file.getAbsolutePath() + "/" + childFilePath);
                    // 递归删除
                    deleteDir(childFile);
                }
                file.delete();
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 渲染对象 MAP 信息
     *
     * @param tableInfo 表信息对象
     * @return ignore
     */
    public Map<String, Object> getObjectMap(TableInfo tableInfo) {
        Map<String, Object> objectMap = new HashMap<>(30);
        ConfigBuilder config = getConfigBuilder();
        if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
            objectMap.put("controllerMappingHyphenStyle", config.getStrategyConfig().isControllerMappingHyphenStyle());
            objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
        }
        objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
        objectMap.put("config", config);
        objectMap.put("package", config.getPackageInfo());
        GlobalConfig globalConfig = config.getGlobalConfig();
        objectMap.put("author", globalConfig.getAuthor());
        objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
        objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
        objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
        objectMap.put("activeRecord", globalConfig.isActiveRecord());
        objectMap.put("kotlin", globalConfig.isKotlin());
        objectMap.put("swagger2", globalConfig.isSwagger2());
        objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        objectMap.put("table", tableInfo);
        objectMap.put("enableCache", globalConfig.isEnableCache());
        objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
        objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
        objectMap.put("entity", tableInfo.getEntityName());
        objectMap.put("entitySerialVersionUID", config.getStrategyConfig().isEntitySerialVersionUID());
        objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
        objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
        objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
        objectMap.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
        objectMap.put("superEntityClass", getSuperClassName(config.getSuperEntityClass()));
        objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
        objectMap.put("superMapperClass", getSuperClassName(config.getSuperMapperClass()));
        objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
        objectMap.put("superServiceClass", getSuperClassName(config.getSuperServiceClass()));
        objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
        objectMap.put("superServiceImplClass", getSuperClassName(config.getSuperServiceImplClass()));
        objectMap.put("superControllerClassPackage", config.getSuperControllerClass());
        objectMap.put("superControllerClass", getSuperClassName(config.getSuperControllerClass()));
        return config.getInjectionConfig().prepareObjectMap(objectMap);
    }

    /**
     * 获取类名
     *
     * @param classPath ignore
     * @return ignore
     */
    private String getSuperClassName(String classPath) {
        if (StringUtils.isEmpty(classPath)) {
            return null;
        }
        return classPath.substring(classPath.lastIndexOf(StringPool.DOT) + 1);
    }

    /**
     * 模板真实文件路径
     *
     * @param filePath 文件路径
     * @return ignore
     */
    public abstract String templateFilePath(String filePath);

    /**
     * 检测文件是否存在
     *
     * @return 文件是否存在
     */
    protected boolean isCreate(FileType fileType, String filePath) {
        ConfigBuilder cb = getConfigBuilder();
        // 自定义判断
        InjectionConfig ic = cb.getInjectionConfig();
        if (null != ic && null != ic.getFileCreate()) {
            return ic.getFileCreate().isCreate(cb, fileType, filePath);
        }
        // 全局判断【默认】
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            PackageHelper.mkDir(file.getParentFile());
        }
        return !exist || getConfigBuilder().getGlobalConfig().isFileOverride();
    }

    /**
     * 文件后缀
     */
    protected String suffixJavaOrKt() {
        return getConfigBuilder().getGlobalConfig().isKotlin() ? ConstVal.KT_SUFFIX : ConstVal.JAVA_SUFFIX;
    }

    public ConfigBuilder getConfigBuilder() {
        return configBuilder;
    }

    public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        return this;
    }
}
