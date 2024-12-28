package org.example.web

import jakarta.servlet.*
import jakarta.servlet.annotation.WebFilter
import jakarta.servlet.annotation.WebListener
import jakarta.servlet.annotation.WebServlet
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.util.concurrent.ConcurrentHashMap
import kotlin.experimental.and


//@WebServlet(urlPatterns = ["/hello"])
//open class HelloServlet : HttpServlet() {
//    override fun doGet(request : HttpServletRequest, response : HttpServletResponse) {
//        val name = request.getParameter("name") ?: ""
//        response.contentType = "text/html"
//        val printWriter = response.writer
//        printWriter.write("""
//                <html>
//                    <head>
//                        <meta charset="UTF-8" />
//                        <title>测试</title>
//                    </head>
//                    <body>
//                        <div>
//                            <h1>你好 ${name}</h1>
//                        </div>
//                    </body>
//                </html>
//            """.trimIndent()
//        )
//        printWriter.close()
//    }
//}

//@WebServlet(urlPatterns = ["/hi"])
//open class HiServlet : HttpServlet() {
//    override fun doGet(request : HttpServletRequest, response : HttpServletResponse) {
//        val name = request.getParameter("name") ?: ""
//        val redirectUrl = "/Hi/hello?name=${name}"
//        //临时重定向 302
////        response.sendRedirect(redirectUrl)
//        //永久重定向 301
//        response.status = HttpServletResponse.SC_MOVED_PERMANENTLY
//        response.setHeader("Location",redirectUrl)
//    }
//}


//@WebServlet(urlPatterns = ["/morning"])
//open class ForwardServlet : HttpServlet() {
//    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
//        //代理转发
//        request.getRequestDispatcher("/hello").forward(request,response)
//    }
//}

@WebServlet(urlPatterns = ["/signin"])
open class SignInServlet : HttpServlet() {
    //模拟数据库
    val users = mapOf (
        "Chiu-xaH" to "chiuxah12345"
    )
    //显示登录页面
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        response.contentType = "text/html"
        val printWriter = response.writer
        val contextPath = request.contextPath
        printWriter.apply {
            write("<h1>登录</h1>")
            write("<form action=\"${contextPath}/signin\" method=\"post\">")
            write("<p>手机号: <input name=\"username\"></p>")
            write("<p>密码: <input name=\"password\" type=\"password\"></p>")
            write("<p><button type=\"submit\">登录</button> <a href=\"${contextPath}/\">取消</a></p>")
            write("</form>")
            flush()
        }
    }
    //用户提交登录
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        val contextPath = request.contextPath
        val name = request.getParameter("username")
        val password = request.getParameter("password")
        val exceptedPwd = users[name]
        if(exceptedPwd != null && exceptedPwd == password) {
            request.session.setAttribute("user",name)
            response.sendRedirect("${contextPath}/")
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN)
        }
    }
}

@WebServlet(urlPatterns = ["/"])
open class IndexServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val contextPath = request.contextPath
        val user = request.session.getAttribute("user")
        response.contentType = "text/html"
//        response.characterEncoding = "UTF-8"
        response.setHeader("X-Powered-By","JavaEE Servlet")
        val printWriter = response.writer
        printWriter.apply {
            write("<h1>欢迎, " + (user ?: "游客") + "</h1>")
            write("<p><a href=\"${contextPath}/user/profile\">编辑个人资料</a></p>");
            write("<p><a href=\"${contextPath}/user/post\">发帖</a></p>");
            write("<p><a href=\"${contextPath}/user/reply\">回复</a></p>");
            if (user == null) {
                // 未登录，显示登录链接:
                write("<p><a href=\"${contextPath}/signin\">登录</a></p>");
            } else {
                // 已登录，显示登出链接:
                write("<p><a href=\"${contextPath}/signout\">注销</a></p>");
            }
            flush()
        }
    }
}

@WebServlet(urlPatterns = ["/signout"])
open class SignOutServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
        val contextPath = request.contextPath
        request.session.removeAttribute("user")
        response.sendRedirect("${contextPath}/")
    }
}

