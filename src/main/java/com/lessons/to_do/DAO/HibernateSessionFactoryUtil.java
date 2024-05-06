package com.lessons.to_do.DAO;


import com.lessons.to_do.models.ToDo;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Component
public class HibernateSessionFactoryUtil {

//    @Bean
//    public SessionFactory getSessionFactory() {
//
//        Configuration configuration = new Configuration().configure();
//        configuration.addAnnotatedClass(ToDo.class);
//        //configuration.addAnnotatedClass(Auto.class);
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
//
//        return configuration.buildSessionFactory(builder.build());
//    }


}
