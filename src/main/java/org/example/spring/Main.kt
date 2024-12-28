package org.example.spring

import org.springframework.context.annotation.AnnotationConfigApplicationContext

fun main() {
//    val context = ClassPathXmlApplicationContext("applicationContext.xml")
//    val studentBean = context.getBean("student", StudentBean::class.java)
    val context = AnnotationConfigApplicationContext(AppConfig::class.java)
//    val studentBean = context.getBean(StudentBean::class.java)
//    println("Student: $studentBean")
    val studentsService = context.getBean(StudentService::class.java)
    val students = studentsService.students1
    students.forEach { item ->
        println(item.toString())
    }
    println("*************************************************************************************")
    val students2 = studentsService.students2
    students2.forEach { item ->
        println(item.toString())
    }

//    studentsService.readFileAndPrint()

//    TextExtend().printStudentInfo()
}



