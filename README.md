# VoiceWaveView
a voice wave view library
一个音频条形图的view,支持柱状音频，折线音频的样式

## Screenshot
![Screenshot2](pic/3.gif)![Screenshot1](pic/1.gif)

## Dependencies

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
implementation 'com.github.xfans:VoiceWaveView:1.0.2'
```
## Use

属性

 * `addHeader` - 添加图头的线(头尾的线不变化),百分数1-100，实际高度等于布局的`高度*百分数`
 * `addFooter`  - 添加图尾的线(头尾的线不变化)百分数1-100，实际高度等于布局的`高度*百分数`
 * `addBody` - 添加图上的线,百分数1-100
 * `lineSpace` - 线间距
 * `lineWidth` - 线宽
 * `duration` - 动画持续时间
 * `waveMode` - 线条动画模式，`up_down`上线变化，`left_right`从左往右移动
 * `lineType` - 线条样式，`line_graph`折线样式，`bar_chart`柱状样式
 * `lineColor` - 线条颜色
 * `gravity` - 图的位置，`center`，`bottom`等，同androd gravity

```
<me.xfans.lib.voicewaveview.VoiceWaveView
                android:id="@+id/voiceWaveView0"
                android:layout_width="wrap_content"
                android:layout_height="150dp"
                app:lineColor="@color/colorPrimary"
                app:waveMode="left_right"
                android:gravity="center"
        />
```
```
voiceWaveView?.apply {
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
```
