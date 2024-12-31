package com.example.sblearn

import jakarta.servlet.*
import jakarta.servlet.annotation.WebListener
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.text.SimpleDateFormat
import java.util.*

@SpringBootApplication
@ServletComponentScan
@EnableScheduling //定时任务
//@MapperScan("com.example.sblearn")
class SblearnApplication

fun main(args: Array<String>) {
	runApplication<SblearnApplication>(*args)
}

@Controller
class HelloController {
//	@Value("\${user.studentid}")
//	lateinit var  email : String

	@GetMapping("/Hello")
//	@ResponseBody
	fun hello() : String {
		return "page" //返回page.html
	}
}

//@Component
class MyTask {
	@Scheduled(cron = "* * * * * * ")
	fun task() {
		val sdf = SimpleDateFormat("HH:MM:SS")
		println(sdf.format(Date()))
	}
}

@Controller
class ConsultController {
	@Autowired
	lateinit var studentMapper : StudentMapper

	@RequestMapping("/student/findById")
	@ResponseBody
	fun findById(id : Long): StudentBean? {
		val student = studentMapper.getById(id)
//		if(student != null) {
		return student
	}
}


