package com.example.food_detect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore


class CameraActivity_AllergyInfo : AppCompatActivity() {
    private lateinit var tvFoodName: TextView
    private lateinit var tvAllergyInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_allergy_info)

        tvFoodName = findViewById(R.id.tvFoodName)
        tvAllergyInfo = findViewById(R.id.tvAllergyInfo)

        val foodName = intent.getStringExtra("foodName") ?: "Unknown"
        tvFoodName.text = foodName

        fetchAllergyInfo(foodName)
    }

    private fun fetchAllergyInfo(foodName: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("foodinfo").document(foodName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val foodInfo = document.toObject(FoodInfo::class.java)
                    tvAllergyInfo.text = foodInfo?.allergy ?: "No allergy information available"
                } else {
                    tvAllergyInfo.text = "No details found."
                }
            }
            .addOnFailureListener { exception ->
                tvAllergyInfo.text = "Error getting documents: $exception"
            }
    }
}
