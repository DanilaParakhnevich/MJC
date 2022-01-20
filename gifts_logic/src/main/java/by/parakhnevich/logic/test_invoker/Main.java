package by.parakhnevich.logic.test_invoker;

import by.parakhnevich.logic.bean.Certificate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Certificate certificate = (Certificate) context.getBean("certificate");
        Certificate certificate2 = (Certificate) context.getBean("certificate");
        System.out.println(certificate == certificate2);
    }
}
