package com.app.garminauthentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.garminauthdemo.Utility.GARMIN_REDIRECT_URL
import com.app.garminauthdemo.Utility.MAIN_ACTIVITY_TAG
import com.app.garminauthdemo.Utility.seperatorWithCharacter
import com.app.garminauthentication.Helper.OauthSignatureHelper
import com.app.garminauthentication.Utility.AuthenticatingWebView
import com.app.garminauthentication.Utility.AuthenticatingWebViewCallbackMethods
import java.util.HashMap

class MainActivity : AppCompatActivity(),
    AuthenticatingWebViewCallbackMethods {

    lateinit var authenticatingWebView: AuthenticatingWebView
    lateinit var oauth_token: String
    lateinit var oauth_token_secret: String
    lateinit var URLWB: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authenticatingWebView =
            AuthenticatingWebView(
                findViewById(R.id.webView),
                this
            )

        generateOauthToken()
    }

    private fun generateOauthToken() {
        executeVolley(OauthSignatureHelper.url)
    }


    override fun displayResults(authorizationReturnParameters: HashMap<String, String>) {
        Log.e("Authorization ", "" + authorizationReturnParameters)

        if (authorizationReturnParameters.containsKey("oauth_verifier")) {
            AlertDialog.Builder(this@MainActivity)
                .setTitle("Success :)")
                .setMessage("Garmin authentication completed successfully.")
                .setNeutralButton(
                    android.R.string.ok
                ) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun stopProgressDialog() {

    }

    override fun startProgressDialog() {

    }

    private fun executeVolley(url: String) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { response ->
                print(response)
                when {
                    response.toString().contains("oauth_token") -> {
                        oauth_token = response.seperatorWithCharacter("&")[0]
                        oauth_token_secret = response.seperatorWithCharacter("&")[1]
                        //authenticate()

                        val urlGarmin = "https://connect.garmin.com/oauthConfirm?" +
                                "$oauth_token&" +
                                "oauth_callback=$GARMIN_REDIRECT_URL"

                        authenticatingWebView.makeRequest(urlGarmin)
                    }
                }
            },
            Response.ErrorListener {
                Log.e(MAIN_ACTIVITY_TAG, it.toString())
            })
        Volley.newRequestQueue(this@MainActivity).add(stringRequest)
    }
}
