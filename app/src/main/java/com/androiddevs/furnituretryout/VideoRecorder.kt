package com.androiddevs.furnituretryout

import android.app.Activity
import android.content.res.Configuration
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

    fun toggleRecordingState(): Boolean {
        if(isRecording) {
            stopRecording()
        } else {
            startRecording()
        }
        return isRecording
    }

    private fun startRecording() {
        setupMediaRecorder()
        encoderSurface = mediaRecorder?.surface
        sceneView.startMirroringToSurface(encoderSurface, 0, 0, videoSize.width, videoSize.height)
        isRecording = true
    }

    private fun stopRecording() {
        encoderSurface?.let {
            sceneView.stopMirroringToSurface(encoderSurface)
            encoderSurface = null
        }
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        isRecording = false
    }

    fun setVideoQuality(quality: Int, orientation: Int) {
        var profile: CamcorderProfile? = null
        if(CamcorderProfile.hasProfile(quality)) {
            profile = CamcorderProfile.get(quality)
        } else {
            for(level in qualityLevels) {
                if(CamcorderProfile.hasProfile(level)) {
                    profile = CamcorderProfile.get(level)
                    break
                }
            }
        }
        profile?.let {
            if(orientation == Configuration.ORIENTATION_LANDSCAPE) {
                videoSize = Size(profile.videoFrameWidth, profile.videoFrameHeight)
            } else {
                videoSize = Size(profile.videoFrameHeight, profile.videoFrameWidth)
            }
            videoEncoder = profile.videoCodec
            bitRate = profile.videoBitRate
            frameRate = profile.videoFrameRate
        }
    }
}




