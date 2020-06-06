package great.sachin.to_dolist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Paint
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.view_holder_main_activity.*
import java.util.*

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    var parsed = ""
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setTitle(" TODO List ")

        dbHandler = DBHandler(this)
        recycler_view.layoutManager = LinearLayoutManager(this);
        setSupportActionBar(toolbar)

        fabIcon.setOnClickListener {
            val view=  layoutInflater.inflate(R.layout.dialog_add_item,null)
            val dialog = AlertDialog.Builder(this)
            val todoName = view.findViewById<EditText>(R.id.textEditor)
            val checkBoxDue = view.findViewById<CheckBox>(R.id.checkBoxDue)
            dialog.setView(view)
            checkBoxDue.setOnClickListener {
                //show the date and time setter
                if(checkBoxDue.isChecked) {
                    val calendar: Calendar = Calendar.getInstance()
                    day = calendar.get(Calendar.DAY_OF_MONTH)
                    month = calendar.get(Calendar.MONTH)
                    year = calendar.get(Calendar.YEAR)
                    val datePickerDialog =
                        DatePickerDialog(this, this, year, month, day)
                    datePickerDialog.setOnCancelListener {
                        Toast.makeText(this,"Date Picker Canceled.", Toast.LENGTH_SHORT).show()
                        checkBoxDue!!.isChecked=false
                    }
                    datePickerDialog.show()
                }
            }
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->

                if(todoName.text.isNotEmpty()){
                    val toDoItems = ToDoItems()
                    toDoItems.name = todoName.text.toString()
                    toDoItems.isCompleted = false
                    if(checkBoxDue.isChecked){
                        toDoItems.remainder = timeParse()
                    }else
                        toDoItems.remainder = "Not Specified"
                    dbHandler.addToDoItems(toDoItems)
                    reloadList()
                }
            }
            dialog.setNegativeButton("Cancel"){ _: DialogInterface, _: Int ->
            }
            dialog.show()
        }
    }

    override fun onResume() {
        reloadList()
        super.onResume()
    }

    fun reloadList(){
        recycler_view.adapter = MainRecyclerAdapter(this,dbHandler.getToDoItems(),dbHandler)
    }

    class MainRecyclerAdapter(val activity: MainActivity,val list: MutableList<ToDoItems>,val dbHandler: DBHandler) :
        RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(activity).inflate(R.layout.view_holder_main_activity,
                parent,false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.taskName.text = list[position].name
            holder.checkBox.isChecked = list[position].isCompleted
            if(holder.checkBox.isChecked){
                holder.taskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }else{
                holder.taskName.paintFlags = Paint.ANTI_ALIAS_FLAG
            }
            holder.checkBox.setOnClickListener {
                if(holder.checkBox.isChecked) {
                    dbHandler.updateCheckStatus(list[position].id, true)
                    Toast.makeText(activity, "Task Completed", Toast.LENGTH_SHORT).show()
                    holder.taskName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                }else{
                    dbHandler.updateCheckStatus(list[position].id, false)
                    Toast.makeText(activity,"Incomplete",Toast.LENGTH_SHORT).show()
                    holder.taskName.paintFlags = Paint.ANTI_ALIAS_FLAG
                }
            }
            holder.dateAndTime.text = list[position].remainder
            holder.delete.setOnClickListener{
                val dialog1 =  AlertDialog.Builder(activity)
                dialog1.setTitle("Are you sure")
                dialog1.setMessage("You want to delete ${holder.taskName.text}")
                dialog1.setPositiveButton("Delete") { _: DialogInterface, _: Int ->
                    Toast.makeText(activity,"${holder.taskName.text} is deleted", Toast.LENGTH_SHORT).show()
                    dbHandler.deleteToDo(list[position].id)
                    activity.reloadList()
                }
                dialog1.setNegativeButton("Not now"){ _: DialogInterface, _: Int ->

                }
                dialog1.show()


            }
        }
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
            val taskName : TextView = v.findViewById(R.id.viewTaskName)
            val checkBox : CheckBox = v.findViewById(R.id.checkBox)
            val dateAndTime : TextView = v.findViewById(R.id.dateAndTime)
            val delete : ImageView = v.findViewById(R.id.deleteTask)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

      override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month
        val calendar: Calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(this@MainActivity, this@MainActivity, hour, minute,
            DateFormat.is24HourFormat(this))
        timePickerDialog.show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        parsed = """
            Year: $myYear
            Month: $myMonth
            Day: $myDay
            Hour: $myHour
            Minute: $myMinute
            """.trimIndent()

    }
    fun timeParse():String {
        return " $myDay : $myMonth : $myYear  At $hour : $minute"
    }
}
