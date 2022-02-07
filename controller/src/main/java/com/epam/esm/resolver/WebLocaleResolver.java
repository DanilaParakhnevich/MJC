package com.epam.esm.resolver;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * The type Web locale resolver.
 */
public class WebLocaleResolver implements LocaleResolver {
    private static final List<Locale> LOCALES = Arrays.asList(Locale.US, new Locale("ru"));
    private String headerLang;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        headerLang = request.getHeader("lang");

        if (headerLang == null) {
            return Locale.US;
        }
        return LOCALES.stream()
                .filter(locale -> locale.getLanguage().equalsIgnoreCase(headerLang))
                .findFirst()
                .orElse(Locale.US);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Cannot change HTTP accept header - use a different locale resolution strategy");
    }
}