package com.flutlin.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log.d
import com.flutlin.apiConfig.WebApiClient
import com.flutlin.R
import com.flutlin.apiConfig.response.UserResponse
import com.google.gson.GsonBuilder
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterRunArguments
import io.flutter.view.FlutterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {
  private var flutterView: FlutterView? = null
  private var messageChannel: BasicMessageChannel<String>? = null
  private val CHANNEL = "api"
  private var LOG_TAG = "apiCall"

  private fun getArgsFromIntent(intent: Intent): Array<String>? {
    // Before adding more entries to this list, consider that arbitrary
    // Android applications can generate intents with extra data and that
    // there are many security-sensitive args in the binary.
    val args = ArrayList<String>()
    if (intent.getBooleanExtra("trace-startup", false)) {
      args.add("--trace-startup")
    }
    if (intent.getBooleanExtra("start-paused", false)) {
      args.add("--start-paused")
    }
    if (intent.getBooleanExtra("enable-dart-profiling", false)) {
      args.add("--enable-dart-profiling")
    }
    if (!args.isEmpty()) {
      val argsArray = arrayOfNulls<String>(args.size)
      return args.toTypedArray<String>()
    }
    return null
  }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // handling arguments.
    val args = getArgsFromIntent(intent)
    FlutterMain.ensureInitializationComplete(applicationContext, args)

    // requesting for layout inside content view.
    setContentView(R.layout.flutter_view_layout)

    // hiding action bar
    val supportActionBar = supportActionBar
    supportActionBar?.hide()

    flutterView = findViewById(R.id.flutter_view)

    // preparing bundle to use for FlutterView we have added inside activity layout.
    val runArguments = FlutterRunArguments()
    runArguments.bundlePath = FlutterMain.findAppBundlePath(applicationContext)
    runArguments.entrypoint = "main"
    flutterView!!.runFromBundle(runArguments)

    // establishing message channel which can be used for the data passing from one platform to another.
    messageChannel = BasicMessageChannel(flutterView, CHANNEL, StringCodec.INSTANCE)

    // calling rest api of random user.
    callRandomUserApi()
  }

  /**
   * this function is used to send message on Message Channel with appropriate delay.
   */
  private fun sendAndroidIncrement(message: String) {
    Handler().postDelayed({ messageChannel?.send(message) }, 500)
  }

  override fun onPostResume() {
    super.onPostResume()
    flutterView?.onPostResume()
  }

  /**
   * here, we are calling random user profile.
   */
  @SuppressLint("CheckResult")
  private fun callRandomUserApi() {
    WebApiClient
            .webApiWithoutToken
            .randomUserApi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                    { result ->
                      if (result.isSuccessful && result.body() != null) {
                        val userResponse: UserResponse? = result.body()
                        userResponse?.let {
                          it.users?.let {
                            val userPicList: Array<String> = it.map {
                              it.picture?.image ?: ""
                            }.toTypedArray()
                            val gson = GsonBuilder().setPrettyPrinting().create()
//                                        val dataAsString: String = gson.toJson(images)
                            val dataAsString: String = gson.toJson(userPicList)
                            sendAndroidIncrement(dataAsString)
                          }
                        }
                      }
                    },
                    { error ->
                      d(LOG_TAG, "api error: ${error.message} ")
                    })
  }

  override fun onPause() {
    super.onPause()
    flutterView?.onPause()
  }

  override fun onDestroy() {
    flutterView?.destroy()
    super.onDestroy()
  }
}

