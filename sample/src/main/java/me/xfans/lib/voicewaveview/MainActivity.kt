package me.xfans.lib.voicewaveview

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity

class MainActivity : AppCompatActivity() {
    var voiceWaveView0: VoiceWaveView? = null
    var voiceWaveView1: VoiceWaveView? = null
    var voiceWaveView2: VoiceWaveView? = null
    var voiceWaveView3: VoiceWaveView? = null
    var voiceWaveView4: VoiceWaveView? = null
    var voiceWaveView5: VoiceWaveView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        voiceWaveView0 = findViewById<VoiceWaveView>(R.id.voiceWaveView0)
        voiceWaveView1 = findViewById<VoiceWaveView>(R.id.voiceWaveView1)
        voiceWaveView2 = findViewById<VoiceWaveView>(R.id.voiceWaveView2)
        voiceWaveView3 = findViewById<VoiceWaveView>(R.id.voiceWaveView3)
        voiceWaveView4 = findViewById<VoiceWaveView>(R.id.voiceWaveView4)
        voiceWaveView5 = findViewById<VoiceWaveView>(R.id.voiceWaveView5)

        voiceWaveView0?.apply {
            duration = 150
            addHeader(4)
            addHeader(14)
            addBody(27)
            addBody(17)
            addBody(38)
            addBody(91)
            addBody(38)
            addBody(24)
            addBody(8)
            addBody(60)
            addBody(38)
            addBody(14)
            addBody(8)
            addFooter(4)
            addFooter(2)
            start()
        }
        voiceWaveView1?.apply {
            showGravity = Gravity.RIGHT or Gravity.TOP
            waveMode = WaveMode.UP_DOWN
            lineWidth = 30f
            lineSpace = 15f
            duration = 500
            lineColor = Color.parseColor("#F56B00")
            addHeader(4)
            addHeader(14)
            addBody(27)
            addBody(17)
            addBody(38)
            addBody(91)
            addBody(38)
            addBody(24)
            addBody(8)
            addBody(60)
            addBody(38)
            addBody(14)
            addBody(8)
            addFooter(4)
            addFooter(2)
            start()
        }
        voiceWaveView2?.apply {

            addBody(14)
            addBody(27)
            addBody(17)
            addBody(38)
            addBody(91)
            addBody(38)
            addBody(24)
            addBody(8)
            addBody(60)
            addBody(38)
            addBody(14)
            addBody(8)
            addBody(4)
            addBody(2)
            addBody(2)
            start()
        }
        voiceWaveView3?.apply {

            addHeader(14)
            addBody(27)
            addBody(17)
            addBody(38)
            addBody(91)
            addBody(38)
            addBody(24)
            addBody(8)
            addBody(60)
            addBody(38)
            addBody(14)
            addBody(8)
            addFooter(4)
            addFooter(2)
            start()
        }
        voiceWaveView4?.apply {

            addHeader(14)
            addBody(27)
            addBody(17)
            addBody(38)
            addBody(91)
            addBody(38)
            addBody(24)
            addBody(8)
            addBody(60)
            addBody(38)
            addBody(14)
            addBody(8)
            addFooter(4)
            addFooter(2)
            start()
        }
        voiceWaveView5?.apply {

            addHeader(14)
            addBody(27)
            addBody(17)
            addBody(38)
            addBody(91)
            addBody(38)
            addBody(24)
            addBody(8)
            addBody(60)
            addBody(38)
            addBody(14)
            addBody(8)
            addFooter(4)
            addFooter(2)
            start()
        }
    }

    override fun onStop() {
        super.onStop()
        voiceWaveView0?.stop()
        voiceWaveView1?.stop()
        voiceWaveView2?.stop()
        voiceWaveView3?.stop()
        voiceWaveView4?.stop()
        voiceWaveView5?.stop()
    }
}
