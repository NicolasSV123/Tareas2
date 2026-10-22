package com.nicolas.tareas.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.nicolas.tareas.utils.DatabaseManager

class TaskDAO(val context: Context) {

    lateinit var db: SQLiteDatabase

    fun open(){
        db = DatabaseManager(context).writableDatabase
    }

    fun close(){
        db.close()
    }

    fun insert(task: Task){
        // Gets the data repository in write mode
        open()

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(Task.COLUMN_NAME_TITLE, task.title)
        values.put(Task.COLUMN_NAME_DONE, task.done)

        try {
            // Insert the row
            val newRowId = db.insertOrThrow(Task.TABLE_NAME, null, values)
            Log.i("DATABASE", "Inserted row inserted with ID: $newRowId in ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            Log.e("DATABASE", e.localizedMessage, e)
        } finally {
            close()
        }


    }

    fun update(task: Task){

        // Gets the data repository in write mode
        open()

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(Task.COLUMN_NAME_TITLE, task.title)
        values.put(Task.COLUMN_NAME_DONE, task.done)

        try {
            // Update the row
            val updateRows = db.update(
                Task.TABLE_NAME,
                values,
                "${Task.COLUMN_NAME_ID} = ${task.id}",
                null
            )
            Log.i("DATABASE", "Update a row inserted with ID: ${task.id} in ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            Log.e("DATABASE", e.localizedMessage, e)
        } finally {
            close()
        }
    }

    fun delete(task: Task){
        delete(task.id)
    }

    fun delete(id: Int){
        open()
        try {
            val deleteRows = db.delete(Task.TABLE_NAME, "${Task.COLUMN_NAME_ID} = $id", null)
            Log.i("DATABASE", "Deleted a row inserted with ID: $id in ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            Log.e("DATABASE", e.localizedMessage, e)
        } finally {
            close()
        }
    }

    fun findById(id: Int): Task?{

    }

    fun findAll(): List<Task>{

    }
}