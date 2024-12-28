package org.example.sql

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.sql.SQLManager.Sex.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement


/*SELECT * FROM Students
+----+------+--------+-------+-------+
| id | name | gender | grade | score |
+----+------+--------+-------+-------+
|  1 | 小赵 |      1 |     1 |    88 |
|  2 | 小红 |      1 |     1 |    95 |
|  3 | 小军 |      0 |     1 |    93 |
|  4 | 小白 |      0 |     1 |   100 |
|  5 | 小牛 |      1 |     2 |    96 |
|  6 | 小兵 |      1 |     2 |    99 |
|  7 | 小强 |      0 |     2 |    86 |
|  8 | 小乔 |      0 |     2 |    79 |
|  9 | 小青 |      1 |     3 |    85 |
| 10 | 小王 |      1 |     3 |    90 |
| 11 | 小林 |      0 |     3 |    91 |
| 12 | 小贝 |      0 |     3 |    77 |
+----+------+--------+-------+-------+
*/


object SQLManager {
    enum class Sex {
        MALE,FEMALE,OTHER
    }
    data class Student(val id : Long? = null,val name : String,val gender : Sex,val grade : Long,val score : Long)
    //配置
    const val TABLE = "learn"
    const val JDBC_URL = "jdbc:mysql://localhost:3306/${TABLE}?useSSL=false&characterEncoding=utf8&allowPublicKeyRetrieval=true"
    const val JDBC_USER = "root"
    const val JDBC_PASSWORD = "050908"
    //SQL命令
    const val QUERY_SQL = "SELECT * FROM Students"
    const val INSERT_SQL = "INSERT INTO Students (name,gender,grade,score) VALUES (?,?,?,?)"
    const val DELETE_SQL = "DELETE FROM Students WHERE id=?"

    //连接SQL
    //private val conn: Connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
    /***连接池*****************************************************************************************/
    private val config = HikariConfig().apply {
        username = JDBC_USER
        jdbcUrl = JDBC_URL
        password = JDBC_PASSWORD
        addDataSourceProperty("connectionTimeout",1000)
        addDataSourceProperty("idleTimeout",60000) //空闲超时
        addDataSourceProperty("maximumPoolSize",10) //最大连接数
    }

    val dataSource = HikariDataSource(config)
    private val conn: Connection = dataSource.connection

    fun query(): List<Student> {
        val students = mutableListOf<Student>()
    //    DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD).use { conn ->
            conn.prepareStatement(QUERY_SQL).use { stmt ->
                stmt.executeQuery().use { rs ->
                    while (rs.next()) {
                        val id = rs.getLong(1)
                        val name = rs.getString(2)
                        val gender = rs.getInt(3)
                        val grade = rs.getLong(4)
                        val score = rs.getLong(5)
                        val sex = when(gender) {
                            0 -> FEMALE
                            1 -> MALE
                            else -> OTHER
                        }
                        students.add(Student(id,name,sex,grade,score))
                    }
                }
            }
     //   }
        return students
    }

    fun insert(student : Student) {
    //    DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD).use { conn ->
            conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS).use { stmt ->
                //stmt.setObject(1,student.id)
                stmt.setObject(1,student.name)
                val gender = when(student.gender) {
                    MALE -> 1
                    FEMALE -> 0
                    OTHER -> 2
                }
                stmt.setObject(2,gender)
                stmt.setObject(3,student.grade)
                stmt.setObject(4,student.score)
                stmt.executeUpdate()
//                stmt.generatedKeys.use { rs ->
//                    if(rs.next()) {
//                        val id = rs.getLong(1)
//                    }
//                }
            }
    //    }
    }

    fun delete(primaryId : Long) {
     //   DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD).use { conn ->
            conn.prepareStatement(DELETE_SQL).use { ps ->
                ps.setObject(1, primaryId)
              //  val n =
                    ps.executeUpdate()
            }
       // }
    }

    fun update() {
        //略
    }

    //事务
    fun updateMulti() {

        try {
            // 关闭自动提交:
            conn.setAutoCommit(false)
            // 执行多条SQL语句:
//            insert()
//            update()
//            delete()
            // 提交事务:
            conn.commit()
        } catch (e: SQLException) {
            // 回滚事务:
            conn.rollback()
        } finally {
            conn.setAutoCommit(true)
            conn.close()
        }
    }

    //循环 批量操作
    fun insertStudents(students : List<Student>) {
     //   val conn: Connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
        conn.prepareStatement(INSERT_SQL).use { ps ->
            // 对同一个PreparedStatement反复设置参数并调用addBatch():
            for ((_, name, gender, grade, score) in students) {
                ps.setString(1, name)
                val sex = when(gender) {
                    FEMALE -> 0
                    MALE -> 1
                    OTHER -> 2
                }
                ps.setObject(2,sex)
                ps.setObject(3,grade)
                ps.setObject(4,score)
                ps.addBatch() // 添加到batch
            }
            // 执行batch:
//            val ns: IntArray =
                ps.executeBatch()
//            for (n in ns) {
//                println("$n inserted.")
//            }
        }
    }

    //批量删除
    fun deleteStudents(ids : List<Long>) {
//        val conn: Connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)
        conn.prepareStatement(DELETE_SQL).use { ps ->
            // 对同一个PreparedStatement反复设置参数并调用addBatch():
            for (id in ids) {
                ps.setObject(1, id)
                ps.addBatch() // 添加到batch
            }
            // 执行batch:
//            val ns: IntArray =
                ps.executeBatch()
//            for (n in ns) {
//                println("$n inserted.")
//            }
        }
    }

}


