package io.indrian16.internetstatus

import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.indrian16.internetstatus.model.ConnectionStatusStr
import io.indrian16.internetstatus.util.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BasePresenter(internal val view: BaseContract.View) : BaseContract.Presenter  {

    private var connectivityDisposable: Disposable? = null
    private var internetDisposable: Disposable? = null

    private val liveStatus = ConnectionStatusStr()

    override fun checkingConnection() {

        connectivityDisposable = ReactiveNetwork.observeNetworkConnectivity(view.getContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { connectivity ->

                    checkTypeConnection(connectivity.type())
                    checkDetailState(connectivity.state())

                    view.updateConnectionStatus(liveStatus)
                }

        internetDisposable = ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { isConnectionInternet ->

                    view.updateInternetStatus(isConnectionInternet)
                }
    }

    private fun checkTypeConnection(type: Int) {

        when (type) {

            ConnectivityManager.TYPE_MOBILE -> {

                view.imageTypeMobile()
                liveStatus.typeConnection = TYPE_MOBILE
            }
            ConnectivityManager.TYPE_WIFI -> {

                view.imageTypeWIFI()
                liveStatus.typeConnection = TYPE_WIFI
            }

            else -> {view.imageTypeNone(); liveStatus.typeConnection = TYPE_NONE}
        }
    }

    private fun checkDetailState(state: Enum<NetworkInfo.State>) {

        when (state) {

            NetworkInfo.State.CONNECTING -> liveStatus.state = CONNECTING
            NetworkInfo.State.CONNECTED -> liveStatus.state = CONNECTED
            NetworkInfo.State.SUSPENDED -> liveStatus.state = SUSPENDED
            NetworkInfo.State.DISCONNECTING -> liveStatus.state = DISCONNECTING
            NetworkInfo.State.DISCONNECTED -> liveStatus.state = DISCONNECTED
            NetworkInfo.State.UNKNOWN -> liveStatus.state = UNKNOWN

            else -> liveStatus.state = UNKNOWN
        }
    }
    private fun safelyDispose(disposable: Disposable?) {

        if (disposable != null && disposable.isDisposed) {

            disposable.dispose()
        }
    }

    override fun unSubscribe() {

        safelyDispose(connectivityDisposable)
        safelyDispose(internetDisposable)
    }
}