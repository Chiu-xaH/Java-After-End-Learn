package org.example.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

//服务类
@Service
open class StudentService {
    @Autowired
    @Qualifier("studentList1")
    lateinit var students1 : List<StudentBean>

    @Autowired
    @Qualifier("studentList2")
    lateinit var students2 : List<StudentBean>

    //@get:Profile("test")

    @Value("classpath:test.txt")
    lateinit var resource : Resource

    fun readFileAndPrint() {
        if(isResourceInit()) {
            resource.inputStream.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        println(line)
                    }
                }
            }
        } else {
            println("未初始化")
        }
    }

    private fun isResourceInit() = ::resource.isInitialized

    @PostConstruct
    fun init() {
        println("初始化")
    }

    @PreDestroy
    fun shutdown() {
        println("已销毁")
    }
}
