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

class VoiceWaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle) {

    var bodyWaveList = LinkedList<Int>()
        private set
    var headerWaveList = LinkedList<Int>()
        private set
    var footerWaveList = LinkedList<Int>()
        private set

    private var waveList = LinkedList<Int>()

    /**
     * 线间距
     */
    var lineSpace: Float = 10f
    /**
     * 线宽
     */
    var lineWidth: Float = 20f
    /**
     * 线颜色
     */
    var lineColor: Int = Color.BLUE
    var paintLine: Paint? = null

    private var valueAnimator = ValueAnimator.ofFloat(0f, 1f)

    private var valueAnimatorOffset: Float = 1f

    private var valHandler = Handler()

    var isStart: Boolean = false
        private set

    /**
     * 跳动模式
     */
    var waveMode: WaveMode = WaveMode.UP_DOWN

    /**
     * 显示位置
     */
    var showGravity: Int = Gravity.LEFT or Gravity.BOTTOM

    private var runnable: Runnable? = null


    init {
        attrs?.let {
            val typedArray = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.VoiceWaveView, 0, 0
            )

            lineWidth = typedArray.getFloat(R.styleable.VoiceWaveView_lineWidth, 20f)
            lineSpace = typedArray.getFloat(R.styleable.VoiceWaveView_lineSpace, 10f)
            showGravity = typedArray.getInt(R.styleable.VoiceWaveView_android_gravity, Gravity.LEFT or Gravity.BOTTOM)
            lineColor = typedArray.getInt(R.styleable.VoiceWaveView_lineColor, Color.BLUE)
            val mode = typedArray.getInt(R.styleable.VoiceWaveView_waveMode, 0)
            when (mode) {
                0 -> waveMode = WaveMode.UP_DOWN
                1 -> waveMode = WaveMode.LEFT_RIGHT
            }
            typedArray.recycle()
        }

        paintLine = Paint()
        paintLine?.isAntiAlias = true
        paintLine?.strokeCap = Paint.Cap.ROUND
    }

    /**
     * 线的高度 0,100 百分数
     */
    fun addBody(num: Int) {
        checkNum(num)
        bodyWaveList.add(num)
    }

    /**
     * 头部线的高度 0,100 百分数
     */
    fun addHeader(num: Int) {
        checkNum(num)
        headerWaveList.add(num)
    }

    /**
     * 尾部线的高度 0,100 百分数
     */
    fun addFooter(num: Int) {
        checkNum(num)
        footerWaveList.add(num)
    }

    private fun checkNum(num: Int) {
        if (num < 0 || num > 100) {
            throw Exception("num must between 0 and 100")
        }
    }

    /**
     * 开始
     */
    fun start() {
        if (isStart) {
            return
        }
        isStart = true
        if (waveMode == WaveMode.UP_DOWN) {
            valueAnimator.duration = 500
            valueAnimator.repeatMode = ValueAnimator.REVERSE
            valueAnimator.repeatCount = ValueAnimator.INFINITE
            valueAnimator.addUpdateListener {
                valueAnimatorOffset = it.getAnimatedValue() as Float
                invalidate()
            }
            valueAnimator.start()
        } else if (waveMode == WaveMode.LEFT_RIGHT) {
            runnable = object : Runnable {
                override fun run() {
                    val last = bodyWaveList.pollLast()
                    bodyWaveList.addFirst(last)
                    invalidate()
                    valHandler.postDelayed(this, 100);
                }
            }
            valHandler.post(runnable)
        } else {

        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        waveList.clear()
        waveList.addAll(headerWaveList)
        waveList.addAll(bodyWaveList)
        waveList.addAll(footerWaveList)

        paintLine?.strokeWidth = lineWidth
        paintLine?.color = lineColor

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

            val absoluteGravity = Gravity.getAbsoluteGravity(showGravity, layoutDirection);

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


            when (showGravity and Gravity.VERTICAL_GRAVITY_MASK) {
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

    /**
     * 停止
     */
    fun stop() {
        isStart = false
        if (runnable != null) {
            valHandler.removeCallbacks(runnable)
        }
        valueAnimator.cancel()
    }
}