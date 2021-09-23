package com.example.luckypoint.slotmachine

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.luckypoint.R
import kotlinx.android.synthetic.main.img_view_slot.view.*

class ImageViewScrolling: FrameLayout {

    internal lateinit var eventEnd: EventEnd

    internal var last_result = 0
    internal var oldvalue=0

    val value: Int
        get() = Integer.parseInt(nextImg.tag.toString())
    fun setEventEnd(eventEnd: EventEnd)
    {
        this.eventEnd = eventEnd
    }

    constructor(context: Context): super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        //FrameLayout을 연결할 기존 Layout을 Inflate
        LayoutInflater.from(context).inflate(R.layout.img_view_slot,this)

        //nextImg가 세로 방향으로 위치 변동
        nextImg.translationY = height.toFloat()
    }

    fun setValueRandom(image: Int, num_rotate:Int) {
        //세로 방향으로 현재 이미지 위치 변동을 지정한 150의 Duration 만큼 적용
        currentImg.animate()
            .translationY((-height).toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        //현재 이미지 변동에 맞춰 다음 이미지 또한 세로 방향으로 위치 변동
        nextImg.translationY = nextImg.height.toFloat()

        nextImg.animate().translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object : Animator.AnimatorListener {
                //애니메이션 종료 시의 경우만 판단
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    //결과 이미지를 전체 값/전체 값 개수를 통해 value 받아오기
                    setImage(currentImg,oldvalue%6)

                    //결과 이미지의 움직임은 정지
                    currentImg.translationY = 0f

                    if(oldvalue != num_rotate){
                        //이전 값과 회전 횟수가 같지 않다면 다시 랜덤 값 추출
                        setValueRandom(image, num_rotate)
                        oldvalue++
                    }
                    else {
                        //결과 이미지 출력
                        last_result = 0
                        oldvalue = 0
                        setImage(nextImg,image)
                        eventEnd.eventEnd(image%6, num_rotate)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            }).start()
    }

    private fun setImage(img: ImageView?, value:Int) {
        when (value) {
            //Util Obj 각각의 id에 drawable 지정
            Util.bar -> img!!.setImageResource(R.drawable.bar_done)
            Util.lemon -> img!!.setImageResource(R.drawable.lemon_done)
            Util.orange -> img!!.setImageResource(R.drawable.orange_done)
            Util.seven -> img!!.setImageResource(R.drawable.sevent_done)
            Util.triple -> img!!.setImageResource(R.drawable.triple_done)
            Util.watermelon -> img!!.setImageResource(R.drawable.waternelon_done)
        }

        //결과 이미지 값 반환
        img!!.tag = value
        last_result = value
    }

    companion object {
        //150의 동작 시간
        private const val ANIMATION_DURATION = 150
    }
}