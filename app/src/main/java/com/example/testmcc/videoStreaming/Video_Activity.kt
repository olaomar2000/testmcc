package com.example.testmcc.videoStreaming

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.testmcc.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import kotlinx.android.synthetic.main.activity_video.*


class Video_Activity : AppCompatActivity() {

    var player: SimpleExoPlayer? = null
    var videoURL = "https://firebasestorage.googleapis.com/v0/b/push-notifications-32c15.appspot.com/o/video%2FNature%20Beautiful%20short%20video%20720p%20HD.mp4?alt=media&token=63c211c1-61cb-4dea-b44f-f5844590da71"
    var playWhenReady = true
    var currentWindow = 0
    var playBackPosition: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
    }

      fun initVideo(){
          player = SimpleExoPlayer.Builder(this).build()
          video_view.player = player

          val mediaItem = MediaItem.Builder()
              .setUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
              .setMimeType(MimeTypes.APPLICATION_MP4)
              .build()

          // Create a media source and pass the media item
          val mediaSource = ProgressiveMediaSource.Factory(
              DefaultDataSource.Factory(this) // <- context
          )
              .createMediaSource(mediaItem)
          player!!.playWhenReady = playWhenReady
          player!!.seekTo(currentWindow, playBackPosition)
          player!!.prepare(mediaSource, false, false)

      }
    fun releaseVideo(){
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playBackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.release()
            player = null
        }
    }

    override fun onStart() {
        super.onStart()
        initVideo()
    }


    override fun onStop() {
        super.onStop()
        releaseVideo()
    }

    override fun onPause() {
        super.onPause()
        releaseVideo()
    }
}