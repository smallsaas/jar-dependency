package com.jfeat.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jfeat.util.FileUtils;

/**
 * @author zxchengb
 * @date 2020-08-05
 */
public class ParseJarDependency {
    /**
     * 键盘读取对象
     */
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Please input JAR File path :");
        String filePath = scanner.nextLine();
        File jarFile = new File(filePath);
        if (jarFile.exists() && jarFile.isFile() && filePath.endsWith(FileUtils.JAR_SUFFIX)) {
            final List<String> dependencies = getDependencies(jarFile);
            if (dependencies != null && !dependencies.isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dependencies", dependencies);
                String dependenciesJsonFilePath = jarFile.getParent().concat(File.separator).concat(jarFile.getName().replaceAll(".jar$", "").concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_PREFIX).concat(String.valueOf(System.currentTimeMillis()))).concat(FileUtils.DEPENDENCIES_OUT_PUT_JSON_FILE_SUFFIX);
                FileUtils.writeContext(new File(dependenciesJsonFilePath), JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat));
                System.out.println("\nFound " + dependencies.size() + " dependencies in lib.");
                System.out.println("\nOUT PUT Dependencies JSON in file: " + dependenciesJsonFilePath);
            } else {
                System.out.println("NOT Found Dependency JAR file.");
            }
        } else {
            System.err.println("NOT Found JAR File: " + filePath);
        }
    }

    /**
     * 通过jar包文件解压，获取其依赖包(lib)并生成依赖集合
     *
     * @param jarFile 目标JAR包
     * @return java.util.List<java.lang.String>
     */
    private static List<String> getDependencies(File jarFile) {
        List<String> dependencies = new ArrayList<>();
        // 不解压读取压缩包中的文件内容
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(jarFile))) {
            ZipEntry zipEntry;
            // 循环遍历压缩包内文件对象
            while ((zipEntry = zis.getNextEntry()) != null) {
                String name = zipEntry.getName();
                if (name.startsWith(FileUtils.LIB_JAR_DIR) && name.endsWith(FileUtils.JAR_SUFFIX)) {
                    dependencies.add(name.replaceFirst(FileUtils.LIB_JAR_DIR, ""));
                }
            }
            return dependencies;
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}