package com.restservice;

import com.restservice.auth.AuthenticationFilter;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
    public MyApplication() {
        packages("com.restservice");
        register(LoggingFilter.class);

        register(AuthenticationFilter.class);
    }
}
