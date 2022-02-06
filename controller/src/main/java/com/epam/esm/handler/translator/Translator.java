package com.epam.esm.handler.translator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * The type Translator.
 */
@Component
@Scope(scopeName = "singleton")
public class Translator {
    /**
     * The Message source.
     */
    @Autowired
    ResourceBundleMessageSource messageSource;

    /**
     * Translate string.
     *
     * @param errorCode the error code
     * @return the string
     */
    public String translate(String errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(errorCode, null, locale);
    }

    /**
     * Sets message source.
     *
     * @param messageSource the message source
     */
    public void setMessageSource(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