@WebServlet(urlPatterns = ["/user/profile"])
open class ProfileServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
//        val contextPath = request.contextPath
        val user = request.session.getAttribute("user")
        response.contentType = "text/html"
        response.setHeader("X-Powered-By","JavaEE Servlet")
        val printWriter = response.writer
        printWriter.apply {
            write("<h1>这里是个人资料" + "</h1>")
            write("<h2>用户名 ${user ?: "游客"}" + "</h2>")
            flush()
        }
    }
}

@WebServlet(urlPatterns = ["/user/post"])
open class PostServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
//        val contextPath = request.contextPath
        response.contentType = "text/html"
        response.setHeader("X-Powered-By","JavaEE Servlet")
        val printWriter = response.writer
        printWriter.apply {
            write("<h1>模拟发帖成功" + "</h1>")
            flush()
        }
    }
}

@WebServlet(urlPatterns = ["/user/reply"])
open class ReplyServlet : HttpServlet() {
    override fun doGet(request: HttpServletRequest, response: HttpServletResponse) {
//        val contextPath = request.contextPath
        response.contentType = "text/html"
        response.setHeader("X-Powered-By","JavaEE Servlet")
        val printWriter = response.writer
        printWriter.apply {
            write("<h1>模拟回复成功" + "</h1>")
            flush()
        }
    }
}
//通配符，表示所有路径
//为所有请求响应设置UTF-8编码
@WebFilter(urlPatterns = ["/*"])
open class EncodingFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        println("EncodingFilter:doFilter")
        val encode = "UTF-8"
        request.characterEncoding = encode
        response.characterEncoding = encode
        //继续处理请求
        chain.doFilter(request,response)
    }
}
//为所有请求响应打印日志
//@WebFilter(urlPatterns = ["/*"])
//open class LogFilter : Filter {
//    override fun doFilter(
//        request: ServletRequest,
//        response: ServletResponse,
//        chain: FilterChain
//    ) {
//        val httpRequest = request as HttpServletRequest
//        val httpResponse = response as HttpServletResponse
//        println("LogFilter: process " + httpRequest.requestURL)
//        //继续处理请求
//        chain.doFilter(request,response)
//    }
//}
//检测是否登录
@WebFilter(urlPatterns = ["/user/*"])
open class AuthFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        println("AuthFilter: 检查登录状态")
        if(httpRequest.session.getAttribute("user") == null) {
            val contextPath = request.contextPath
            println("AuthFilter: 未登录")
            httpResponse.sendRedirect("$contextPath/signin")
        } else {
            println("AuthFilter: 已登录")
            //继续处理
            chain.doFilter(request,response)
        }
    }
}

//上传文件
@WebServlet(urlPatterns = ["/upload/file"])
open class UploadServlet : HttpServlet() {
    override fun doPost(request: HttpServletRequest,response: HttpServletResponse) {
        val input = request.inputStream
        val output = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        //读取
        while (true) {
            val len = input.read(buffer)
            if (len == -1)
                break
            output.write(buffer, 0, len)
        }
        //写入
        val uploadText = output.toString()
        val printWriter = response.writer
        printWriter.apply {
            write("<h1>上传</h1>")
            write("<pre><code>")
            write(uploadText)
            write("</code></pre>")
            flush()
        }
    }
}

