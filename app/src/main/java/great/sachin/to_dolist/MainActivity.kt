package great.sachin.to_dolist

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setTitle(" TO-DO List ")

        dbHandler = DBHandler(this)
        recycler_view.layoutManager = LinearLayoutManager(this);
        setSupportActionBar(toolbar)

        fabIcon.setOnClickListener {
            val view=  layoutInflater.inflate(R.layout.dialog_add_item,null)
            val dialog = AlertDialog.Builder(this)
            val todoName = view.findViewById<EditText>(R.id.textEditor)
            dialog.setView(view)
            dialog.setPositiveButton("Add") { _: DialogInterface, _: Int ->
                if(todoName.text.isNotEmpty()){
                    val toDoItems = ToDoItems()
                    toDoItems.name = todoName.text.toString()
                    toDoItems.isCompleted = false
                    dbHandler.addToDoITems(toDoItems)
                    reloadList()
                }
            }
            dialog.setNegativeButton("Cancel"){ _: DialogInterface, _: Int ->

            }

            dialog.show()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
        }
    }

    override fun onResume() {
        reloadList()
        super.onResume()
    }

    fun reloadList(){
        recycler_view.adapter = MainRecyclerAdapter(this,dbHandler.getToDoItems())
    }

    class MainRecyclerAdapter(val context: Context,val list: MutableList<ToDoItems>) :
        RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_holder_main_activity,
                parent,false))
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.taskName.text = list[position].name
            holder.checkBox.isChecked = list[position].isCompleted
        }
        class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
            val taskName : TextView = v.findViewById(R.id.viewTaskName)
            val checkBox : CheckBox = v.findViewById(R.id.checkBox)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
