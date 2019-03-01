package com.example.mrtayyab.uogis

import android.app.ProgressDialog
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.widget.MediaController
import android.widget.VideoView

class VideoActivity : AppCompatActivity() {
    lateinit var mVideoView: VideoView
    lateinit var  player: MediaPlayer
    lateinit var mediaController: MediaController
    lateinit var pDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val toolbar = findViewById<View>(R.id.myToolbar) as Toolbar
        setSupportActionBar(toolbar);
        mediaController = MediaController(this)
        mVideoView = findViewById(R.id.myVideoView2)

        val videolink = intent.extras!!.getString("videoPath")



        pDialog = ProgressDialog(this)
//        // Set progressbar title
        pDialog.setTitle("Video playing")
        // Set progressbar message
        pDialog.setMessage("Buffering...")
        pDialog.isIndeterminate = false
        pDialog.setCancelable(false)
        // Show progressbar
        pDialog.show()


        try {
            // Start the MediaController
            val mediacontroller = MediaController(this)
            mediacontroller.setAnchorView(mVideoView)
            // Get the URL from String VideoURL
            val video = Uri.parse(videolink)
            mVideoView.setMediaController(mediacontroller)
            mVideoView.setVideoURI(video)

        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        mVideoView.requestFocus()
        mVideoView.setOnPreparedListener {

            pDialog.dismiss()
            mVideoView.start()
        }

    }
}
