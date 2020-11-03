package com.example.tawkpracticaltest.util

import android.net.*
import android.os.Build
import android.telephony.NetworkRegistrationInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ConnectivityChecker(
    private val connectivityManager: ConnectivityManager
)  {
    private val _connectedStatus = MutableLiveData<Boolean>()
    val connectedStatus: LiveData<Boolean>
        get() = _connectedStatus

    private val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _connectedStatus.postValue(true)
        }

        override fun onLost(network: Network) {
           _connectedStatus.postValue(false)
        }
    }

    fun startMonitoringConnectivity() {
        var connected = false

        if (Build.VERSION.SDK_INT >= Build. VERSION_CODES.M) {
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                connected = hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        || hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            }
        } else {
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            connected = activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

        _connectedStatus.postValue(connected)

        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build(),
            connectivityCallback
        )
    }
}