package com.jfeat.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfeat.util.DependencyUtils;
import com.jfeat.util.FileUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author zxchengb
 * @date 2020-08-05
 */
public class MainMethod {
    /**
     * 对比标识
     */
    private static final String COMPARE = "-c --compare";
    /**
     * 解析标识
     */
    private static final String PARSE = "-p --parse";
    /**
     * 版本标识
     */
    private static final String VERSION = "-v --version";

    /**
     * 选项正则表达式
     */
    private static final String REGEX = "(^-[cpv]$)|(^--[\\w]+$)";

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }
        String option = args[0];
        if (option != null && !option.isBlank() && option.matches(REGEX)) {
            if (COMPARE.contains(option) && args.length >= 3) {
                compare(args[1], args[2]);
            } else if (PARSE.contains(option)) {
                Arrays.stream(args).skip(1).forEach(MainMethod::parse);
            } else if (VERSION.contains(option)) {
                Properties properties = FileUtils.getProperties();
                if (properties != null) {
                    System.err.println(properties.getProperty("app.version"));
                }
            }
        }
    }

    /**
     * 根据JAR包路径解析并生成依赖结果
     *
     * @param filePath 目标JAR包路径
     */
    private static void parse(String filePath) {
        File jarFile = new File(filePath);
        if (jarFile.exists() && jarFile.isFile()) {
            System.out.println("Parsing " + jarFile.getName() + "...");
            List<String> dependencies = DependencyUtils.getDependenciesByJar(jarFile);
            dependencies = (dependencies != null && dependencies.isEmpty()) ? DependencyUtils.getDependenciesByPomModel(FileUtils.getPomModelByJar(jarFile)) :dependencies;
            if (dependencies != null && !dependencies.isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dependencies", dependencies);
                File jsonFile = new File(".", "parse_".concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_PREFIX).concat(String.valueOf(System.currentTimeMillis())).concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_SUFFIX));
                FileUtils.writeContextInJSON(jsonFile,jsonObject);
                System.out.println("Found " + dependencies.size() + " dependencies in lib.");
                System.out.println("SUCCESS output Dependencies JSON in file: " + jsonFile.getAbsolutePath() + "\n");
            } else {
                System.out.println("NOT Found Dependency JAR file.");
            }
        } else {
            System.err.println("NOT Found JAR File: " + filePath);
        }
    }

    /**
     * 根据两个Maven module查找对应的依赖并将对比结果输出为JSON文件
     *
     * @param module_1 目标模块1
     * @param module_2 目标模块2
     */
    private static void compare(String module_1, String module_2) {
        List<String> d1 = DependencyUtils.getDependenciesByPom(new File(module_1.concat(File.separator).concat(FileUtils.POM)));
        List<String> d2 = DependencyUtils.getDependenciesByPom(new File(module_2.concat(File.separator).concat(FileUtils.POM)));
        if(d1 != null && d2 != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sameDependencies", DependencyUtils.getSameDependencies(d1,d2));
            jsonObject.put("LeftMismatchDependencies",DependencyUtils.getDifferentDependencies(d2,d1));
            jsonObject.put("RightMismatchDependencies",DependencyUtils.getDifferentDependencies(d1,d2));
            File jsonFile = new File(".", "compare_".concat(String.valueOf(System.currentTimeMillis())).concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_SUFFIX));
            FileUtils.writeContextInJSON(jsonFile,jsonObject);
            System.out.println("SUCCESS output compare results in file: " + jsonFile.getAbsolutePath() + "\n");
        }else{
            System.err.println("ERROR.");
        }
    }
}