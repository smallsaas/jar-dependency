package com.jfeat.am.module.dependency.services.persistence.dto;

import org.springframework.validation.annotation.Validated;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Jar实体信息传输类
 * @author zxchengb
 * @date 2020-08-05
 */
@Data
@Accessors(chain = true)
public class JarDTO {
    /**
     * 应用标识ID
     */
    private String appId;
    /**
     * JAR包依赖
     */
    private List<String> dependencies;
}