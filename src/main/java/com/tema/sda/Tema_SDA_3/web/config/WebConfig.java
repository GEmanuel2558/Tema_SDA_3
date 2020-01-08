package com.tema.sda.Tema_SDA_3.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@EnableTransactionManagement
@EnableJpaRepositories("com.tema.sda.Tema_SDA_3.data.repository")
@Configuration
public class WebConfig implements WebMvcConfigurer {


}
