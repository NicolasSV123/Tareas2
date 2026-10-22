package com.nicolas.tareas.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nicolas.tareas.R
import com.nicolas.tareas.adapters.TaskAdapter
import com.nicolas.tareas.data.Task
import com.nicolas.tareas.data.TaskDAO
import com.nicolas.tareas.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager


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

        setContentView(R.layout.activity_main)
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
    }

    fun onItemClick(position: Int){
        onItemCheck(position)
    }

    fun onItemCheck(position: Int){
        val task = taskList[position]
        task.done = !task.done
        taskDAO.update(task)

        //TODO: Se puede optimizar
        taskList = taskDAO.findAll()
        adapter.updateData(taskList)
    }

    fun onItemEdit(position: Int) {

    }

    fun onItemDelete(position: Int) {

    }
}

