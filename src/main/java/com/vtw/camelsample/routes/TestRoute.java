package com.vtw.camelsample.routes;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.stereotype.Component;

//@Component
public class TestRoute extends EndpointRouteBuilder {
    @Override
    public void configure() throws Exception {
        from(timer("test-001").period(3000))
        .log("Hello");
    }
}
