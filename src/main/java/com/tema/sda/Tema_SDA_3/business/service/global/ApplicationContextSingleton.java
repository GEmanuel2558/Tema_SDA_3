package com.tema.sda.Tema_SDA_3.business.service.global;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextSingleton {

    private static ApplicationContext context;

    public ApplicationContextSingleton(ApplicationContext contextInjected) {
        context = contextInjected;
    }

    public static ApplicationContext getContext() {
        return context;
    }

}
