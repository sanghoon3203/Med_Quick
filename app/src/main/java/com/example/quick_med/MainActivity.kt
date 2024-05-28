package com.example.quick_med

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonAlarm = findViewById<Button>(R.id.button_alarm)
        buttonAlarm.setOnClickListener {
            val intent = Intent(this, Alarm::class.java)
            startActivity(intent)
        }

        val buttonCalendar = findViewById<Button>(R.id.button_calendar)
        buttonCalendar.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            startActivity(intent)
        }

        val buttonMyMed = findViewById<Button>(R.id.button_my_med)
        buttonMyMed.setOnClickListener {
            val intent = Intent(this, My_Med::class.java)
            startActivity(intent)
        }

        val buttonSearchMed = findViewById<Button>(R.id.button_search_med)
        buttonSearchMed.setOnClickListener {
            val intent = Intent(this, Search_Med::class.java)
            startActivity(intent)
        }

        // Load saved medicine names and display them in the ListView
        val medicineListView = findViewById<ListView>(R.id.medicineListView)
        val sharedPreferences = getSharedPreferences("saved_medicines", Context.MODE_PRIVATE)
        val savedMedicines = sharedPreferences.getStringSet("medicines", HashSet()) ?: HashSet()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, savedMedicines.toList())
        medicineListView.adapter = adapter
    }
}
