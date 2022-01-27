package com.epam.esm.test_invoker;

import com.epam.esm.TagService;
import com.epam.esm.config.ServiceConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Invoker {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);
        TagService tagService = context.getBean(TagService.class);
        System.out.println(tagService.findAll());
    }
}
