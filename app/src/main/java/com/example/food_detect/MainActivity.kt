package com.example.food_detect

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Edge-to-Edge 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 한식 알레르기 정보 보기 버튼
        findViewById<Button>(R.id.btnShowList).setOnClickListener {
            // AllergyInfoActivity로 이동
            val intent = Intent(this, AllergyInfoActivity::class.java)
            startActivity(intent)
        }

        // 새 화면으로 이동 버튼
        findViewById<Button>(R.id.btnOpenCameraActivity).setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }
    }
}