//验证文件完整性
@WebFilter(urlPatterns = ["/upload/*"])
open class ValidateUpdateFilter : Filter {
    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        val digest = httpRequest.getHeader("Signature-Method")
        val signature = httpRequest.getHeader("Signature")
        if(digest == null || digest.isEmpty() || signature == null || signature.isEmpty()) {
            sendErrorPage(httpResponse,"无Digest或Sign")
            return
        }
        val md = getMessageDigest(digest)
        val input = request.inputStream
        val buffer = ByteArray(1024)
        //读取
        while (true) {
            val len = input.read(buffer)
            if (len == -1)
                break
        }
        val actual = md?.let { toHexString(it.digest()) }
        if(signature != actual) {
            sendErrorPage(httpResponse,"不合理")
            return
        }
        chain.doFilter(request,response)
    }
    private fun sendErrorPage(response : HttpServletResponse,errorMsg : String) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        val pw = response.writer
        pw.write("<html><body><h1>")
        pw.write(errorMsg)
        pw.write("</h1></body></html>")
        pw.flush()
    }
    private fun toHexString(digest: ByteArray): String {
        val sb = StringBuilder()
        for (b in digest) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }

    // 根据名称创建MessageDigest:
    private fun getMessageDigest(name: String): MessageDigest? {
        return try {
            MessageDigest.getInstance(name)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

//open class ReReadableHttpServletRequest(
//    request: HttpServletRequest,
//    private val body : ByteArray
//    ) : HttpServletRequestWrapper(request) {
//    private var open = false
//
//    @Throws(IOException::class)
//    override fun getInputStream(): ServletInputStream {
//        if(open) {
//            throw java.lang.IllegalStateException("Cannot re-open input stream!")
//        }
//        open = true
//        return object : ServletInputStream() {
//            private var offset = 0
//            override fun read(): Int {
//                if(isFinished)
//                    return -1
//                val n: Int = body[offset] and 0xff
//
//            }
//
//            override fun isFinished(): Boolean {
//                return offset >= body.size
//            }
//
//            override fun isReady(): Boolean {
//                return true
//            }
//
//            override fun setReadListener(p0: ReadListener?) {
//                TODO("Not yet implemented")
//            }
//
//        }
//    }
//}
//

//internal class ReReadableHttpServletRequest(request: HttpServletRequest?, private val body: ByteArray) :
//    HttpServletRequestWrapper(request) {
//    private var open = false
//
//    // 返回InputStream:
//    @Throws(IOException::class)
//    override fun getInputStream(): ServletInputStream {
//        check(!open) { "Cannot re-open input stream!" }
//        open = true
//        return object : ServletInputStream() {
//            private var offset = 0
//            override fun isFinished(): Boolean {
//                return offset >= body.size
//            }
//
//            override fun isReady(): Boolean {
//                return true
//            }
//
//            override fun setReadListener(listener: ReadListener) {}
//
//            @Throws(IOException::class)
//            override fun read(): Int {
//                if (offset >= body.size) {
//                    return -1
//                }
//                val n = body[offset].toInt() and 0xff
//                offset++
//                return n
//            }
//        }
//    }
//
//    // 返回Reader:
//    @Throws(IOException::class)
//    override fun getReader(): BufferedReader {
//        check(!open) { "Cannot re-open reader!" }
//        open = true
//        return BufferedReader(InputStreamReader(ByteArrayInputStream(body), "UTF-8"))
//    }
//}

//@WebFilter(urlPatterns = ["/slow/*"])
//open class CacheFilter : Filter {
//    private val cache = ConcurrentHashMap<String,ByteArray>()
//
//    override fun doFilter(
//        request: ServletRequest,
//        response: ServletResponse,
//        chain: FilterChain
//    ) {
//        val httpRequest = request as HttpServletRequest
//        val httpResponse = response as HttpServletResponse
//        try {
//            val url = httpRequest.requestURL.toString()
//            val data = this.cache[url]
//            httpResponse.setHeader("X-Cache-Hit",if (data == null) "No" else "Yes")
//            if(data == null) {
//                //未找到缓存，构造一个伪造的响应
//                val wrapper = CachedHttpServletResponse
//            }
//            val output = httpResponse.outputStream
//            output.write(data)
//            data?.let { cache.put(url, it) }
//        } catch (e : Exception) {
//            e.printStackTrace()
//            //chain.doFilter(request,response)
//        }
//    }
//}

@WebListener
open class AppListener : ServletContextListener {
    override fun contextInitialized(sce: ServletContextEvent?) {
        println("WebApp 已初始化")
    }

    override fun contextDestroyed(sce: ServletContextEvent?) {
        println("WebApp 已释放")
    }
}

