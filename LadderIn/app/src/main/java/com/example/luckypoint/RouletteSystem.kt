package com.example.luckypoint

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import java.lang.RuntimeException

class RouletteSystem @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //원의 모양, 색, 내부 텍스트를 그릴 페인트 선언
    private val drawPaint = Paint()
    private val fillPaint = Paint()
    private val textPaint = Paint()

    //
    var rouletteDataList = mutableListOf("꽝","당첨","한번더!","당첨","꽝"," 한번더!","당첨","한번더!")

    //룰렛의 조각을 나눌 변수로서 dataList의 크기 만큼 분할
    private var rouletteSize = rouletteDataList.size

    //룰렛의 크기, 색 등 초기 디자인 설정
    private var textSize = 0f
    private var textColor = Color.BLACK
    private var rouletteBorderLineColor = Color.BLACK
    private var rouletteBorderLineWidth = 0f
    private var textStyle = Typeface.BOLD

    init {
        val typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.RouletteSystem,
                defStyleAttr,
                0
        )

        rouletteBorderLineColor = typedArray.getColor(
                R.styleable.RouletteSystem_rouletteBorderLineColor,
                Color.BLACK
        )

        rouletteBorderLineWidth = typedArray.getDimension(
                R.styleable.RouletteSystem_rouletteBorderLineWidth,
                20f
        )

        textColor = typedArray.getColor(
                R.styleable.RouletteSystem_textColor,
                Color.BLACK
        )

        textSize = typedArray.getDimension(
                R.styleable.RouletteSystem_textSize,
                60f
        )

        typedArray.recycle()

        drawPaint.apply {
            //검은색으로 비어있는 원을 13F의 크기로 부드럽게 생성
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeWidth = 13f
            isAntiAlias = true
        }

        fillPaint.apply {
            //원의 색을 채우기
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        textPaint.apply {
            //룰렛 내부에 텍스트를 검은색으로 중앙 정렬, 굵게 60F 크기로 그리기
            color = Color.BLACK
            textSize = 60f
            textAlign = Paint.Align.CENTER
            textStyle = Typeface.BOLD
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /*크기 설정 : 룰렛을 뷰의 중앙에 그리기 위해 top을 뷰의 중앙에 가로 반만큼 위로
        bottom은 중앙에서 가로의 반만큼 아래로 설정*/
        val rectLeft = left.toFloat() + paddingLeft
        val rectRight = right.toFloat() - paddingRight
        val rectTop = height / 2f - rectRight / 2f + paddingTop
        val rectBottom = height / 2f + rectRight / 2f - paddingRight
        val rectF = RectF(rectLeft, rectTop, rectRight, rectBottom)

        //설정 값에 따라 룰렛 그리기
        drawRoulette(canvas, rectF)
    }

    private fun drawRoulette(canvas: Canvas?, rectF: RectF) {
        //룰렛을 그리는 함수
        canvas?.drawArc(rectF, 0f, 360f, true, drawPaint)

        if(rouletteSize in 2..8) { //룰렛의 사이즈는 최소 2에서 최대 8까지 설정

            //룰렛의 회전각도 (360/X)를 담는 변수
            val sweepAngle = 360f / rouletteSize.toFloat()

            //원의 가운데에 맞춰 나눠지도록 /2를 통해 룰렛 조각의 크기 설정
            val centerX = (rectF.left + rectF.right) / 2
            val centerY = (rectF.top + rectF.bottom) / 2
            val radius = (rectF.right - rectF.left) /2 * 0.5

            //룰렛 조각에 맞춰질 색상의 리스트 선언
            val colors = listOf("#fe4a49", "#2ab7ca", "#fed766", "#e6e6ea", "#f6abb6", "#005b96", "#7bc043", "#f37735")

            for(i in 0 until rouletteSize) {
                //룰렛의 크기만큼 순회하여 colorsList에 있는 색상을 적용
                fillPaint.color = Color.parseColor(colors[i])

                //배열 요소 개수만큼 그리기를 시작하는데, useCenter true 설정해야 가득 채워짐
                val startAngle = if(i==0) 0f else sweepAngle * i
                canvas?.drawArc(rectF,startAngle,sweepAngle,true,fillPaint)

                //룰렛 내부의 중앙 각도를 담는 변수
                val medianAngle = (startAngle + sweepAngle / 2f) * Math.PI / 180f

                //부채꼴 모양으로 조각을 그리기위해 x값은 cos을 통해 좌표 저장
                val x = (centerX + (radius * kotlin.math.cos(medianAngle))).toFloat()

                //y값은 피타고라스의 정리에 따라 sin 이용하여 좌표를 저장
                val y = (centerY + (radius * kotlin.math.sin(medianAngle))).toFloat() + Constant.DEFAULT_PADDING

                //만일 dataList 만큼 텍스트가 없다면 빈 공간은 empty로 처리
                val text = if(i>rouletteDataList.size - 1) "empty" else rouletteDataList[i]
                canvas?.drawText(text,x,y,textPaint)
            }
        } else throw RuntimeException("Size out")
    }

    fun rotateRoulette(toDegrees: Float, duration: Long, rotateListener: RotateListener?) {
        val animListener = object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                //애니메이션 시작 시 룰렛 회전 시작
                rotateListener?.onRotateStart()
            }

            override fun onAnimationEnd(animation: Animation?) {
                //애니메이션 종료 시 룰렛의 결과 값을 가져오기
                rotateListener?.onRotateEnd(getRouletteRotateResult(toDegrees))
            }
        }

        val rotateAnim = RotateAnimation(
            //룰렛의 회전 시작 각도를 0F로 설정하고 애니메이션이 제자리에서 얼만큼 동작할지 지정
                0f, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,0.5f
        )

        //애니메이션 지속 시간으로 각도와 시간에 따라 회전 속도가 바뀜
        rotateAnim.duration = duration

        //애니메이션이 종료된 상태를 유지
        rotateAnim.fillAfter = true
        rotateAnim.setAnimationListener(animListener)

        startAnimation(rotateAnim)
    }

    private fun getRouletteRotateResult(degrees: Float): String {
        val moveDegrees = degrees % 360

        //결과 값을 반환할 각도에 대해 변수로 지정
        //회전 각도에서 moveDegrees의 나머지를 통해 결과 값을 반환할 각도를 지정
        val resultAngle = if(moveDegrees > 270) 360 - moveDegrees + 270 else 270 - moveDegrees
        for (i in 1..rouletteSize) {
            if(resultAngle < (360 / rouletteSize) * i) {
                //배열의 인덱스 값 반환을 위해 i-1
                if(i-1 >= rouletteDataList.size) {
                    /*해당 인덱스보다 룰렛 데이터 리스트 사이즈가 더 크면 그 인덱스에
                    값이 없다는 것으로 empty를 반환 */
                    return "empty"
                }
                return rouletteDataList[i-1]
            }
        }
        return ""
    }

    /* 룰렛을 그리는 데에 필요한 글씨 크기, 색, 선의 색, 굵기에 대한
    getter, setter 요소를 추가하여 속성을 바꾸기 가능 */
    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        invalidate()
    }

    fun getTextSize(): Float = textSize

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        invalidate()
    }

    fun getTextColor(): Int = textColor

    fun setRouletteBorderLineColor(borderLineColor: Int) {
        this.rouletteBorderLineColor = borderLineColor
        invalidate()
    }

    fun getRouletteBorderLineColor(): Int = rouletteBorderLineColor

    fun setRouletteBorderLineWidth(width: Float) {
        rouletteBorderLineWidth = width
        invalidate()
    }

    fun getRouletteBorderLineWidth(): Float = rouletteBorderLineWidth
}