package great.sachin.to_dolist

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.TableLayout

class DBHandler(val context: Context) : SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION){
    override fun onCreate(db: SQLiteDatabase) {
        val createToDoTable : String = "CREATE TABLE $TABLE_TO_DO (" +
                "$COL_ID integer PRIMARY KEY AUTOINCREMENT," +
                "$COL_NAME varchar," +
                "$COL_CREATED_ID datetime DEFAULT CURRENT_TIMESTAMP," +
                "$COL_REMAINDER varchar," +
                "$COL_IS_COMPLETED boolean);"

        db.execSQL(createToDoTable)
        //Errors expected:
        //null in remainder
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $TABLE_TO_DO")
//        onCreate(db)
    }
    fun addToDoItems(items: ToDoItems):Boolean{
        val db = writableDatabase
        val cv = ContentValues()
        cv.put (COL_NAME,items.name )
        cv.put(COL_IS_COMPLETED,items.isCompleted)
        cv.put(COL_REMAINDER,items.remainder)
        //Errors expected:
        //no value for the remainder
        //sol : is to put another cv.put method
        val result = db.insert(TABLE_TO_DO,null,cv)
        return result!=(-1).toLong()
    }
    fun updateCheckStatus(todoID: Long, isComplete:Boolean) {
        val db = writableDatabase
        val cv = ContentValues()
//        if (isComplete) {
//            cv.put(COL_IS_COMPLETED, true)
//        } else {
//            cv.put(COL_IS_COMPLETED, false)
//        }
        val idToPass = todoID.toString()
        val db2 = readableDatabase
        val queryResult = db2.rawQuery("SELECT * FROM $TABLE_TO_DO WHERE $COL_ID = $idToPass", null)
        if (queryResult.moveToFirst()) {
            do {
                val todoObject = ToDoItems()
                todoObject.id = queryResult.getLong(queryResult.getColumnIndex(COL_ID))
                todoObject.name = queryResult.getString(queryResult.getColumnIndex(COL_NAME))
                todoObject.isCompleted = isComplete
                todoObject.remainder = if (queryResult.getString(
                        queryResult.getColumnIndex(COL_REMAINDER)) != null) {
                    queryResult.getString(queryResult.getColumnIndex(COL_REMAINDER))
                } else {"Not Specified"}
//            result.add(todoObject)

                cv.put(COL_ID,todoObject.id)
                cv.put(COL_NAME,todoObject.name)
                cv.put(COL_IS_COMPLETED,todoObject.isCompleted)
                cv.put(COL_REMAINDER,todoObject.remainder)
                db.update(TABLE_TO_DO, cv, "$COL_ID=?", arrayOf(todoID.toString()))
            } while (queryResult.moveToNext())

        }
        queryResult.close()
    }

    fun deleteToDo(todoID: Long){
        val db = writableDatabase
        db.delete(TABLE_TO_DO,"$COL_ID=?", arrayOf(todoID.toString()))
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
                todoObject.isCompleted = queryResult.getInt(queryResult.getColumnIndex(
                    COL_IS_COMPLETED))==1
                todoObject.remainder = if(queryResult.getString(queryResult.getColumnIndex(
                    COL_REMAINDER))!=null){queryResult.getString(queryResult.getColumnIndex(
                    COL_REMAINDER)) }else{"Not Specified"}
                result.add(todoObject)
            }while(queryResult.moveToNext())
        }
        queryResult.close()
        return result
    }
}