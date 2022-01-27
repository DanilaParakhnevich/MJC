package com.epam.esm;

import com.epam.esm.config.DaoConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Invoker {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoConfig.class);
        System.out.println(((TagDAO)context.getBean(TagDAO.class)).findAll());
    }
}
