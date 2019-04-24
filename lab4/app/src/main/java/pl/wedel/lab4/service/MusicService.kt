package pl.wedel.lab4.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import pl.wedel.lab4.R


class MusicService : Service(), MediaPlayer.OnErrorListener {

    private val binder = ServiceBinder()
    internal var mediaPlayer: MediaPlayer? = null
    private var length = 0

    override fun onError(mp: MediaPlayer, what: Int, extra: Int): Boolean {
        return false
    }

    inner class ServiceBinder : Binder() {
        internal val service: MusicService
            get() = this@MusicService
    }

    override fun onBind(arg0: Intent): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()

        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer!!.setOnErrorListener(this)

        if (mediaPlayer != null) {
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.setVolume(50f, 50f)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        mediaPlayer!!.start()
        return Service.START_NOT_STICKY
    }

    fun pauseMusic() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            length = mediaPlayer!!.currentPosition
        }
    }

    fun resumeMusic() {
        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.seekTo(length)
            mediaPlayer!!.start()
        }
    }

    fun stopMusic() {
        mediaPlayer!!.stop()
        mediaPlayer!!.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer != null) {
            try {
                mediaPlayer!!.stop()
                mediaPlayer!!.release()
            } finally {
                mediaPlayer = null
            }
        }
    }


}
