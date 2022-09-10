package com.example.demoapp.view.userprofile.fragments.video

import android.Manifest
import android.app.ActivityManager
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Rational
import android.view.View
import android.widget.MediaController
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapp.R
import com.example.demoapp.databinding.ActivityVideoViewBinding
import com.example.demoapp.utils.FileUtil
import com.example.demoapp.utils.ScreenshotUtils
import com.example.demoapp.viewmodel.userprofile.VideoViewVM
import com.example.demoapp.viewmodel.userprofile.VideoViewVMFactory
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.lang.ref.WeakReference


class VideoViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoViewBinding
    private val viewModel: VideoViewVM by viewModels {
        VideoViewVMFactory(WeakReference(this))
    }
    private lateinit var videoUrl: String

    @RequiresApi(Build.VERSION_CODES.O)
    var pictureInPictureParamsBuilder = PictureInPictureParams.Builder()

    var mReceiver: BroadcastReceiver? = null
    private var bitmap: Bitmap? = null

//    companion object {
//        fun startActivity(context: Context, intent: Intent) {
//            if (context == null) return
//             intent = Intent(context, VideoViewActivity::class.java)
//            context.startActivity(intent)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_view)
        if (intent != null && intent.hasExtra("videoUrl") && intent.getStringExtra("videoUrl") != null)
            videoUrl = intent.getStringExtra("videoUrl")!!

        init()
    }

    private fun init() {
        val mediacontroller: MediaController = MediaController(this)
        mediacontroller.setAnchorView(binding.videoView);

        binding.videoView.setMediaController(mediacontroller);
        binding.videoView.setVideoURI(Uri.parse(videoUrl));
        binding.videoView.requestFocus();

        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener {
            it.start()
        })

        binding.floatingActionPipBtn.setOnClickListener {
            startPictureInPictureFeature()
        }
        binding.buttonScreenshotActivity.setOnClickListener {
            bitmap = ScreenshotUtils.getInstance()
                ?.takeScreenshotForScreen(this); // Take ScreenshotUtil for activity
            binding.imageView.setImageBitmap(bitmap);
        }
        binding.buttonScreenshotView.setOnClickListener {

        }
        binding.buttonSaveScreenshot.setOnClickListener {
            requestPermissionAndSave();

        }
        binding.buttonReset.setOnClickListener {
            bitmap = null
            binding.imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * Requesting storage permission
     * Once the permission granted, screenshot captured
     * On permanent denial show toast
     */
    private fun requestPermissionAndSave() {
        Dexter.withActivity(this)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {
                    if (bitmap != null) {
                        val path =
                            Environment.getExternalStorageDirectory().toString() + "/test.png"
                        FileUtil.getInstance()?.storeBitmap(bitmap!!, path)
                        Toast.makeText(
                            this@VideoViewActivity,
                            getString(R.string.toast_message_screenshot_success) + " " + path,
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@VideoViewActivity,
                            getString(R.string.toast_message_screenshot),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                    // check for permanent denial of permission
                    if (response.isPermanentlyDenied) {
                        Toast.makeText(
                            this@VideoViewActivity,
                            getString(R.string.settings_message),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }


    private fun startPictureInPictureFeature() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && binding.videoView.isPlaying) {
            val aspectRatio =
                Rational(binding.videoView.width, binding.videoView.height);
            pictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
            enterPictureInPictureMode(pictureInPictureParamsBuilder.build());
            updatePictureInPictureActions(
                R.drawable.ic_pause,
                R.string.pause.toString(),
                viewModel.CONTROL_TYPE_PAUSE,
                viewModel.REQUEST_PAUSE
            )
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        if (isInPictureInPictureMode) {
            mReceiver = object : BroadcastReceiver() {
                @RequiresApi(Build.VERSION_CODES.M)
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent == null
                        || viewModel.ACTION_MEDIA_CONTROL != intent.action
                    ) {
                        return
                    }

                    // This is where we are called back from Picture-in-Picture action
                    // items.
                    val controlType = intent.getIntExtra(viewModel.EXTRA_CONTROL_TYPE, 0)
                    when (controlType) {
                        viewModel.CONTROL_TYPE_PLAY -> {
                            updatePictureInPictureActions(
                                R.drawable.ic_pause,
                                R.string.pause.toString(),
                                viewModel.CONTROL_TYPE_PAUSE,
                                viewModel.REQUEST_PAUSE
                            )
                            binding.videoView.start()
                        }
                        viewModel.CONTROL_TYPE_PAUSE -> {
                            updatePictureInPictureActions(
                                R.drawable.ic_play,
                                R.string.play.toString(),
                                viewModel.CONTROL_TYPE_PLAY,
                                viewModel.REQUEST_PLAY
                            )
                            binding.videoView.pause()
                        }
                    }
                }
            }
            registerReceiver(mReceiver, IntentFilter(viewModel.ACTION_MEDIA_CONTROL))
            actionBar?.hide()
            binding.floatingActionPipBtn.visibility = View.GONE
            binding.tvVideoView.visibility = View.GONE
        } else {
            unregisterReceiver(mReceiver)
            mReceiver = null
            binding.floatingActionPipBtn.visibility = View.VISIBLE
            binding.tvVideoView.visibility = View.VISIBLE
        }
    }


    /**
     * Update the state of pause/resume action item in Picture-in-Picture mode.
     *
     * @param iconId The icon to be used.
     * @param title The title text.
     * @param controlType The type of the action. either [.CONTROL_TYPE_PLAY] or [     ][.CONTROL_TYPE_PAUSE].
     * @param requestCode The request code for the [PendingIntent].
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun updatePictureInPictureActions(
        @DrawableRes iconId: Int, title: String?, controlType: Int, requestCode: Int
    ) {
        val actions: ArrayList<RemoteAction> = ArrayList()

        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        val intent = PendingIntent.getBroadcast(
            this,
            requestCode,
            Intent(viewModel.ACTION_MEDIA_CONTROL).putExtra(
                viewModel.EXTRA_CONTROL_TYPE,
                controlType
            ),
            0
        )
        val icon: Icon = Icon.createWithResource(this, iconId)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            actions.add(RemoteAction(icon, title!!, title, intent))
            val pictureInPictureParamsBuilder =
                PictureInPictureParams.Builder()
            pictureInPictureParamsBuilder.setActions(actions)
            setPictureInPictureParams(pictureInPictureParamsBuilder.build())
        }

//        // Another action item. This is a fixed action.
//        actions.add(
//            RemoteAction(
//                Icon.createWithResource(this, R.drawable.ic_info),
//                getString(R.string.info),
//                getString(R.string.info_description),
//                PendingIntent.getActivity(
//                    this,
//                    viewModel.REQUEST_INFO,
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse(getString(R.string.info))
//                    ),
//                    0
//                )
//            )
//        )

    }


    override fun onNewIntent(i: Intent) {
        super.onNewIntent(i)
        updateVideoView(i)
    }

    private fun updateVideoView(i: Intent) {
        val videoUrl = i.getStringExtra("videoUrl")
        binding.videoView.setVideoURI(Uri.parse(videoUrl))
        binding.videoView.requestFocus()
    }

    //bring up launcher task to front
    private fun navToLauncherTask() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (task in activityManager.appTasks) {
            if (this.componentName != task.taskInfo.baseIntent.component) {
                task.moveToFront()
                return
            }
        }
    }

    override fun onBackPressed() {
        if (viewModel.backStackLost) {
            navToLauncherTask()
        } else super.onBackPressed()
    }

}