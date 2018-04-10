package com.quark.module.runner;

import com.quark.module.conditional.MyConditional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * Created by ZhenpengLu on 2018/4/4.
 * 自定义applicationRunner  用于在afterRefresh（）中的callRunner（）
 * 自定义一些执行内容 如配置 、数据库连接。。
 *  note: 它不是通过
 *  @see org.springframework.core.io.support.SpringFactoriesLoader 加载的 所以不能通过spring.fatories配置方式实现
 */
@Component
@Order(2)
//@Conditional(MyConditional.class)
/**
 * 由自定义的
 * @see MyConditional#matches(ConditionContext, AnnotatedTypeMetadata) 判断该bean是否需要注册为bean
 * 此时MyApplicationRunner 不会执行 因为MyConditional 中match返回的是false
 */
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("MyApplicationRunner is executing....");
    }
}
