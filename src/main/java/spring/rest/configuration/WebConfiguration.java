package spring.rest.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "spring.rest") //==<context:component-scan base-package="spring.rest" />
@EnableWebMvc //== <aop:aspectj-autoproxy/>
@EnableTransactionManagement //==  <tx:annotation-driven transaction-manager="transactionManager" />
public class WebConfiguration {

    @Bean
    public DataSource webDataSource(){
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            dataSource.setJdbcUrl("jdbc:mysql://localhost:7777/staff_db?useSSL=false&amp;serverTimezone=UTC");
            dataSource.setUser("user_admin");
            dataSource.setPassword("123123DaDa");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }return dataSource;

    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(webDataSource());//за DataSource у нас отвечает метод webDataSource() поэтому в параметре пишем создание бина webDataSource()
        sessionFactory.setPackagesToScan("spring.rest.entity");

        Properties hibernateProperties= new Properties();
        hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true");
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());//sessionFactory is an object of LocalSessionFactoryBean, which implements FactoryBean<SessionFactory>, to get sessionFactory from FactoryBean we need to .getObject()
        return transactionManager;

    }






}
