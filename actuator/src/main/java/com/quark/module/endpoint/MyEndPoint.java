package com.quark.module.endpoint;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;

/**
 * Created by Administrator on 2018/2/27/027.
 */
public class MyEndPoint extends AbstractEndpoint<String> {

    public MyEndPoint(String id) {
        super(id);
    }

    public MyEndPoint(String id, boolean sensitive) {
        super(id, sensitive);
    }

    public MyEndPoint(String id, boolean sensitive, boolean enabled) {
        super(id, sensitive, enabled);
    }

    @Override
    public String invoke() {
        return "myEndPoint....";
    }
}
