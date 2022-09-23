package com.example.aston_intensive_3

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import com.example.aston_intensive_3.databinding.ActivityTask2Binding
import java.net.HttpURLConnection
import java.net.URL


class Task2 : AppCompatActivity() {

    private lateinit var binding: ActivityTask2Binding

    var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTask2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null) {
            bitmap = savedInstanceState.getParcelable("bitmap")
            setImage()
        }


        binding.editText.setOnEditorActionListener { view, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loadImageFromWeb(view.editableText)
                view.hideKeyboard()
                true
            } else false
        }
    }

    private fun loadImageFromWeb(urlString: Editable?) {
        val imageUrl: URL

        try {
            imageUrl = URL(urlString.toString())
        } catch (e: Exception) {
            return
        }


        Thread {
            bitmap = backgroundTask(imageUrl)
            runOnUiThread {
                if (bitmap != null) {
                    setImage()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Ошибка. Убедитесь в правльности URL.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }

    private fun setImage() {
        binding.imageView.setImageBitmap(bitmap)
        binding.imageView.updatePadding(20, 20, 20, 20)
    }

    private fun backgroundTask(url: URL): Bitmap? {

        val httpConnection = url.openConnection() as HttpURLConnection
        httpConnection.doInput = true

        return try {
            httpConnection.connect()
            val inputStream = httpConnection.inputStream
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("Task2", e.localizedMessage ?: "Unknown error")
            null
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bitmap", bitmap)
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}