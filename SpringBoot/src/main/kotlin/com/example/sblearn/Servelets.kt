package com.example.sblearn

import jakarta.servlet.*
import jakarta.servlet.annotation.WebListener
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


@WebServlet("/first")
open class FirstServlet : HttpServlet() {
	override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
		val printWriter = response.writer
		response.contentType = "UTF-8"
		printWriter.apply {
			write("<h1>FIRST PAGE</h1>")
			flush()
		}
	}
}


//@WebFilter("/*")
open class EncodingFilter : Filter {
	override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
		val httpRequest = request as HttpServletRequest
		val httpResponse = response as HttpServletResponse
//		response.contentType = "UTF-8"
		println("EncodingFilter Doing...")
		chain.doFilter(request, response)
	}
}

@WebListener
open class ContextListener : ServletContextListener {
	override fun contextInitialized(sce: ServletContextEvent?) {
		println("初始化监听器成功")
	}
}
