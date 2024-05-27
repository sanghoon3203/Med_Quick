package com.example.quick_med

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
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
    }
}
