package com.lessons.to_do;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories("com.lessons.to_do.repository")
public class ToDoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ToDoApplication.class, args);
    }


    @Bean("defaultCacheManager")
    @Primary
    public CacheManager defaultCacheManager() {
        return new ConcurrentMapCacheManager();
    }

//    @Bean
//    public SessionFactory getSessionFactory() {
//
//        Configuration configuration = new Configuration().configure("templates/hibernate.cfg.xml");
//        configuration.addAnnotatedClass(ToDo.class);
//        //configuration.addAnnotatedClass(Auto.class);
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//
//        return configuration.buildSessionFactory(builder.build());
//
//    }



}
