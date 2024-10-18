package com.epam.vital.gym_crm;

import com.epam.vital.gym_crm.controller.FacadeController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("classpath:application.properties")
public class GymCrmApplication {
    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(GymCrmApplication.class);

    public static void main(String[] args) {
        FacadeController controller = applicationContext.getBean(FacadeController.class);
        System.out.println(controller.getAllTrainers());
    }
}
