package io.indrian16.internetstatus

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import io.indrian16.internetstatus.model.ConnectionStatusStr
import io.indrian16.internetstatus.util.OFFLINE
import io.indrian16.internetstatus.util.ONLINE
import kotlinx.android.synthetic.main.activity_base.*

/***
 *
 *  Create By Indrian(rg16)
 *  Location Tenggarong Kutai Kartanegara
 */

class BaseActivity : BaseContract.View, AppCompatActivity() {

    private val presenter = BasePresenter(this)

    override fun onResume() {
        super.onResume()
        presenter.checkingConnection()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    override fun getContext(): Context = baseContext

    override fun updateConnectionStatus(value: ConnectionStatusStr) {

        tv_type_network_value.text = value.typeConnection
        tv_state_value.text = value.state
    }

    override fun updateInternetStatus(value: Boolean) {

        if (value) {

            tv_internet_status.text = ONLINE
            tv_internet_status.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        } else {

            tv_internet_status.text = OFFLINE
            tv_internet_status.setTextColor(ContextCompat.getColor(this, R.color.colorTextSecond))
        }
    }

    override fun imageTypeMobile() {

        image_type.setImageResource(R.drawable.icons8_cellular_network_96)
    }

    override fun imageTypeWIFI() {

        image_type.setImageResource(R.drawable.icons8_wi_fi_96)
    }

    override fun imageTypeNone() {

        image_type.setImageResource(R.drawable.icons8_question_mark_96)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unSubscribe()
    }
}
