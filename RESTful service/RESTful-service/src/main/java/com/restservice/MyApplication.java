package com.restservice;

import com.restservice.auth.AuthenticationFilter;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;

public class MyApplication extends ResourceConfig {
    public MyApplication() {
        //super(ServiceLL.class);
        packages("com.restservice");
        register(LoggingFilter.class);

        register(AuthenticationFilter.class);


        //register(GsonMessageBodyHandler.class);


        //Register Auth Filter here

    }
}
