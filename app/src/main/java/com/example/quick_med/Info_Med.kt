package com.example.quick_med

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso

class Info_Med : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infomed)

        val medicineNameTextView: TextView = findViewById(R.id.medicineName)
        val medicineImage: ImageView = findViewById(R.id.medicineImage)
        val effectInfo: TextView = findViewById(R.id.Api_effectInfo)
        val dosageInfo: TextView = findViewById(R.id.Api_dosageInfo)
        val sideEffectsInfo: TextView = findViewById(R.id.Api_sideEffectsInfo)
        val yesButton: Button = findViewById(R.id.yesButton)
        val noButton: Button = findViewById(R.id.noButton)

        val name = intent.getStringExtra("name")
        val imageUrl = intent.getStringExtra("imageUrl")
        val effect = intent.getStringExtra("effect")
        val dosage = intent.getStringExtra("dosage")
        val sideEffects = intent.getStringExtra("sideEffects")

        medicineNameTextView.text = name

        if (imageUrl != null) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.placeholder_image).into(medicineImage)
        } else {
            medicineImage.setImageResource(R.drawable.placeholder_image)
        }

        effectInfo.text = effect
        dosageInfo.text = dosage
        sideEffectsInfo.text = sideEffects

        // Button click listeners
        yesButton.setOnClickListener {
            if (name != null) {
                saveMedicine(name)
            }
        }

        noButton.setOnClickListener {
            val intent = Intent(this, Search_Med::class.java)
            startActivity(intent)
        }
    }

    private fun saveMedicine(name: String) {
        val sharedPreferences = getSharedPreferences("saved_medicines", Context.MODE_PRIVATE)
        val savedMedicines = sharedPreferences.getStringSet("medicines", HashSet()) ?: HashSet()

        if (savedMedicines.contains(name)) {
            Toast.makeText(this, "이미 저장된 약입니다", Toast.LENGTH_SHORT).show()
        } else {
            savedMedicines.add(name)
            sharedPreferences.edit().putStringSet("medicines", savedMedicines).apply()
            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
