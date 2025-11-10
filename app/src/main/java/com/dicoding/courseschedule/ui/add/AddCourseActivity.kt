package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.ui.home.HomeViewModel
import com.dicoding.courseschedule.ui.home.HomeViewModelFactory
import com.dicoding.courseschedule.util.TimePickerFragment

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {
    private lateinit var viewModel: AddCourseViewModel
    private var startTime: String = ""
    private var endTime: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        val ibStartTime = findViewById<View>(R.id.ib_start_time)
        val ibEndTime = findViewById<View>(R.id.ib_end_time)

        ibStartTime.setOnClickListener {
            showDatePicker(it, "start time")
        }

        ibEndTime.setOnClickListener {
            showDatePicker(it, "end time")
        }

        viewModel.saved.observe(this) {
            if (it.getContentIfNotHandled() == true) {
                Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to add course", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_insert -> {
                val courseName = findViewById<EditText>(R.id.ed_course_name).text.toString()
                val lecture = findViewById<EditText>(R.id.ed_lecturer).text.toString()
                val note = findViewById<EditText>(R.id.ed_note).text.toString()
                val spinner = findViewById<Spinner>(R.id.spinner_day)

                viewModel.insertCourse(
                    courseName = courseName,
                    lecturer = lecture,
                    note = note,
                    startTime = startTime,
                    endTime = endTime,
                    day = resources.getStringArray(R.array.day)
                        .indexOf(spinner.selectedItem.toString())
                )
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun showDatePicker(view: View, tag: String? = null) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, tag)
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val tvStartTime = findViewById<TextView>(R.id.tv_start_time)
        val tvEndTime = findViewById<TextView>(R.id.tv_end_time)
        when (tag) {
            "start time" -> {
                startTime = "$hour:$minute"
                tvStartTime.text = "$hour:$minute"
            }

            "end time" -> {
                endTime = "$hour:$minute"
                tvEndTime.text = "$hour:$minute"
            }
        }
    }
}