package com.example.tasksapplication

import android.app.AlertDialog
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import com.example.tasksapplication.databinding.ActivityMainBinding
import com.example.tasksapplication.FirstFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var textoPadrao: String = "Hello App"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->

            var title = ""
            val popupTitle = AlertDialog.Builder(this)
            val textTitle = EditText(this)

            var description = ""
            val popupDescription = AlertDialog.Builder(this)
            val textDescription = EditText(this)

            val popupStatus = AlertDialog.Builder(this)
            val statusOptions = arrayOf("Pendente", "Concluída")

            var task : Task

            val taskList = ArrayList<Task>()

            fun TaskCreate(){

                popupTitle.setTitle("Título")
                popupTitle.setView(textTitle)
                popupTitle.setPositiveButton("Ok") {
                        dialog, _ ->
                    title = textTitle.text.toString()

                    popupDescription.setTitle("Descrição")
                    popupDescription.setView(textDescription)
                    popupDescription.setPositiveButton("Ok") {
                            dialog, _ ->
                        description = textDescription.text.toString()


                        popupStatus.setTitle("Status")
                        popupStatus.setItems(statusOptions){
                            dialog, which ->
                            val status = statusOptions[which]
                            task = Task(title, description, status)

                            taskList.add(task)

                            Snackbar.make(view, task.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show()

                        }
                        popupStatus.show()
                    }
                    popupDescription.setNegativeButton("Cancelar") {
                            dialog, _ ->
                        dialog.cancel()
                    }
                    popupDescription.show()
                }
                popupTitle.setNegativeButton("Cancelar") {
                        dialog, _ ->
                    dialog.cancel()
                }
                popupTitle.show()


            }

            TaskCreate()
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

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}