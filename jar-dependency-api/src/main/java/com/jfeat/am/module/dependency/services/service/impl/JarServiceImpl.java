package com.jfeat.am.module.dependency.services.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jfeat.am.module.dependency.services.persistence.dao.JarMapper;
import com.jfeat.am.module.dependency.services.persistence.model.Jar;
import com.jfeat.am.module.dependency.services.service.JarService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxchengb
 * @since 2020-08-05
 */
@Service
public class JarServiceImpl extends ServiceImpl<JarMapper, Jar> implements JarService {
    /**
     * JAR实体映射对象
     */
    private JarMapper jarMapper;

    public JarServiceImpl(JarMapper jarMapper) {
        this.jarMapper = jarMapper;
    }

    /**
     * 根据应用标识ID查询JAR包详细记录
     *
     * @param appId 目标应用标识ID
     * @return com.jfeat.am.module.dependency.services.persistence.model.Jar
     */
    @Override
    public Jar selectByAppId(String appId) {
        return jarMapper.selectList(new EntityWrapper<Jar>().eq("app_id",appId)).stream().findFirst().orElse(null);
    }
}
