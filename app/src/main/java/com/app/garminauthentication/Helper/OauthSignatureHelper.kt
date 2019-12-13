package com.app.garminauthentication.Helper

import com.app.garminauthdemo.Utility.*
import com.woocommerse.OAuth1.services.HMACSha1SignatureService
import com.woocommerse.OAuth1.services.TimestampServiceImpl
import java.io.UnsupportedEncodingException
import java.net.URLEncoder

object OauthSignatureHelper {
    private const val BASE_URL = GARMIN_CONNECT_BASE_URL
    private const val COSTUMER_KEY = CONSUMER_KEY
    private const val COSTUMER_SECRET = CONSUMER_SECRET
    private const val METHORD =
        "GET" //change API method eg POST,PUT, DELETE etc (ONLY FOR THIS EXAMPLE FOR LIB LIKE RETROFIT,OKHTTP, The Are Dynamic Way)//BECAUSE I PUT IN SIMPLE THREAD NOT NECESSARY

    // GENERATED NONCE and TIME STAMP
    //THE BASE STRING AND COSTUMER_SECRET KEY IS USED FOR GENERATING SIGNATURE
    //Signature is encoded before parsing (ONLY FOR THIS EXAMPLE NOT NECESSARY FOR LIB LIKE RETROFIT,OKHTTP)
    val url: String
        get() {
            val nonce = TimestampServiceImpl().nonce
            val timestamp = TimestampServiceImpl().timestampInSeconds
            // GENERATED NONCE and TIME STAMP
            val firstEncodedString =
                METHORD + "&" + encodeUrl(BASE_URL)
            val parameterString =
                "oauth_consumer_key=$COSTUMER_KEY&oauth_nonce=$nonce&oauth_signature_method=HMAC-SHA1&oauth_timestamp=$timestamp&oauth_version=1.0"
            val secoundEncodedString =
                "&" + encodeUrl(parameterString)
            val baseString = firstEncodedString + secoundEncodedString
            //THE BASE STRING AND COSTUMER_SECRET KEY IS USED FOR GENERATING SIGNATURE
            var signature = HMACSha1SignatureService().getSignature(
                baseString,
                COSTUMER_SECRET,
                ""
            )
            //Signature is encoded before parsing (ONLY FOR THIS EXAMPLE NOT NECESSARY FOR LIB LIKE RETROFIT,OKHTTP)
            signature = encodeUrl(signature)
            val finalSignature = signature //BECAUSE I PUT IN SIMPLE THREAD NOT NECESSARY
            return "$BASE_URL?oauth_consumer_key=$COSTUMER_KEY&oauth_nonce=$nonce&oauth_signature=$finalSignature&oauth_signature_method=HMAC-SHA1&oauth_timestamp=$timestamp&oauth_version=1.0"
        }

    private fun encodeUrl(url: String): String {
        var encodedurl = ""
        try {
            encodedurl = URLEncoder.encode(url, "UTF-8")
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return encodedurl
    }
}