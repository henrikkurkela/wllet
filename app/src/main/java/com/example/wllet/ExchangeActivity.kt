package com.example.wllet

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ExchangeActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private var user: FirebaseUser? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchange)

        database = Firebase.database.reference
        user = intent.getParcelableExtra("user")
        updatePrices()
        val tabs = findViewById<TabLayout>(R.id.tabLayout)
        tabs.addTab(tabs.newTab().setText("Bitcoin"))
        tabs.addTab(tabs.newTab().setText("Ethereum"))
        tabs.addTab(tabs.newTab().setText("GME"))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val selected = findViewById<TextView>(R.id.selected)
                val errText = findViewById<TextView>(R.id.errText)
                when (tab?.position) {
                    1 -> {
                        selected.text = "Ethereum"
                        errText.visibility = View.INVISIBLE
                    }
                    2 -> {
                        selected.text = "GME"
                        errText.visibility = View.VISIBLE

                    }
                    else -> {
                        selected.text = "Bitcoin"
                        errText.visibility = View.INVISIBLE
                    }
                }
                updatePrices()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
        val wv = findViewById<View>(R.id.webView) as WebView
        wv.setOnTouchListener { _, event -> event.action == MotionEvent.ACTION_MOVE }
    }

    private fun gmeAlert() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Alert")
            setMessage("Trading with GME is currently suspended. Please try again later.")
            show()
        }
    }

    private fun successAlert() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Market order")
            setMessage("Order placed successfully! Your order will execute at market price after it has been processed.")
            show()
        }
    }

    private fun errorAlert() {
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("Market order")
            setMessage("An error occurred. Please try again later.")
            show()
        }
    }

    fun refresh(view: View) {
        updatePrices()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun updatePrices() {
        val selected = findViewById<TextView>(R.id.selected)
        val balance = findViewById<TextView>(R.id.balance)
        when (selected.text) {
            "Bitcoin" -> {
                database.child("users").child(user?.uid.toString()).child("btcbal").get()
                    .addOnSuccessListener {
                        balance.text = it.value.toString()
                    }.addOnFailureListener {}
                val data =
                    "<div style=\"height:560px; background-color: #FFFFFF; overflow:hidden; box-sizing: border-box; border: 0px solid #56667F; border-radius: 4px; text-align: right; line-height:14px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #56667F;padding:1px;padding: 0px; margin: 0px; width: 100%;\"><div style=\"height:540px; padding:0px; margin:0px; width: 100%;\"><iframe src=\"https://widget.coinlib.io/widget?type=chart&theme=light&coin_id=859&pref_coin_id=1506\" width=\"100%\" height=\"536px\" scrolling=\"auto\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" border=\"0\" style=\"border:0;margin:0;padding:0;line-height:14px;\"></iframe></div><div style=\"color: #FFFFFF; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;\"><a href=\"https://coinlib.io\" target=\"_blank\" style=\"font-weight: 500; color: #FFFFFF; text-decoration:none; font-size:11px\">Cryptocurrency Prices</a>&nbsp;by Coinlib</div></div>"
                val wv = findViewById<View>(R.id.webView) as WebView
                wv.settings.javaScriptEnabled = true
                wv.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
            }
            "Ethereum" -> {
                database.child("users").child(user?.uid.toString()).child("ethbal").get()
                    .addOnSuccessListener {
                        balance.text = it.value.toString()
                    }.addOnFailureListener {}
                val data =
                    "<div style=\"height:560px; background-color: #FFFFFF; overflow:hidden; box-sizing: border-box; border: 0px solid #56667F; border-radius: 4px; text-align: right; line-height:14px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #56667F;padding:1px;padding: 0px; margin: 0px; width: 100%;\"><div style=\"height:540px; padding:0px; margin:0px; width: 100%;\"><iframe src=\"https://widget.coinlib.io/widget?type=chart&theme=light&coin_id=145&pref_coin_id=1506\" width=\"100%\" height=\"536px\" scrolling=\"auto\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" border=\"0\" style=\"border:0;margin:0;padding:0;line-height:14px;\"></iframe></div><div style=\"color: #FFFFFF; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;\"><a href=\"https://coinlib.io\" target=\"_blank\" style=\"font-weight: 500; color: #FFFFFF; text-decoration:none; font-size:11px\">Cryptocurrency Prices</a>&nbsp;by Coinlib</div></div>"
                val wv = findViewById<View>(R.id.webView) as WebView
                wv.settings.javaScriptEnabled = true
                wv.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
            }
            else -> {
                database.child("users").child(user?.uid.toString()).child("gmebal").get()
                    .addOnSuccessListener {
                        balance.text = it.value.toString()
                    }.addOnFailureListener {}
                val data =
                    "<div style=\"height:560px; background-color: #FFFFFF; overflow:hidden; box-sizing: border-box; border: 0px solid #56667F; border-radius: 4px; text-align: right; line-height:14px; font-size: 12px; font-feature-settings: normal; text-size-adjust: 100%; box-shadow: inset 0 -20px 0 0 #56667F;padding:1px;padding: 0px; margin: 0px; width: 100%;\"><div style=\"height:540px; padding:0px; margin:0px; width: 100%;\"><iframe src=\"https://widget.coinlib.io/widget?type=chart&theme=light&coin_id=99999&pref_coin_id=1506\" width=\"100%\" height=\"536px\" scrolling=\"auto\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\" border=\"0\" style=\"border:0;margin:0;padding:0;line-height:14px;\"></iframe></div><div style=\"color: #FFFFFF; line-height: 14px; font-weight: 400; font-size: 11px; box-sizing: border-box; padding: 2px 6px; width: 100%; font-family: Verdana, Tahoma, Arial, sans-serif;\"><a href=\"https://coinlib.io\" target=\"_blank\" style=\"font-weight: 500; color: #FFFFFF; text-decoration:none; font-size:11px\">Cryptocurrency Prices</a>&nbsp;by Coinlib</div></div>"
                val wv = findViewById<View>(R.id.webView) as WebView
                wv.settings.javaScriptEnabled = true
                wv.loadDataWithBaseURL(null, data, "text/html", "utf-8", null)
            }
        }
    }

    fun actionBuy(view: View) {
        val selected = findViewById<TextView>(R.id.selected)
        val amount = findViewById<EditText>(R.id.amountAsset)
        when (selected.text) {
            "GME" -> {
                gmeAlert()
            }
            "Bitcoin" -> {
                database
                    .child("users")
                    .child(user?.uid.toString())
                    .child("btcbal")
                    .setValue(
                        com.google.firebase.database.ServerValue.increment(
                            amount.text.toString().toLong()
                        )
                    )
                    .addOnSuccessListener { successAlert() }
                    .addOnFailureListener { errorAlert() }
            }
            "Ethereum" -> {
                database
                    .child("users")
                    .child(user?.uid.toString())
                    .child("ethbal")
                    .setValue(
                        com.google.firebase.database.ServerValue.increment(
                            amount.text.toString().toLong()
                        )
                    )
                    .addOnSuccessListener { successAlert() }
                    .addOnFailureListener { errorAlert() }
            }
        }
    }

    fun actionSell(view: View) {
        val selected = findViewById<TextView>(R.id.selected)
        val amount = findViewById<EditText>(R.id.amountAsset)
        when (selected.text) {
            "GME" -> {
                gmeAlert()
            }
            "Bitcoin" -> {
                database
                    .child("users")
                    .child(user?.uid.toString())
                    .child("btcbal")
                    .setValue(
                        com.google.firebase.database.ServerValue.increment(
                            -amount.text.toString().toLong()
                        )
                    )
                    .addOnSuccessListener { successAlert() }
                    .addOnFailureListener { errorAlert() }
            }
            "Ethereum" -> {
                database
                    .child("users")
                    .child(user?.uid.toString())
                    .child("ethbal")
                    .setValue(
                        com.google.firebase.database.ServerValue.increment(
                            -amount.text.toString().toLong()
                        )
                    )
                    .addOnSuccessListener { successAlert() }
                    .addOnFailureListener { errorAlert() }
            }
        }
    }
}