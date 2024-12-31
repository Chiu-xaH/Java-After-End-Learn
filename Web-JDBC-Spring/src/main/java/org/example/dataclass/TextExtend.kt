package org.example.dataclass

//父类 扩展数据类的用法
abstract class FatherBean {
    abstract val name: String
    abstract val age: Int
    abstract val sexIsMan: Boolean
}

//子类继承
data class SonBean(
    val grade: String,
    val studentID : String,
    override val sexIsMan: Boolean,
    override val name: String,
    override val age: Int
) : FatherBean()

class TextExtend() {
    val student = SonBean(
        grade = "100",
        studentID = "2023218ABC",
        sexIsMan = true,
        name = "小赵",
        age = 19
    )
    fun printStudentInfo() {
        println(
            student.toString()
        )
    }
}