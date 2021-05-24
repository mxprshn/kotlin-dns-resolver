package resolver

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.InetSocketAddress

private const val DNS_PORT = 53

fun runDns(ip: String) {
    runBlocking {
        val server = aSocket(ActorSelectorManager(Dispatchers.IO))
            .udp()
            .bind(InetSocketAddress(ip, DNS_PORT))

        while (true) {
            val datagram = server.receive()

            launch {
                val packet = datagram.packet.readBytes()
            }
        }
    }
}