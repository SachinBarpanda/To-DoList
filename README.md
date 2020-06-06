### Concepts Used 
1.	Var is used to declare variable in class ToDoItems as it is a mutable list and Val is used in the Launcher Activity items to store items that can be initialised only once.

2.	Inheritance 
Use of the SQLiteOpenHelper class provides the functionality to use the SQLite database.

```class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION)```

3.	 Creating a class to store Items and their properties in the TODO List

4.	lateinit var is used in the MainActivity which is a non-null initializer  that cannot be supplied in the constructor, but the I was sure that the variable will not be null it, thus I avoided the null checks when referencing it later.
```
lateinit var dbHandler: DBHandler
```

5.	use of AlertDialog to get user input from edit text . without changing the screen. This input will be stored in the sqlite datebase and shown in the recyclerView.
```
val dialog = AlertDialog.Builder(this)
```
5.1.	setPositive and setNegative buttons are used to add or cancel the dialog input 
```
dialog.setPositiveButton("Add") 
dialog.setNegativeButton("Cancel”)
```

6.	Inheritance and Primary constructor
Constructor is called in the adapter view of the recycler adapter
And the super class ```RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()```
is inherited : 
```class MainRecyclerAdapter(context: Context,val list: MutableList<ToDoItems>) :
    RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>()
 ```
There the interfaces of the classes are called and overridden to be made useful in the app

```
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    TODO("Not yet implemented")
}

override fun getItemCount(): Int {
    return list.size
}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    TODO("Not yet implemented")
}
```

7.	Bang Bang Operator
The not-null assertion operator !! converts any value to a non-null type and throws an exception if the value is null.
```	checkBoxDue!!.isChecked=false

```




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

1.2 Then  I added the effect by using the``` https://shapeshifter.design/ ``` website 

1.3 I then dragged every item after exporting from the website 

1.4 I set the images as ImageView, gave them a id and declared the variable 
```
val tick1 = tick1Animate.drawable
val tick2 = tick2Animate.drawable
val line1 = line1Animate.drawable
val line2 = line2Animate.drawable
val line3 = line3Animate.drawable

```

1.5 Then I included the 
       
 ```vectorDrawables.useSupportLibrary = true```
1.6 After that I declared the elipse and added the fade animation      into it directly
1.6.1 First I set the visibililty to Invisible then after the animation I made it visible
```
elipse.visibility = View.INVISIBLE;
Handler().postDelayed({
    elipse.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fade_in))
    elipse.visibility = View.VISIBLE;
},1000)
```
1.7  Then I set the lines to animate in sequence like line1 will be animate in the start after 500ms the line2 and after 800ms the (third line) line3

```
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
```
1.8 Then the tick animation is declared at intervals of 500ms and 1000ms repectively
```
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
```
1.9 To Remove the Action Bar I
used 


```
android:theme="@style/AppTheme.NoActionBar">
```
in the manifest file for the
launcher activity

1.10 And To make the status bar
To not show any icon I used
```
window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
```
Moving on to the next activity
1.     Icons
I Got my plus icon from 
```
Here:- https://icons8.com/icons/set/plus-math
```
Got the delete icon from
```
Here: - https://www.flaticon.com/free-icon/delete_872192
```

2.    
I changed the color of the fab icon by changing
this:
```
app:srcCompat="@drawable/ic_icons8_plus_math_48"

android:backgroundTint="@color/colorPrimaryDark"
```


 



 
