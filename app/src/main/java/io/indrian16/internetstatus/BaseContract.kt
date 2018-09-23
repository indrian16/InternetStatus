package io.indrian16.internetstatus

import android.content.Context
import io.indrian16.internetstatus.model.ConnectionStatusStr

interface BaseContract {

    interface Presenter {

        fun checkingConnection()

        fun unSubscribe()
    }

    interface View {

        fun getContext(): Context

        fun updateConnectionStatus(value: ConnectionStatusStr)
        fun updateInternetStatus(value: Boolean)

        fun imageTypeMobile()
        fun imageTypeWIFI()
        fun imageTypeNone()
    }
}