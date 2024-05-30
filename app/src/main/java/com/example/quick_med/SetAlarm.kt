package com.example.quick_med

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.example.quick_med.AlarmData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SetAlarm : AppCompatActivity() {
    private companion object {
        const val REQUEST_CODE = 1 // 요청 코드 정의
    }

    private lateinit var alarmListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_set_alarm_0)

        // 시스템 바 패딩 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 오늘 날짜를 표시
        val dateTextView: TextView = findViewById(R.id.dateTextView)
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("M월 d일 E요일", Locale.KOREAN)
        val dateString = dateFormat.format(calendar.time)
        dateTextView.text = dateString

        alarmListLayout = findViewById(R.id.alarmListLayout)

        // 알람 설정 화면으로 이동하는 버튼
        val button: Button = findViewById(R.id.addbutton)
        button.setOnClickListener {
            val intent = Intent(this, SetAlarm_Add::class.java)
            startActivityForResult(intent, REQUEST_CODE) // 알람 설정 화면으로 이동
        }
        // 저장된 알람 불러오기
        loadAlarms()
    }

    private fun loadAlarms() {
        val sharedPreferences = getSharedPreferences("AlarmPreferences", MODE_PRIVATE)
        val alarmList = getAlarmList(sharedPreferences)

        for ((index, alarmData) in alarmList.withIndex()) {
            val amPm = if (alarmData.hour >= 12) "PM" else "AM"
            val displayHour = if (alarmData.hour > 12) alarmData.hour - 12 else if (alarmData.hour == 0) 12 else alarmData.hour
            val displayMinute = String.format("%02d", alarmData.minute)
            val time = "$amPm $displayHour:$displayMinute"
            addAlarmToList(alarmData.name, time, index)
        }
    }

    private fun getAlarmList(sharedPreferences: SharedPreferences): MutableList<AlarmData> {
        val json = sharedPreferences.getString("ALARM_LIST", null)
        return if (json != null) {
            val type = object : TypeToken<MutableList<AlarmData>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun addAlarmToList(label: String, time: String, index: Int) {
        val alarmView = layoutInflater.inflate(R.layout.alarm_item, alarmListLayout, false)
        val alarmLabelTextView = alarmView.findViewById<TextView>(R.id.alarmLabelTextView)
        val alarmTimeTextView = alarmView.findViewById<TextView>(R.id.alarmTimeTextView)

        alarmLabelTextView.text = label
        alarmTimeTextView.text = time

        alarmView.setOnClickListener {
            val intent = Intent(this, SetAlarm_Modify::class.java)
            intent.putExtra("ALARM_NAME", label)
            intent.putExtra("ALARM_HOUR", time.substring(3, 5).toInt())
            intent.putExtra("ALARM_MINUTE", time.substring(6, 8).toInt())
            intent.putExtra("ALARM_INDEX", index)
            startActivityForResult(intent, REQUEST_CODE)
        }

        alarmListLayout.addView(alarmView, 0) // 알람 목록에 뷰 추가
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let { intent ->
                val alarmIndex = intent.getIntExtra("ALARM_INDEX", -1)
                if (alarmIndex != -1) {
                    val sharedPreferences = getSharedPreferences("AlarmPreferences", MODE_PRIVATE)
                    val alarmList = getAlarmList(sharedPreferences)

                    if (intent.hasExtra("ALARM_NAME") && intent.hasExtra("ALARM_HOUR") && intent.hasExtra("ALARM_MINUTE")) {
                        // 수정된 알람 데이터
                        val label = intent.getStringExtra("ALARM_NAME")
                        val hour = intent.getIntExtra("ALARM_HOUR", -1)
                        val minute = intent.getIntExtra("ALARM_MINUTE", -1)
                        if (label != null && hour != -1 && minute != -1) {
                            val alarmData = alarmList[alarmIndex]
                            alarmData.name = label
                            alarmData.hour = hour
                            alarmData.minute = minute
                        }
                    } else {
                        // 알람 삭제
                        alarmList.removeAt(alarmIndex)
                    }

                    saveAlarmList(sharedPreferences, alarmList)
                    alarmListLayout.removeAllViews()
                    loadAlarms()
                }
            }
        }
    }

    private fun saveAlarmList(sharedPreferences: SharedPreferences, alarmList: MutableList<AlarmData>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(alarmList)
        editor.putString("ALARM_LIST", json)
        editor.apply()
    }
}
