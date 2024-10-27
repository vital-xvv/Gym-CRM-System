package com.epam.vital.gym_crm;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "com.epam.vital.gym_crm")
@PropertySource("classpath:application.properties")
public class GymCrmApplicationTests {
    protected static final ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(GymCrmApplication.class);
}
