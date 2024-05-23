package com.example.food_detect

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CameraActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var currentPhotoPath: String
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_PERMISSIONS = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        imageView = findViewById(R.id.imageView)
        val btnCamera: Button = findViewById(R.id.btnCamera)

        btnCamera.setOnClickListener {
            if (checkPermissions()) {
                dispatchTakePictureIntent()
            }
        }
    }

    private fun checkPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        val writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        val permissionsNeeded = mutableListOf<String>()

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        return if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), REQUEST_PERMISSIONS)
            false
        } else {
            true
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // 이미지 파일 이름 생성
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // 파일의 절대 경로 저장
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // 인텐트를 처리할 카메라 앱이 있는지 확인
            takePictureIntent.resolveActivity(packageManager)?.also {
                // 사진 파일 생성
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // 파일을 생성할 수 없으면 null을 반환하고 에러 메시지를 표시
                    null
                }
                // 사진 파일이 성공적으로 생성되었다면 파일을 URI로 변환하여 인텐트에 추가
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.myapplication.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSIONS) {
            if ((grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED })) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            imageView.setImageBitmap(bitmap)
        }
    }
}
