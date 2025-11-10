package com.dicoding.courseschedule.paging

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.util.DayName
import com.dicoding.courseschedule.util.DayName.Companion.getByNumber

class CourseViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private lateinit var course: Course

    private val tvCourseName: TextView = view.findViewById(R.id.tv_course)
    private val tvTime: TextView = view.findViewById(R.id.tv_time)
    private val tvLecturer: TextView = view.findViewById(R.id.tv_lecturer)
    private val timeString = itemView.context.resources.getString(R.string.time_format)

    // 7 : Complete ViewHolder to show item
    fun bind(course: Course, clickListener: (Course) -> Unit) {
        this.course = course

        val dayName = DayName.getByNumber(course.day)
        val time = String.format(timeString, dayName, course.startTime, course.endTime)

        tvCourseName.text = course.courseName
        tvTime.text = time
        tvLecturer.text = course.lecturer

        itemView.setOnClickListener {
            clickListener(course)
        }
    }

    fun getCourse(): Course = course
}