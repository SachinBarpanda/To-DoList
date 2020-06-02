package great.sachin.to_dolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        val createToDoTable : String = "CREATE TABLE $TABLE_TO_DO (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_NAME varchar," +
                "$COL_CREATED_ID datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_REMAINDER datetime," +
                "$COL_IS_COMPLETED boolean);"

        db.execSQL(createToDoTable)
        //Errors expected:
        //null in remainder
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
    fun addToDoITems(items: ToDoItems):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put (COL_NAME,items.name )
        //Errors expected:
        //no value for the remainder
        //sol : is to put another cv.put method
        val result = db.insert(TABLE_TO_DO,null,cv)
        return result!=(-1).toLong()
    }

    fun getToDoItems() : MutableList<ToDoItems>{
        val result : MutableList<ToDoItems> = ArrayList()
        val db = readableDatabase
        val queryResult  = db.rawQuery("SELECT * FROM $TABLE_TO_DO",null)
        if(queryResult.moveToFirst()){
            do{
                val todoObject = ToDoItems()
                todoObject.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                todoObject.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                result.add(todoObject)
            }while(queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }
}