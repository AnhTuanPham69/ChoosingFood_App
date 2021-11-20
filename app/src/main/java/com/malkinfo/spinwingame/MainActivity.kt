package com.malkinfo.spinwingame

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.malkinfo.spinwingame.data.database.model.Food

class MainActivity : AppCompatActivity() ,Animation.AnimationListener{

    private var count = 0
    private var flag = false

    private var powerButton: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**get Id*/
        powerButton = findViewById(R.id.powerButton)
        powerButton!!.setOnTouchListener(PowerTouchListener())
        intSpinner()


    }

    /**
     * All the vars you need
     * */
    val prizes = arrayOf<Food>( Food(1,R.drawable.banhtrangtron,"Bánh Tráng Trộn"),
                                Food(2,R.drawable.trasua,"Trà Sữa"),
                                Food(3,R.drawable.tratac,"Trà Tắc"),
                                Food(4,R.drawable.changasatac,"Chân Gà Sả Tắc"),
                                Food(5,R.drawable.bunchaca,"Bún Chả Cá"),
                                Food(6,R.drawable.hutieu,"Hủ Tiếu"),
                                Food(7,R.drawable.bunthitnuong,"Bún Thịt Nướng"),
                                Food(8,R.drawable.banhcanh,"Bánh Canh"),
                                Food(9,R.drawable.tomalaska,"Tôm Hùm Alaska"),
                                Food(10,R.drawable.trungvitlon,"Trứng Vịt Lộn"),
                                Food(11,R.drawable.tradaocamsa,"Trà Đào Cam Sả"),
                                Food(12,R.drawable.yagurt,"Yagurt Xoài Đá Xay")
                                )
    private var mSpinDuration :Long = 0
    private var mSpinRevolution = 0f
    var pointerImageView:ImageView? = null
    var showFood:ImageView?= null
    var infoText: TextView? = null
    var prizeText = "N/A"
    var img = 0
    private fun intSpinner() {
        pointerImageView = findViewById(R.id.imageWheel)
        infoText = findViewById(R.id.infoText)
        showFood = findViewById(R.id.imageView)
    }


    fun startSpinner() {
        mSpinRevolution = 3600f
        mSpinDuration = 5000

        if (count >= 30){
            mSpinDuration = 1000
            mSpinRevolution = (3600 * 2).toFloat()
        }
        if (count >= 60){
            mSpinDuration = 15000
            mSpinRevolution = (3600 * 3).toFloat()
        }

        // Final point of rotation defined right here
        // 360 is the degree of the circle
        val end = Math.floor(Math.random() * 3600).toInt() // random : 0-360
        val numOfPrizes = prizes.size // quantity of prize
        val degreesPerPrize = 360/numOfPrizes // size of sector per prize in degrees
        val shift = 0 // shit where the arrow points
        val prizeIndex = (shift + end) % numOfPrizes

        prizeText = "Bạn đã chọn được món : \n ${prizes[prizeIndex].name}"
         img = prizes[prizeIndex].images

        val rotateAnim = RotateAnimation(
            0f,mSpinRevolution + end,
            Animation.RELATIVE_TO_SELF,
            0.5f,Animation.RELATIVE_TO_SELF,0.5f
        )
        rotateAnim.interpolator = DecelerateInterpolator()
        rotateAnim.repeatCount = 0
        rotateAnim.duration = mSpinDuration
        rotateAnim.setAnimationListener(this)
        rotateAnim.fillAfter = true
        pointerImageView!!.startAnimation(rotateAnim)

    }
    // When click on button
    override fun onAnimationStart(p0: Animation?) {
        infoText!!.text = "Đang chọn món..."
    }
    //When finished choosing food
    override fun onAnimationEnd(p0: Animation?) {
        infoText!!.text = prizeText
        showFood!!.setImageResource(img)
    }

    override fun onAnimationRepeat(p0: Animation?) {}

    private inner class PowerTouchListener: View.OnTouchListener {
        override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {

            when(motionEvent!!.action){
                MotionEvent.ACTION_DOWN ->{
                    flag = true
                    count = 0
                    Thread{
                        while (flag){
                            count++
                            if (count == 100){
                                try {
                                    Thread.sleep(100)
                                }catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }
                                count = 0
                            }
                            try {
                                Thread.sleep(10)
                            }
                            catch (e: InterruptedException){
                                e.printStackTrace()
                            }
                        }
                    }.start()
                    return true
                }
                MotionEvent.ACTION_UP ->{
                    flag = false
                    startSpinner()
                    return false
                }

            }


            return false
        }

    }
}