import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.utils.io.*

//@OptIn(InternalAPI::class)
suspend fun main(){
    val client = HttpClient(CIO) {

    }
    val response = client.get("http://8.154.28.108/getSongmid") {
        parameter("songid","123")
    }

    var responseBody = response.body<String>()

    println(responseBody)
    client.close()
}