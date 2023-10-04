package com.android.sellacha.helper.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.NinePatchDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.android.sellacha.R
import com.android.sellacha.utils.MyUtils
import java.util.*

/**
 * Created by Dacer on 11/4/13.
 * Edited by Lee youngchan 21/1/14
 * Edited by dector 30-Jun-2014
 */
class LineView @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null) :
    View(context, attrs) {
    private val bottomTriangleHeight = 12
    private val popupTopPadding = MyUtils.dip2px(getContext(), 2f)
    private val popupBottomMargin = MyUtils.dip2px(getContext(), 5f)
    private val bottomTextTopMargin = MyUtils.sp2px(getContext(), 5f)
    private val bottomLineLength = MyUtils.sp2px(getContext(), 22f)
    private val DOT_INNER_CIR_RADIUS = MyUtils.dip2px(getContext(), 2f)
    private val DOT_OUTER_CIR_RADIUS = MyUtils.dip2px(getContext(), 5f)
    private val MIN_TOP_LINE_LENGTH = MyUtils.dip2px(getContext(), 12f)
    private val MIN_VERTICAL_GRID_NUM = 4
    private val MIN_HORIZONTAL_GRID_NUM = 1
    private val BACKGROUND_LINE_COLOR = Color.parseColor("#EEEEEE")
    private val BOTTOM_TEXT_COLOR = Color.parseColor("#9B9A9B")
    private val tmpPoint = Point()
    var showPopup = true
    private var mViewHeight = 0
    private val autoSetDataOfGird = true
    private val autoSetGridWidth = true
    private var dataOfAGird = 10
    private var bottomTextHeight = 0
    private var bottomTextList: ArrayList<String>? = ArrayList()
    private var dataLists: ArrayList<ArrayList<Float>>? = null
    private val xCoordinateList = ArrayList<Int>()
    private val yCoordinateList = ArrayList<Int>()
    private val drawDotLists: ArrayList<ArrayList<Dot>>? = ArrayList()
    private val bottomTextPaint = Paint()
    private var bottomTextDescent = 0
    private val popupTextPaint = Paint()
    private var showFloatNumInPopup = false
    private var pointToSelect: Dot? = null
    private var selectedDot: Dot? = null
    private val popupBottomPadding = MyUtils.dip2px(getContext(), 2f)

    /*
          |  | ←topLineLength
        --+--+--+--+--+--+--
        --+--+--+--+--+--+--
         ↑sideLineLength
     */
    private var topLineLength = 80
    private var sideLineLength = 40
    private var backgroundGridWidth = MyUtils.dip2px(getContext(), 45f)
    private var showPopupType = SHOW_POPUPS_NONE
    private var drawDotLine = false
    private var colorArray = intArrayOf(
        Color.parseColor("#e74c3c"), Color.parseColor("#2980b9"), Color.parseColor("#1abc9c")
    )
    private val animator: Runnable = object : Runnable {
        override fun run() {
            var needNewFrame = false
            for (data in drawDotLists!!) {
                for (dot in data) {
                    dot.update()
                    if (!dot.isAtRest) {
                        needNewFrame = true
                    }
                }
            }
            if (needNewFrame) {
                postDelayed(this, 25)
            }
            invalidate()
        }
    }

    init {
        popupTextPaint.isAntiAlias = true
        popupTextPaint.color = Color.WHITE
        popupTextPaint.textSize = MyUtils.sp2px(getContext(), 13f).toFloat()
        popupTextPaint.strokeWidth = 5f
        popupTextPaint.textAlign = Paint.Align.CENTER
        bottomTextPaint.isAntiAlias = true
        bottomTextPaint.textSize = MyUtils.sp2px(getContext(), 12f).toFloat()
        bottomTextPaint.textAlign = Paint.Align.CENTER
        bottomTextPaint.style = Paint.Style.FILL
        bottomTextPaint.color = BOTTOM_TEXT_COLOR
        refreshTopLineLength()
    }

    fun setShowPopup(popupType: Int) {
        showPopupType = popupType
    }

    fun setDrawDotLine(drawDotLine: Boolean) {
        this.drawDotLine = drawDotLine
    }

    fun setColorArray(colors: IntArray) {
        colorArray = colors
    }

    /**
     * dataList will be reset when called is method.
     *
     * @param bottomTextList The String ArrayList in the bottom.
     */
    fun setBottomTextList(bottomTextList: ArrayList<String>) {
        this.bottomTextList = bottomTextList
        val r = Rect()
        var longestWidth = 0
        var longestStr: String? = ""
        bottomTextDescent = 0
        for (s in bottomTextList) {
            bottomTextPaint.getTextBounds(s, 0, s.length, r)
            if (bottomTextHeight < r.height()) {
                bottomTextHeight = r.height()
            }
            if (autoSetGridWidth && longestWidth < r.width()) {
                longestWidth = r.width()
                longestStr = s
            }
            if (bottomTextDescent < Math.abs(r.bottom)) {
                bottomTextDescent = Math.abs(r.bottom)
            }
        }
        if (autoSetGridWidth) {
            if (backgroundGridWidth < longestWidth) {
                backgroundGridWidth =
                    longestWidth + bottomTextPaint.measureText(longestStr, 0, 1).toInt()
            }
            if (sideLineLength < longestWidth / 2) {
                sideLineLength = longestWidth / 2
            }
        }
        refreshXCoordinateList(horizontalGridNum)
    }

    /**
     * @param dataLists The Float ArrayLists for showing,
     * dataList.size() must be smaller than bottomTextList.size()
     */
    fun setFloatDataList(dataLists: ArrayList<ArrayList<Float>>) {
        setFloatDataList(dataLists, true)
    }

    fun setDataList(dataLists: ArrayList<ArrayList<Int>>) {
        val newList = ArrayList<ArrayList<Float>>()
        for (list in dataLists) {
            val tempList = ArrayList<Float>()
            for (i in list) {
                tempList.add(i.toFloat())
            }
            newList.add(tempList)
        }
        setFloatDataList(newList, false)
    }

    fun setFloatDataList(
        dataLists: ArrayList<ArrayList<Float>>,
        showFloatNumInPopup: Boolean
    ) {
        selectedDot = null
        this.showFloatNumInPopup = showFloatNumInPopup
        this.dataLists = dataLists
        for (list in dataLists) {
            if (list.size > bottomTextList!!.size) {
                throw RuntimeException(
                    "dacer.LineView error:" + " dataList.size() > bottomTextList.size() !!!"
                )
            }
        }
        var biggestData = 0f
        for (list in dataLists) {
            if (autoSetDataOfGird) {
                for (i in list) {
                    if (biggestData < i) {
                        biggestData = i
                    }
                }
            }
            dataOfAGird = 1
            while (biggestData / 10 > dataOfAGird) {
                dataOfAGird *= 10
            }
        }
        refreshAfterDataChanged()
        showPopup = true
        minimumWidth = 0 // It can help the LineView reset the Width,
        // I don't know the better way..
        postInvalidate()
    }

    private fun refreshAfterDataChanged() {
        val verticalGridNum = verticalGridlNum
        refreshYCoordinateList(verticalGridNum)
        refreshDrawDotList(verticalGridNum)
    }

    private val verticalGridlNum: Int
        private get() {
            var verticalGridNum = MIN_VERTICAL_GRID_NUM
            if (dataLists != null && !dataLists!!.isEmpty()) {
                for (list in dataLists!!) {
                    for (f in list) {
                        if (verticalGridNum < f + 1) {
                            verticalGridNum = Math.floor((f + 1).toDouble()).toInt()
                        }
                    }
                }
            }
            return verticalGridNum
        }
    private val horizontalGridNum: Int
        private get() {
            var horizontalGridNum = bottomTextList!!.size - 1
            if (horizontalGridNum < MIN_HORIZONTAL_GRID_NUM) {
                horizontalGridNum = MIN_HORIZONTAL_GRID_NUM
            }
            return horizontalGridNum
        }

    private fun refreshXCoordinateList(horizontalGridNum: Int) {
        xCoordinateList.clear()
        for (i in 0 until horizontalGridNum + 1) {
            xCoordinateList.add(sideLineLength + backgroundGridWidth * i)
        }
    }

    private fun refreshYCoordinateList(verticalGridNum: Int) {
        yCoordinateList.clear()
        for (i in 0 until verticalGridNum + 1) {
            yCoordinateList.add(
                topLineLength + (mViewHeight
                        - topLineLength
                        - bottomTextHeight
                        - bottomTextTopMargin
                        - bottomLineLength
                        - bottomTextDescent) * i / verticalGridNum
            )
        }
    }

    private fun refreshDrawDotList(verticalGridNum: Int) {
        if (dataLists != null && !dataLists!!.isEmpty()) {
            if (drawDotLists!!.size == 0) {
                for (k in dataLists!!.indices) {
                    drawDotLists.add(ArrayList())
                }
            }
            for (k in dataLists!!.indices) {
                val drawDotSize = if (drawDotLists[k].isEmpty()) 0 else drawDotLists[k].size
                for (i in dataLists!![k].indices) {
                    val x = xCoordinateList[i]
                    val y = getYAxesOf(dataLists!![k][i], verticalGridNum)
                    if (i > drawDotSize - 1) {
                        drawDotLists[k].add(Dot(x, 0f, x, y, dataLists!![k][i], k))
                    } else {
                        drawDotLists[k][i] = drawDotLists[k][i]
                            .setTargetData(x, y, dataLists!![k][i], k)
                    }
                }
                val temp = drawDotLists[k].size - dataLists!![k].size
                for (i in 0 until temp) {
                    drawDotLists[k].removeAt(drawDotLists[k].size - 1)
                }
            }
        }
        removeCallbacks(animator)
        post(animator)
    }

    private fun getYAxesOf(value: Float, verticalGridNum: Int): Float {
        return topLineLength + (mViewHeight
                - topLineLength
                - bottomTextHeight
                - bottomTextTopMargin
                - bottomLineLength
                - bottomTextDescent) * (verticalGridNum - value) / verticalGridlNum
    }

    private fun refreshTopLineLength() {
        // For prevent popup can't be completely showed when backgroundGridHeight is too small.
        topLineLength = popupHeight + DOT_OUTER_CIR_RADIUS + DOT_INNER_CIR_RADIUS + 2
    }

    override fun onDraw(canvas: Canvas) {
        drawBackgroundLines(canvas)
        drawLines(canvas)
        drawDots(canvas)
        for (k in drawDotLists!!.indices) {
            val maxValue = Collections.max(dataLists!![k])
            val minValue = Collections.min(dataLists!![k])
            for (d in drawDotLists[k]) {
                if (showPopupType == SHOW_POPUPS_All) {
                    drawPopup(
                        canvas, d.data, d.setupPoint(tmpPoint),
                        colorArray[k % colorArray.size]
                    )
                } else if (showPopupType == SHOW_POPUPS_MAXMIN_ONLY) {
                    if (d.data == maxValue) {
                        drawPopup(
                            canvas, d.data, d.setupPoint(tmpPoint),
                            colorArray[k % colorArray.size]
                        )
                    }
                    if (d.data == minValue) {
                        drawPopup(
                            canvas, d.data, d.setupPoint(tmpPoint),
                            colorArray[k % colorArray.size]
                        )
                    }
                }
            }
        }
        if (showPopup && selectedDot != null) {
            drawPopup(
                canvas, selectedDot!!.data, selectedDot!!.setupPoint(tmpPoint),
                colorArray[selectedDot!!.linenumber % colorArray.size]
            )
        }
    }

    /**
     * @param canvas The canvas you need to draw on.
     * @param point  The Point consists of the x y coordinates from left bottom to right top.
     * Like is
     *
     *
     * 3
     * 2
     * 1
     * 0 1 2 3 4 5
     */
    private fun drawPopup(canvas: Canvas, num: Float, point: Point, PopupColor: Int) {
        try {
            val numStr = if (showFloatNumInPopup) num.toString() else Math.round(num).toString()
            val singularNum = numStr.length == 1
            val sidePadding =
                MyUtils.dip2px(context, (if (singularNum) 8 else 5.toFloat()) as Float)
            val x = point.x
            val y = point.y - MyUtils.dip2px(context, 5f)
            val popupTextRect = Rect()
            popupTextPaint.getTextBounds(numStr, 0, numStr.length, popupTextRect)
            val r = Rect(
                x - popupTextRect.width() / 2 - sidePadding,
                (y
                        - popupTextRect.height()
                        - bottomTriangleHeight) - popupTopPadding * 2 - popupBottomMargin,
                x + popupTextRect.width() / 2 + sidePadding,
                y + popupTopPadding - popupBottomMargin + popupBottomPadding
            )
            val popup = resources.getDrawable(R.drawable.popup_white) as NinePatchDrawable
            popup.colorFilter = PorterDuffColorFilter(PopupColor, PorterDuff.Mode.MULTIPLY)
            popup.bounds = r
            popup.draw(canvas)
            canvas.drawText(
                numStr,
                x.toFloat(),
                (y - bottomTriangleHeight - popupBottomMargin).toFloat(),
                popupTextPaint
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private val popupHeight: Int
        private get() {
            val popupTextRect = Rect()
            popupTextPaint.getTextBounds("9", 0, 1, popupTextRect)
            val r = Rect(
                -popupTextRect.width() / 2,
                (-popupTextRect.height()
                        - bottomTriangleHeight) - popupTopPadding * 2 - popupBottomMargin,
                +popupTextRect.width() / 2,
                +popupTopPadding - popupBottomMargin + popupBottomPadding
            )
            return r.height()
        }

    private fun drawDots(canvas: Canvas) {
        try {
            val bigCirPaint = Paint()
            bigCirPaint.isAntiAlias = true
            val smallCirPaint = Paint(bigCirPaint)
            smallCirPaint.color = Color.parseColor("#FFFFFF")
            if (drawDotLists != null && drawDotLists.isNotEmpty()) {
                for (k in drawDotLists.indices) {
                    bigCirPaint.color = colorArray[k % colorArray.size]
                    for (dot in drawDotLists[k]) {
                        canvas.drawCircle(
                            dot.x.toFloat(),
                            dot.y,
                            DOT_OUTER_CIR_RADIUS.toFloat(),
                            bigCirPaint
                        )
                        canvas.drawCircle(
                            dot.x.toFloat(),
                            dot.y,
                            DOT_INNER_CIR_RADIUS.toFloat(),
                            smallCirPaint
                        )
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun drawLines(canvas: Canvas) {
        try {
            val linePaint = Paint()
            linePaint.isAntiAlias = true
            linePaint.strokeWidth = 7f
            for (k in drawDotLists!!.indices) {
                linePaint.color = colorArray[k % colorArray.size]
                for (i in 0 until drawDotLists[k].size - 1) {
                    canvas.drawLine(
                        drawDotLists[k][i].x.toFloat(), drawDotLists[k][i].y,
                        drawDotLists[k][i + 1].x.toFloat(), drawDotLists[k][i + 1].y,
                        linePaint
                    )
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun drawBackgroundLines(canvas: Canvas) {
        try {
            val paint = Paint()
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = MyUtils.dip2px(context, 1f).toFloat()
            paint.color = BACKGROUND_LINE_COLOR
            val effects: PathEffect = DashPathEffect(floatArrayOf(10f, 5f, 10f, 5f), 1f)

            //draw vertical lines
            for (i in xCoordinateList.indices) {
                canvas.drawLine(
                    xCoordinateList[i].toFloat(), 0f, xCoordinateList[i].toFloat(),
                    (
                            mViewHeight - bottomTextTopMargin - bottomTextHeight - bottomTextDescent).toFloat(),
                    paint
                )
            }

            //draw dotted lines
            paint.pathEffect = effects
            val dottedPath = Path()
            for (i in yCoordinateList.indices) {
                if ((yCoordinateList.size - 1 - i) % dataOfAGird == 0) {
                    dottedPath.moveTo(0f, yCoordinateList[i].toFloat())
                    dottedPath.lineTo(width.toFloat(), yCoordinateList[i].toFloat())
                    canvas.drawPath(dottedPath, paint)
                }
            }
            //draw bottom text
            if (bottomTextList != null) {
                for (i in bottomTextList!!.indices) {
                    canvas.drawText(
                        bottomTextList!![i], (sideLineLength + backgroundGridWidth * i).toFloat(),
                        (
                                mViewHeight - bottomTextDescent).toFloat(), bottomTextPaint
                    )
                }
            }

//        if (!drawDotLine) {
//            //draw solid lines
//            for (int i = 0; i < yCoordinateList.size(); i++) {
//                if ((yCoordinateList.size() - 1 - i) % dataOfAGird == 0) {
//                    canvas.drawLine(0, yCoordinateList.get(i), getWidth(), yCoordinateList.get(i),
//                            paint);
//                }
//            }
//        }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mViewWidth = measureWidth(widthMeasureSpec)
        mViewHeight = measureHeight(heightMeasureSpec)
        //        mViewHeight = MeasureSpec.getSize(measureSpec);
        refreshAfterDataChanged()
        setMeasuredDimension(mViewWidth, mViewHeight)
    }

    private fun measureWidth(measureSpec: Int): Int {
        val horizontalGridNum = horizontalGridNum
        val preferred = backgroundGridWidth * horizontalGridNum + sideLineLength * 2
        return getMeasurement(measureSpec, preferred)
    }

    private fun measureHeight(measureSpec: Int): Int {
        val preferred = 0
        return getMeasurement(measureSpec, preferred)
    }

    private fun getMeasurement(measureSpec: Int, preferred: Int): Int {
        val specSize = MeasureSpec.getSize(measureSpec)
        val measurement: Int
        measurement = when (MeasureSpec.getMode(measureSpec)) {
            MeasureSpec.EXACTLY -> specSize
            MeasureSpec.AT_MOST -> Math.min(preferred, specSize)
            else -> preferred
        }
        return measurement
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            pointToSelect = findPointAt(event.x.toInt(), event.y.toInt())
        } else if (event.action == MotionEvent.ACTION_UP) {
            if (pointToSelect != null) {
                selectedDot = pointToSelect
                pointToSelect = null
                postInvalidate()
            }
        }
        return true
    }

    private fun findPointAt(x: Int, y: Int): Dot? {
        if (drawDotLists!!.isEmpty()) {
            return null
        }
        val width = backgroundGridWidth / 2
        val r = Region()
        for (data in drawDotLists) {
            for (dot in data) {
                val pointX = dot.x
                val pointY = dot.y.toInt()
                r[pointX - width, pointY - width, pointX + width] = pointY + width
                if (r.contains(x, y)) {
                    return dot
                }
            }
        }
        return null
    }

    internal inner class Dot(
        var x: Int,
        var y: Float,
        targetX: Int,
        targetY: Float,
        data: Float,
        var linenumber: Int
    ) {
        var data = 0f
        var targetX = 0
        var targetY = 0f
        var velocity = MyUtils.dip2px(context, 18f)

        init {
            setTargetData(targetX, targetY, data, linenumber)
        }

        fun setupPoint(point: Point): Point {
            point[x] = y.toInt()
            return point
        }

        fun setTargetData(targetX: Int, targetY: Float, data: Float, linenumber: Int): Dot {
            this.targetX = targetX
            this.targetY = targetY
            this.data = data
            this.linenumber = linenumber
            return this
        }

        val isAtRest: Boolean
            get() = x == targetX && y == targetY

        fun update() {
            x = updateSelf(x.toFloat(), targetX.toFloat(), velocity).toInt()
            y = updateSelf(y, targetY, velocity)
        }

        private fun updateSelf(origin: Float, target: Float, velocity: Int): Float {
            var origin = origin
            if (origin < target) {
                origin += velocity.toFloat()
            } else if (origin > target) {
                origin -= velocity.toFloat()
            }
            if (Math.abs(target - origin) < velocity) {
                origin = target
            }
            return origin
        }
    }

    companion object {
        const val SHOW_POPUPS_All = 1
        const val SHOW_POPUPS_MAXMIN_ONLY = 2
        const val SHOW_POPUPS_NONE = 3
    }
}