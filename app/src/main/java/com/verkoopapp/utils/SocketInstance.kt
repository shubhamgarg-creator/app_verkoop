package com.verkoopapp.utils

import android.app.Application
import android.util.Log
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import java.net.URISyntaxException


class SocketInstance : Application() {
    private var iSocket: Socket? = null
    override fun onCreate() {
        super.onCreate()
        try {


            iSocket = IO.socket(AppConstants.SOCKET_URL)
            Log.e("TAG", "onCreate: "+iSocket.toString())
        } catch (e: URISyntaxException) {
            throw RuntimeException(e)
            Log.e("TAG", "onCreate: "+e.message)
        }
    }

    val socketInstance: Socket?
        get() = iSocket

    companion object {
        private const val URL = "http://your_socket_connection_url.com"
    }
}
