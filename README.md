# To-DoList
This is a to do List.
Concepts used for the project 
1.	Changing the Launcher icon 
I made the icon using Adobe XD 
And then I grouped it up and exported it in png format

In Android Studio:
1.1	I went to ic_launcher.xml there I changed the legacy foreground image to my exported png
1.2	And the background to white

2.	Animation in the android studio
I used three resources for it 
1.1 The icon I made in the Adobe XD is first exported in the form of    svg
1.2 Then  I added the effect by using the https://shapeshifter.design/ website 
1.3 I then dragged every item after exporting from the website 
1.4 I set the images as ImageView, gave them a id and declared the variable 
val tick1 = tick1Animate.drawable
val tick2 = tick2Animate.drawable
val line1 = line1Animate.drawable
val line2 = line2Animate.drawable
val line3 = line3Animate.drawable

1.5 Then I included the 
       
 vectorDrawables.useSupportLibrary = true
1.6 After that I declared the elipse and added the fade animation      into it directly
1.6.1 First I set the visibililty to Invisible then after the animation I made it visible
elipse.visibility = View.INVISIBLE;
Handler().postDelayed({
    elipse.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in))
    elipse.visibility = View.VISIBLE;
},1000)

1.7  Then I set the lines to animate in sequence like line1 will be animate in the start after 500ms the line2 and after 800ms the (third line) line3
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
1.8 Then the tick animation is declared at intervals of 500ms and 1000ms repectively
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

