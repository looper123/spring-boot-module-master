package com.quark.module.conditional;


import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.ConfigurationCondition;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Created by ZhenpengLu on 2018/4/4.
 * 自定义bean注册条件
 */
public class MyConditional implements ConfigurationCondition {

    /**
     * 在什么阶段做判断
     * @see ConfigurationPhase
     * PARSE_CONFIGURATION 配置阶段
     * REGISTER_BEAN  注册bean阶段
     * @return
     */
    @Override
    public ConfigurationPhase getConfigurationPhase() {
        return null;
    }

//    判定条件
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//        if(...){
//            ....
//        }
        return false;
    }
}
