package com.epam.esm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{
                ControllerConfig.class,
                DaoConfig.class,
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}