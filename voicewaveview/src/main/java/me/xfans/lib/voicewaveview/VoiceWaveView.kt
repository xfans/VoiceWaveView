package me.xfans.lib.voicewaveview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.animation.ValueAnimator
import android.os.Handler
import android.view.Gravity
import java.util.*


class VoiceWaveView : View {

    var bodyWaveList = LinkedList<Int>()
    var headerWaveList = LinkedList<Int>()
    var footerWaveList = LinkedList<Int>()

    var waveList = LinkedList<Int>()

    var lineSpace: Float = 10f
    var lineWidth: Float = 20f
    var paintLine: Paint? = null
    var valueAnimator = ValueAnimator.ofFloat(0f, 1f)
    var valueAnimatorOffset: Float = 1f
    var valHandler = Handler()

    /**
     * 跳动模式1,2
     */
    var mode: Int = 1

    /**
     * 显示位置
     */
    var gravity: Int = Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL

    var runnable: Runnable? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        paintLine = Paint()
        paintLine?.setStrokeWidth(lineWidth)
        paintLine?.setAntiAlias(true)
        paintLine?.setColor(Color.parseColor("#EFF1F4"))
        paintLine?.strokeCap = Paint.Cap.ROUND
        bodyWaveList.add(8)
        bodyWaveList.add(14)
        bodyWaveList.add(27)
        bodyWaveList.add(17)
        bodyWaveList.add(38)
        bodyWaveList.add(91)
        bodyWaveList.add(38)
        bodyWaveList.add(24)
        bodyWaveList.add(8)
        bodyWaveList.add(60)
        bodyWaveList.add(38)
        bodyWaveList.add(14)
        bodyWaveList.add(8)
        bodyWaveList.add(8)

        addHeader()
        addFooter()
        start()
    }

    fun addHeader() {
        headerWaveList.add(8)
        headerWaveList.add(14)
    }

    fun addFooter() {
        footerWaveList.add(8)
        footerWaveList.add(8)
        footerWaveList.add(8)
    }

    fun start() {

        if (mode == 1) {
            valueAnimator.duration = 500
            valueAnimator.repeatMode = ValueAnimator.REVERSE
            valueAnimator.repeatCount = ValueAnimator.INFINITE
            valueAnimator.addUpdateListener {
                valueAnimatorOffset = it.getAnimatedValue() as Float
                invalidate()
            }
            valueAnimator.start()
        } else if (mode == 2) {
            runnable = object : Runnable {
                override fun run() {
                    val last = bodyWaveList.pollLast()
                    bodyWaveList.addFirst(last)
                    invalidate()
                    valHandler.postDelayed(this, 100);
                }
            }
            valHandler.post(runnable)
        } else if (mode == 3) {

        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        waveList.clear()
        waveList.addAll(headerWaveList)
        waveList.addAll(bodyWaveList)
        waveList.addAll(footerWaveList)

        for (i in waveList.indices) {
            var startX = 0f
            var startY = 0f
            var endX = 0f
            var endY = 0f

            var offset = 1f
            if (i >= headerWaveList.size && i < (waveList.size - footerWaveList.size)) {//模式1 ，排除掉头尾
                offset = valueAnimatorOffset
            }

            val lineHeight = waveList[i] / 100.0 * measuredHeight * offset

            val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);

            when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                Gravity.CENTER_HORIZONTAL -> {
                    val lineSize = waveList.size
                    val allLineWidth = lineSize * (lineSpace + lineWidth)
                    if (allLineWidth < measuredWidth) {
                        startX = (i * (lineSpace + lineWidth) + lineWidth / 2) + ((measuredWidth - allLineWidth) / 2)
                    } else {
                        startX = i * (lineSpace + lineWidth) + lineWidth / 2
                    }
                    endX = startX
                }

                Gravity.RIGHT -> {
                    val lineSize = waveList.size
                    val allLineWidth = lineSize * (lineSpace + lineWidth)
                    if (allLineWidth < measuredWidth) {
                        startX = (i * (lineSpace + lineWidth) + lineWidth / 2) + (measuredWidth - allLineWidth)
                    } else {
                        startX = i * (lineSpace + lineWidth) + lineWidth / 2
                    }
                    endX = startX
                }

                Gravity.LEFT -> {
                    startX = i * (lineSpace + lineWidth) + lineWidth / 2
                    endX = startX
                }
            }


            when (gravity and Gravity.VERTICAL_GRAVITY_MASK) {
                Gravity.TOP -> {
                    startY = 0f
                    endY = lineHeight.toFloat()
                }

                Gravity.CENTER_VERTICAL -> {
                    startY = (measuredHeight / 2 - lineHeight / 2).toFloat()
                    endY = (measuredHeight / 2 + lineHeight / 2).toFloat()
                }

                Gravity.BOTTOM -> {
                    startY = (measuredHeight - lineHeight).toFloat()
                    endY = measuredHeight.toFloat()
                }

            }

            canvas?.drawLine(
                startX,
                startY,
                endX,
                endY,
                paintLine
            )

        }
    }

    fun log() {

    }

    fun stop() {
        if (runnable != null) {
            handler.removeCallbacks(runnable)
        }
        valueAnimator.cancel()
    }
}