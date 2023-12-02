package com.example.theatre.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    public void addResourceHandlers(ResourceHandlerRegistry registry)  {

//        String reportPath = uploadPath("./generated-reports");
        String reportPath = uploadPath("/generated-reports");



        registry.addResourceHandler("/generated-reports/**")
                .addResourceLocations("file:/"+reportPath+"/");

        System.out.println("Абсолютный путь reportPath: "+ reportPath);

        System.out.println("Возвращаем файл по пути /generated-reports/**");
    }

    // возвращает абсолютный путь к папке с отчетом
    private String uploadPath(String directory)  {
        Path uploadDirPath = Paths.get(directory);
//        if (!Files.exists(uploadDirPath)) {
//            try {
//                Files.createDirectories(uploadDirPath);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        File file = new File("D:\\generated-reports");
//        if (file.mkdirs()) {
//            System.out.println("Каталог 'D:/generated-reports' создан");
//        } else {
//            System.out.println("Не удалось создать катоалог 'D:/generated-reports'");
//        }
        return uploadDirPath.toFile().getAbsolutePath();
    }

}
