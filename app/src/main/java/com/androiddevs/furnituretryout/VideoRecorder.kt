package com.androiddevs.furnituretryout

import android.app.Activity
import android.media.CamcorderProfile
import android.media.MediaRecorder
import android.util.Size
import android.view.Surface
import com.google.ar.sceneform.SceneView

class VideoRecorder(
    private val activity: Activity
) {

    private val qualityLevels = arrayOf(
        CamcorderProfile.QUALITY_HIGH,
        CamcorderProfile.QUALITY_2160P,
        CamcorderProfile.QUALITY_1080P,
        CamcorderProfile.QUALITY_720P,
        CamcorderProfile.QUALITY_480P
    )

    lateinit var sceneView: SceneView

    private var mediaRecorder: MediaRecorder? = null
    private lateinit var videoSize: Size

    private var videoEncoder = MediaRecorder.VideoEncoder.DEFAULT
    private var bitRate = 10000000
    private var frameRate = 30
    private var encoderSurface: Surface? = null

    private var isRecording = false

    private fun setupMediaRecorder() {
        if(mediaRecorder == null) {
            mediaRecorder = MediaRecorder()
        }
        mediaRecorder?.apply {
            setVideoSource(MediaRecorder.VideoSource.SURFACE)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setVideoEncodingBitRate(bitRate)
            setVideoFrameRate(frameRate)
            setVideoSize(videoSize.width, videoSize.height)
            setVideoEncoder(videoEncoder)
            prepare()
            start()
        }
    }
}




