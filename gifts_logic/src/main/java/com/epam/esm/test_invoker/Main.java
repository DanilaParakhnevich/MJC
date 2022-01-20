package com.epam.esm.test_invoker;

import com.epam.esm.config.SpringConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println(new SpringConfig().dataSource());
    }
}
