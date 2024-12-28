package org.example.web

import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.nio.charset.StandardCharsets


open class Server {
    fun startServer() {
        val port = 8080
        val serverSocket = ServerSocket(port)
        println("服务器正在启动 监听${port}端口")
        while (true) {
            val sock = serverSocket.accept()
            println("连接 从 " + sock.remoteSocketAddress)
            val t = Handler(sock)
            t.start()
        }
    }
}




