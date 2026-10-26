package com.nicolas.tareas.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicolas.tareas.R
import com.nicolas.tareas.adapters.TaskAdapter
import com.nicolas.tareas.data.Task
import com.nicolas.tareas.data.TaskDAO
import com.nicolas.tareas.databinding.ActivityMainBinding
import com.nicolas.tareas.databinding.DialogTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: TaskAdapter

    lateinit var taskDAO: TaskDAO

    var taskList = listOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        taskDAO = TaskDAO(this)

        taskList = taskDAO.findAll()

        adapter = TaskAdapter(taskList, ::onItemClick, ::onItemCheck, ::onItemEdit, ::onItemDelete)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener {
            val task = Task(-1, "", false)
            showTaskDialog(task)
        }
    }

    fun reloadData(){
        taskList = taskDAO.findAll()
        adapter.updateData(taskList)
    }

    fun showTaskDialog(task: Task) {
        val dialogBinding = DialogTaskBinding.inflate(layoutInflater)
        dialogBinding.titleTextField.editText!!.setText(task.title)
        val dialogTitle = if (task.id == -1) "Nueva tarea" else "Editar tarea"

        val builder = MaterialAlertDialogBuilder(this)
            .setTitle(dialogTitle)
            .setView(dialogBinding.root)
            .setPositiveButton("Guardar") { dialog, which->}
            .setNegativeButton("Cancelar") { dialog, which->}

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val title = dialogBinding.titleTextField.editText!!.text.toString()
            if (title.isEmpty()){
                dialogBinding.titleTextField.error = "No se pueden crear tareas vacias"
            } else {
                task.title = title
                taskDAO.save(task)
                dialog.dismiss()
                reloadData()
            }
        }
    }

    fun onItemClick(position: Int){
        onItemCheck(position)
    }

    fun onItemCheck(position: Int){
        val task = taskList[position]
        task.done = !task.done
        taskDAO.update(task)

        reloadData()
    }

    fun onItemEdit(position: Int) {
        val task = taskList[position]
        showTaskDialog(task)
    }

    fun onItemDelete(position: Int) {
        val builder = MaterialAlertDialogBuilder(this)
            .setTitle("Borrar tarea")
            .setMessage("¿Estas seguro que quieres borrar esta tarea?")
            .setPositiveButton("Borrar") { dialog, which->
                val task = taskList[position]
                taskDAO.delete(task)
                reloadData()
            }
            .setNegativeButton("Cancelar") { dialog, which->}

        val dialog = builder.create()
        dialog.show()
    }
}

