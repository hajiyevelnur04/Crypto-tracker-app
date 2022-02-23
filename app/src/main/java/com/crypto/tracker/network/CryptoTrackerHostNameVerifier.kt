package com.crypto.tracker.network

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class CryptoTrackerHostNameVerifier: HostnameVerifier {
    override fun verify(hostname: String?, session: SSLSession?): Boolean {
        // here check hostname , but now lets just pass true qaqa
        return true
    }
}
