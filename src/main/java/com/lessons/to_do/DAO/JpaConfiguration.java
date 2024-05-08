package com.lessons.to_do.DAO;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@RequiredArgsConstructor
public class JpaConfiguration {
    private final DataSource dataSource;



    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.lessons.to_do.models");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

//    @Bean
//    public SchemaManager getSchemaManager(){
//        SchemaManager schemaManager = new SchemaManager() {
//            @Override
//            public void create(boolean createSchemas) {
//
//            }
//
//            @Override
//            public void drop(boolean dropSchemas) {
//
//            }
//
//            @Override
//            public void validate() throws SchemaValidationException {
//
//            }
//
//            @Override
//            public void truncate() {
//
//            }
//        }
//    }
}
