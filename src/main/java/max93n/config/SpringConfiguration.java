package max93n.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import max93n.chart.c3.ChartC3ModelJson;
import max93n.chart.c3.model.*;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;


@Configuration
@ComponentScan("max93n")
@EnableTransactionManagement
@EnableJpaRepositories("max93n.repositories")
public class SpringConfiguration {


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource);
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProperties.put("hibernate.format_sql", "true");
        entityManagerFactory.setJpaProperties(jpaProperties);
        entityManagerFactory.setPackagesToScan("max93n");
        entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager(DataSource dataSource,
                                                    EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(
                entityManagerFactory);
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/expense_manager");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }


    @Bean
    public ChartC3ModelJson myChartJson() {
        ChartC3ModelJson myChartJson = new ChartC3ModelJson();
        myChartJson.setBindto("#chart");

        Data data = new Data();
        data.setType("bar");
        data.setColumns(new String[][]{
                {"Data", "40", "10", "30", "15"}
        });

        myChartJson.setData(data);
        return myChartJson;
    }

    @Bean
    public ChartC3ModelJson verticalBarChartJson() {
        ChartC3ModelJson chart = new ChartC3ModelJson();
        chart.setBindto("#chart");


        // data configuration
        Data data = new Data();
        data.setType("bar");

        Map<String, String> axes = new HashMap<>();
        axes.put("expense", "y");
        data.setAxes(axes);

        Map<String, String> colors = new HashMap<>();
        colors.put("expense", "#ff0000");
        data.setColors(colors);

        data.setLabels(true);
        chart.setData(data);

        data.setX("headers");

        //  bar configuration
        Bar bar = new Bar();
        Width width = new Width();
        width.setRatio(0.5);
        bar.setWidth(width);
        chart.setBar(bar);

        //axis configuration
        Axis axis = new Axis();
        X x = new X();
        x.setType("category");
        x.setShow(true);
        axis.setX(x);
        axis.setRotated(true);
        chart.setAxis(axis);

        //grid configuration
        Grid grid = new Grid();
        X xGrid = new X();
        xGrid.setShow(true);
        chart.setGrid(grid);

        //legend configuration
        Legend legend = new Legend();
        legend.setShow(false);
        chart.setLegend(legend);

        return chart;
    }


    @Bean
    public ChartC3ModelJson horizontalBarChartJson() {
        ChartC3ModelJson chart = new ChartC3ModelJson();
        chart.setBindto("#chart");

        Color color = new Color();
        color.setPattern(
                new String[] {
                        "#ee0000", "#00dd00"
                }
        );
        chart.setColor(color);

        // data configuration
        Data data = new Data();
        data.setType("bar");
        data.setLabels(true);
        chart.setData(data);
        data.setX("months");

        //  bar configuration
        Bar bar = new Bar();
        Width width = new Width();
        width.setRatio(0.5);
        bar.setWidth(width);
        chart.setBar(bar);

        //axis configuration
        Axis axis = new Axis();
        X x = new X();
        x.setType("category");
        x.setShow(true);
        axis.setX(x);
        axis.setRotated(false);
        chart.setAxis(axis);

        //grid configuration
        Grid grid = new Grid();
        X xGrid = new X();
        xGrid.setShow(true);
        chart.setGrid(grid);

        //legend configuration
        Legend legend = new Legend();
        legend.setShow(true);
        chart.setLegend(legend);

        return chart;
    }

}
