package com.jfeat.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfeat.util.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * @author zxchengb
 * @date 2020-08-05
 */
public class ParseJarDependencyMain {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: parse-jar-with-dependencies.jar <JAR NAME>");
            System.out.println("e.g. parse-jar-with-dependencies.jar test");
            return;
        }
        Arrays.stream(args).forEach(ParseJarDependencyMain::parse);
    }

    /**
     * 根据文件名称解析并生成依赖
     *
     * @param fileName 目标文件名称
     */
    private static void parse(String fileName) {
        File jarFile = new File(".", (fileName.endsWith(FileUtils.JAR_SUFFIX) ? fileName : fileName.concat(FileUtils.JAR_SUFFIX)));
        if (jarFile.exists() && jarFile.isFile()) {
            System.out.println("Parsing " + jarFile.getName() + "...");
            final List<String> dependencies = FileUtils.getDependencies(jarFile);
            if (dependencies != null && !dependencies.isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dependencies", dependencies);
                File jsonFile = new File(".", jarFile.getName().replaceAll(".jar$", "").concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_PREFIX).concat(String.valueOf(System.currentTimeMillis())).concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_SUFFIX));
                FileUtils.writeContext(jsonFile, JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
                System.out.println("Found " + dependencies.size() + " dependencies in lib.");
                System.out.println("SUCCESS OUTPUT Dependencies JSON in file: " + jsonFile.getName() + "\n");
            } else {
                System.out.println("NOT Found Dependency JAR file.");
            }
        } else {
            System.err.println("NOT Found JAR File: " + fileName);
        }
    }
}