package com.quark.module.dubbo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.quark.module.dubbo.service.DemoService;

/**
 * Created by zebon lu on 2017/5/15.
 * 服务提供者 提供的具体功能类
 */
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"

)
public class DemoServiceImpl implements DemoService {


//    访问url测试   192.168.1.110:8080/sayHello?name='looper'
    public String sayHello(String name) {
        return "Hello " + name;
    }

}
