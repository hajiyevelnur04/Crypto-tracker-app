package com.crypto.tracker.presentation.home

import androidx.lifecycle.ViewModel
import com.crypto.tracker.model.remote.response.CryptoModel


class HomeViewModel: ViewModel() {
    val adapter = HomeAdapter{
        //to do on click
    }

    init {
        setDummyTestCrypto()
    }

    private fun setDummyTestCrypto() {
        val list = arrayListOf<CryptoModel>()
        list.add(CryptoModel("1","test1"))
        list.add(CryptoModel("2","test2"))

        // set it to paginable adapter
    }
}