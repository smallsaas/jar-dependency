package com.jfeat.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author zxchengb
 * @date 2020-08-05
 */
public class FileUtils {
    /**
     * 依赖JAR包前缀锚点
     */
    public static final String LIB_JAR_DIR = "BOOT-INF/lib/";
    /**
     * JAR包通用后缀
     */
    public static final String JAR_SUFFIX = ".jar";
    /**
     * JAR包依赖输出文件后缀
     */
    public static final String DEPENDENCIES_OUT_PUT_JSON_FILE_PREFIX = "_dependencies_";
    /**
     * JAR包依赖输出文件后缀
     */
    public static final String DEPENDENCIES_OUT_PUT_JSON_FILE_SUFFIX = ".json";

    /**
     * 将内容写入目标文件中
     *
     * @param file    目标文件
     * @param content 写入内容
     * @return boolean
     */
    public static boolean writeContext(File file, String... content) {
        if (file != null && content != null) {
            try (BufferedWriter write = new BufferedWriter(new FileWriter(file))) {
                for (String s : content) {
                    write.write(s);
                    write.newLine();
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 读取目标文件内容并返回
     *
     * @param file 目标文件
     * @return java.com.jfeat.util.List<java.lang.String>
     */
    public static List<String> readContext(File file) {
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                List<String> list = new ArrayList<>();
                String temp;
                while ((temp = reader.readLine()) != null) {
                    list.add(temp);
                }
                return list;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 通过jar包文件解压，获取其依赖包(lib)并生成依赖集合
     *
     * @param jarFile 目标JAR包
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getDependencies(File jarFile) {
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