package org.example.spring

import org.springframework.beans.factory.FactoryBean

class StudentFactoryBean(private val studentType : Int) : FactoryBean<List<StudentBean>> {
    override fun getObject():  List<StudentBean> {
        return when(studentType) {
            1 -> listOf(
                StudentBean("小赵", "2023218ABC", "计算机与信息学院", "计算机科学与技术"),
                StudentBean("小刘", "2023218ABD", "计算机与信息学院", "计算机科学与技术"),
                StudentBean("小王", "2023218ACD", "计算机与信息学院", "计算机科学与技术"),
                StudentBean("小李", "2023218ADB", "计算机与信息学院", "计算机科学与技术")
            )
            2 -> listOf(
                StudentBean("小孙", "2023217ABC", "计算机与信息学院", "智能科学与技术"),
                StudentBean("小明", "2023217ABD", "计算机与信息学院", "智能科学与技术"),
                StudentBean("小红", "2023217ACD", "计算机与信息学院", "智能科学与技术"),
                StudentBean("小黑", "2023217ADB", "计算机与信息学院", "智能科学与技术")
            )
            else -> emptyList()
        }
    }

    override fun getObjectType(): Class<*> {
        return List::class.java
    }

    override fun isSingleton(): Boolean {
        return false
    }
}