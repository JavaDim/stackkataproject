package com.javamentor.qa.platform.webapp.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/registration").setViewName("regpage");
        registry.addViewController("/tag_page").setViewName("tagpage");
        registry.addViewController("/usersPage").setViewName("userspage");
    }
}
