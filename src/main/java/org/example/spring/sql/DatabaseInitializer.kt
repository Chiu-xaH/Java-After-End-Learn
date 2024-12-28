package org.example.spring.sql

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

//建表
@Component
class DatabaseInitializer {
    @Autowired
    lateinit var  jdbcTemplate : JdbcTemplate

    @PostConstruct
    open fun init() {
        jdbcTemplate.update(
            "CREATE TABLE IF NOT EXISTS users (" //
                    + "id BIGINT IDENTITY NOT NULL PRIMARY KEY, " //
                    + "email VARCHAR(100) NOT NULL, " //
                    + "password VARCHAR(100) NOT NULL, " //
                    + "name VARCHAR(100) NOT NULL, " //
                    + "UNIQUE (email))"
        )
    }
}