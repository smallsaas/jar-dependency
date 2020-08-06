package com.jfeat.am.module.dependency.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

/**
 * 依赖工具类
 *
 * @author zxchengb
 * @date 2020-08-05
 */
public class DependencyUtils {
    /**
     * 依赖匹配正则表达式
     */
    private static final String DEPENDENCY_JAR_NAME_REGEX = "^(\\w+[-.]*\\w*)+\\.jar$";

    /**
     * 检测目标依赖名称是否规范
     * e.g.
     * test.jar(√)
     * ---.jar(×)
     * .jar(×)
     *
     * @param dependencyJarName 待检测的依赖名称
     * @return boolean
     */
    public static boolean isLegal(String dependencyJarName) {
        System.out.println(dependencyJarName);
        return StringUtils.isNotBlank(dependencyJarName) && dependencyJarName.matches(DEPENDENCY_JAR_NAME_REGEX);
    }

    /**
     * 获取不同依赖集合
     *
     * @param origin 源依赖集合
     * @param target 目标依赖集合
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getDifferentDependencies(List<String> origin, List<String> target) {
        return target.stream().filter(not(origin::contains)).collect(Collectors.toList());
    }

    /**
     * 获取相同依赖集合
     *
     * @param origin 源依赖集合
     * @param target 目标依赖集合
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getSameDependencies(List<String> origin, List<String> target) {
        return origin.stream().filter(target::contains).collect(Collectors.toList());
    }

    /**
     * 获取错误（格式错误）依赖集合
     *
     * @param target 目标依赖集合
     * @return java.util.List<java.lang.String>
     */
    public static List<String> getErrorDependencies(List<String> target) {
        return target.stream().filter(not(DependencyUtils::isLegal)).collect(Collectors.toList());
    }
}