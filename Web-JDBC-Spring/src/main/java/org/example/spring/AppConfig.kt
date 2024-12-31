package org.example.spring

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.sql.SQLManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

//相当于xml配置文件
@Configuration
@ComponentScan(basePackages = ["org.example"])
@PropertySource("jdbc.properties") //读取数据库
@EnableAspectJAutoProxy
@EnableTransactionManagement
open class AppConfig {
    @Bean("studentList1")
    @Primary //主Bean
    open fun studentList1() : StudentFactoryBean {
        return StudentFactoryBean(1)
    }

    @Bean("studentList2")
    open fun studentList2() : StudentFactoryBean {
        return StudentFactoryBean(2)
    }

    @Value("\${jdbc.url}")
    lateinit var jdbcUrl: String

    @Value("\${jdbc.username}")
    lateinit var jdbcUsername: String

    @Value("\${jdbc.password}")
    lateinit var jdbcPassword: String

    @Bean
    open fun createDataSource(): DataSource {
        val config = HikariConfig().apply {
            username = SQLManager.JDBC_USER
            jdbcUrl = SQLManager.JDBC_URL
            password = SQLManager.JDBC_PASSWORD
            addDataSourceProperty("connectionTimeout",1000)
            addDataSourceProperty("idleTimeout",60000) //空闲超时
            addDataSourceProperty("autoCommit","true") //自动提交
        }
        return HikariDataSource(config)
    }

    @Bean
    open fun createJdbcTemplate(@Autowired dataSource : DataSource): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }
}