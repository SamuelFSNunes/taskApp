package com.example.basictaskapplication

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import com.example.basictaskapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            createCard(view, "", "", "")
        }
    }

    private fun createCard(view: View, title: String, description: String, status: String) {
        if (title === "") {
            setTitle(view)
        } else if (description === "") {
            setDescription(view, title)
        } else if (status === "") {
            setStatus(view, title, description)
        } else {
            val task = Task(title, description, status)
            TaskManager.addTask(task)
            TaskManager.getAllTasks().forEach {
                Snackbar.make(view, it.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            Snackbar.make(view, TaskManager.getAllTasks().toString(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    private fun setTitle(view: View) {
        val popupTitle = AlertDialog.Builder(this)
        val textTitle = EditText(this)

        popupTitle.setTitle("Título")
        popupTitle.setView(textTitle)
        popupTitle.setPositiveButton("Ok") {
                dialog, _ ->
            val title = textTitle.text.toString()
            createCard(view, title, "", "")
        }
        popupTitle.setNegativeButton("Cancelar") {
                dialog, _ ->
            dialog.cancel()
        }
        popupTitle.show()
    }

    private fun setDescription(view: View, title: String) {
        val popupDescription = AlertDialog.Builder(this)
        val textDescription = EditText(this)

        popupDescription.setTitle("Descrição")
        popupDescription.setView(textDescription)
        popupDescription.setPositiveButton("Ok") {
                dialog, _ ->
            val description = textDescription.text.toString()
            createCard(view, title, description, "")
        }
        popupDescription.setNegativeButton("Cancelar") {
                dialog, _ ->
            dialog.cancel()
        }
        popupDescription.show()
    }

    private fun setStatus(view: View, title: String, description: String) {
        val choices = Spinner(this)
        val statusOptions = arrayOf("Pendente", "Concluido")
        choices.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, statusOptions)

        val popupStatus = AlertDialog.Builder(this)

        popupStatus.setTitle("Descrição")
        popupStatus.setView(choices)
        popupStatus.setPositiveButton("Ok") {
                dialog, _ ->
            val status = choices.selectedItem as String
            createCard(view, title, description, status)
        }
        popupStatus.setNegativeButton("Cancelar") {
                dialog, _ ->
            dialog.cancel()
        }
        popupStatus.show()

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