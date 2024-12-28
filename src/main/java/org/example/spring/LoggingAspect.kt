package org.example.spring

import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.lang.Exception

//@Aspect
//@Component
class LoggingAspect {
//    // 在执行StudentService的每个方法前执行:
//    @Before("execution(* org.example.spring.StudentService.*(..))")
    fun doCheck() {
        println("[监控]")
    }
}