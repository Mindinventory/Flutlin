![N|Solid](flutter.png)![N|Solid](plus.png)![N|Solid](kotlin.png)

![N|Solid](sample.gif)
# Flutter + Kotlin (Flutlin)
### Aim
To find out the way to utilize flutter UI design benefits while on logical tasks like rest api calling  with Kotlin.
  - UI design in Flutter
  - Rest api calling with Retrofit in Kotlin.

### Tech
* FlutterView (https://docs.flutter.io/javadoc/io/flutter/view/FlutterView.html)
* FlutterActivity (https://docs.flutter.io/javadoc/io/flutter/app/FlutterActivity.html)

### Steps to follow to use Flutter View
1) Create Flutter Project.
2) Open android project in Android Studio.
    * Create your own layout for your activity and add simple `FlutterView` 
        ```
        <io.flutter.view.FlutterView
                android:id="@+id/flutter_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent" /> 
    * Need some initialisation for flutter as per below:
        ``` 
        val args = getArgsFromIntent(intent)
        FlutterMain.ensureInitializationComplete(applicationContext, args)
    * Create `FlutterRunArguments`, provide context and entry point of dart for that view.
        ```
        val runArguments = FlutterRunArguments()
        runArguments.bundlePath = FlutterMain.findAppBundlePath(applicationContext)
        runArguments.entrypoint = "main"
    * Bind your `FlutterView`
        ```
        flutterView = findViewById(R.id.flutter_view)
        flutterView!!.runFromBundle(runArguments)
    * User any channel to communicate with your dart file and activity. In our case, I have used `BasicMessageChannel` to pass string as response.
        ```
        messageChannel = BasicMessageChannel(flutterView, CHANNEL, StringCodec.INSTANCE)
        messageChannel?.send(message)
    * Close android project.
3) Open Flutter Project.
    * Initialise `BasicMessageChannel` channel which will be helpful to fetch data shared in our Actitivy.
        ```
        static const BasicMessageChannel<String> platform =
        BasicMessageChannel<String>(_channel, StringCodec());
    * Register callback in your `initState()` widget
        ```
        platform.setMessageHandler(_handlePlatformMessage);
    * Create method as per below through which you can parse your string into response model and that you can use to reflect in UI.
        ```
        Future<String> _handlePlatformMessage(String message) async {
            // use setState() to reflect the message in  your ui.
        }

# LICENSE!

Flutlin is [MIT-licensed](/LICENSE).

# Let us know!
We’d be really happy if you send us links to your projects where you use our component. Just send an email to sales@mindinventory.com And do let us know if you have any questions or suggestion regarding our work.        