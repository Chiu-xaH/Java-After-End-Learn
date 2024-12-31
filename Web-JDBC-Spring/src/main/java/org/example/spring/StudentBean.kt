package org.example.spring

import org.springframework.stereotype.Component

//@Component 使用此注解 只能构建一个
data class StudentBean(
    val name : String,
    val studentId : String,
    val school : String,
    val major : String
)
