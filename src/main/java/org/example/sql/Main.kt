package org.example.sql

fun main() {
    var students = SQLManager.query()
    println("所有人")
    students.forEach { student ->
       // if(student.gender == SQLManager.Sex.MALE) {
            println(student)
        //}
    }

//    SQLManager.delete(13)
//    val newStudent = SQLManager.Student(
//        null,"小凯",SQLManager.Sex.MALE,6L,99L
//    )
//    SQLManager.insert(newStudent)

//    val preInsertStudents = listOf(
//        SQLManager.Student(null,"小凯",SQLManager.Sex.MALE,6L,99L),
//        SQLManager.Student(null,"小民",SQLManager.Sex.MALE,6L,99L),
//        SQLManager.Student(null,"小新",SQLManager.Sex.FEMALE,3L,90L),
//        SQLManager.Student(null,"小红",SQLManager.Sex.FEMALE,3L,90L)
//    )
//    SQLManager.insertStudents(preInsertStudents)
//    val deleteIds = mutableListOf<Long>()
//    for(i in 16 .. 23) {
//        deleteIds.add(i.toLong())
//    }

//    SQLManager.deleteStudents(deleteIds)

//    students = SQLManager.query()
//    println("所有人")
//    students.forEach { student ->
//       // if(student.gender == SQLManager.Sex.MALE) {
//            println(student)
//        //}
//    }
}
