package great.sachin.to_dolist

import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_launcher.*

class LauncherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        val tick1 = tick1Animate.drawable
        val tick2 = tick2Animate.drawable
        val line1 = line1Animate.drawable
        val line2 = line2Animate.drawable
        val line3 = line3Animate.drawable

        if(line1 is AnimatedVectorDrawable){
            line1.start()
            Handler().postDelayed({
                if(line2 is AnimatedVectorDrawable)
                    line2.start()
            },500)
            Handler().postDelayed({
                if(line3 is AnimatedVectorDrawable)
                    line3.start()
            },800)
        }
        elipse.visibility = View.INVISIBLE;
        Handler().postDelayed({
            elipse.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in))
            elipse.visibility = View.VISIBLE;
        },1000)
        if(tick1 is AnimatedVectorDrawable){
            tick1.start()
            Handler().postDelayed({
                if(tick2 is AnimatedVectorDrawable)
                tick2.start()
            }, 500) ;
            Handler().postDelayed({
                startActivity(Intent (this, MainActivity::class.java))
                finish()
            },1500)
        }
    }
}
