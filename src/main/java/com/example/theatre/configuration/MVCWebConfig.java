package com.example.theatre.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//@EnableWebMvc
@Configuration
public class MVCWebConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login");
        registry.addViewController("/admin");
    }

    /*
     * Приложение должно разрешать запросы к нашим ссылкам в формате PDF,
     * используя строку /generated-reports/**.
     *
     * Передаем абсолютный путь, возвращаемый методом, методу addResourceLocations()
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String reportPath = uploadPath("./generated-reports");

        registry.addResourceHandler("/generated-reports/**")
                .addResourceLocations("file:/"+reportPath+"/");
    }

    // возвращает абсолютный путь к папке с отчетом
    private String uploadPath(String directory){
        Path uploadDirPath = Paths.get(directory);
        return uploadDirPath.toFile().getAbsolutePath();
    }

}
