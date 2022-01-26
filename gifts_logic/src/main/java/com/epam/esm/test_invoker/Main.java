package com.epam.esm.test_invoker;

import com.epam.esm.bean.Certificate;
import com.epam.esm.config.SpringConfig;
import com.epam.esm.dao.CertificateDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        CertificateDAO dao = context.getBean(CertificateDAO.class);
        System.out.println(dao.findAll());
    }
}

