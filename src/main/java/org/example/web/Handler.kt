package org.example.web

import java.io.*
import java.net.Socket
import java.nio.charset.StandardCharsets

internal class Handler(private var sock: Socket) : Thread() {
    override fun run() {
        try {
            sock.getInputStream().use { input ->
                sock.getOutputStream().use { output ->
                    handle(input, output)
                }
            }
        } catch (_: Exception) { } finally {
            try {
                sock.close()
            } catch (_: IOException) { }
            println("客户端断连")
        }
    }

//    @Throws(IOException::class)
    private fun handle(input: InputStream, output: OutputStream) {
        println("创建新请求中")
        val reader = BufferedReader(InputStreamReader(input, StandardCharsets.UTF_8))
        val writer = BufferedWriter(OutputStreamWriter(output, StandardCharsets.UTF_8))
        // 读取HTTP请求:
        var requestOk = false
        val first = reader.readLine()
        if (first.startsWith("GET / HTTP/1.")) {
            requestOk = true
        }

        //循环打印Header
        while (true) {
            val header = reader.readLine()
            if (header.isEmpty()) { // 读取到空行时, HTTP Header读取完毕
                break
            }
            println(header)
        }
        println(if (requestOk) "响应 OK" else "响应 Error")

        if (!requestOk) {
            // 发送错误响应:
            writer.write("HTTP/1.0 404 Not Found\r\n")
            writer.write("Content-Length: 0\r\n")
            writer.write("\r\n")
            writer.flush()
        } else {
            // 发送成功响应:
            val data = """
                <html>
                    <head>
                        <meta charset="UTF-8" />
                        <title>测试</title>
                    </head>
                    <body>
                        <div>
                            <h1>测试</h1>
                        </div>
                    </body>
                </html>
            """.trimIndent()
            val length = data.toByteArray(StandardCharsets.UTF_8).size
            writer.write("HTTP/1.0 200 OK\r\n")
            writer.write("Connection: close\r\n")
            writer.write("Content-Type: text/html\r\n")
            writer.write("Content-Length: $length\r\n")
            writer.write("\r\n") // 空行标识Header和Body的分隔
            writer.write(data)
            writer.flush()
        }
    }
}
