package com.example.demoapp.viewmodel.userprofile

import android.content.Context
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

class VideoViewVM(val weakContext: WeakReference<Context?>) : ViewModel() {

    var backStackLost = false

    /** Intent action for media controls from Picture-in-Picture mode.  */
    val ACTION_MEDIA_CONTROL = "media_control"

    /** Intent extra for media controls from Picture-in-Picture mode.  */
    val EXTRA_CONTROL_TYPE = "control_type"

    /** The request code for play action PendingIntent.  */
    val REQUEST_PLAY = 1

    /** The request code for pause action PendingIntent.  */
    val REQUEST_PAUSE = 2

    /** The request code for info action PendingIntent.  */
    val REQUEST_INFO = 3

    /** The intent extra value for play action.  */
    val CONTROL_TYPE_PLAY = 1

    /** The intent extra value for pause action.  */
    val CONTROL_TYPE_PAUSE = 2


}