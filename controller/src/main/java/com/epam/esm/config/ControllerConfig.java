package com.epam.esm.config;

import com.epam.esm.resolver.WebLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring config class for controller layer
 */
@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
public class ControllerConfig implements WebMvcConfigurer {
    /**
     * Message source resource bundle message source.
     *
     * @return the resource bundle message source
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource
                = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("locale.locale");
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;
    }
}
