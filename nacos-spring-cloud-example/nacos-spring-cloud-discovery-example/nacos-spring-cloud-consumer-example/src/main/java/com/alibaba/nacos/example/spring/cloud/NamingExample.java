package com.alibaba.nacos.example.spring.cloud;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;

import java.util.Properties;

public class NamingExample {

    public static void main(String[] args) throws NacosException, InterruptedException {

        Properties properties = new Properties();
        properties.setProperty("serverAddr", "192.168.1.9:8848");
        properties.setProperty("namespace", "public");

        NamingService naming = NamingFactory.createNamingService(properties);

        naming.registerInstance("service-provider", "11.11.11.11", 8888, "TEST1");

        naming.registerInstance("service-provider", "2.2.2.2", 9999, "DEFAULT");

        System.out.println(naming.getAllInstances("service-provider"));

        naming.deregisterInstance("service-provider", "2.2.2.2", 9999, "DEFAULT");

        System.out.println(naming.getAllInstances("service-provider"));

        naming.subscribe("service-provider", new com.alibaba.nacos.api.naming.listener.EventListener() {
            @Override
            public void onEvent(com.alibaba.nacos.api.naming.listener.Event event) {
                System.out.println(((com.alibaba.nacos.api.naming.listener.NamingEvent)event).getServiceName());
                System.out.println(((com.alibaba.nacos.api.naming.listener.NamingEvent)event).getInstances());
            }
        });

        Thread.sleep(3600 * 1000);
    }
}