package com.dicoding.asclepius.view

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.database.Note
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.DateHelper
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.dicoding.asclepius.helper.ViewModelFactory
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var note: Note? = null
    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteAddUpdateViewModel = obtainViewModel(this@ResultActivity)
        note = intent.getParcelableExtra(EXTRA_RESULT)
        note = Note()
        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_IMAGE_URI))
        imageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.resultImage.setImageURI(it)
            imageAnalysis(imageUri)
            note.let {
                note?.imageUri = imageUri
            }
            noteAddUpdateViewModel.insert(note as Note)
        }

    }

    private fun imageAnalysis(imageUri: Uri) {
        imageClassifierHelper = ImageClassifierHelper(
            context = this,
            classifierListener = object : ImageClassifierHelper.ClassifierListener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(result: Classifications?) {
                    runOnUiThread {
                        result?.let { it ->
                            if (it.categories.isNotEmpty()) {
                                println(it)
                                val sortedCategories =
                                    it.categories.sortedByDescending { it?.score }
                                val displayResult =
                                    sortedCategories.joinToString("\n") {
                                        "${it.label} " + NumberFormat.getPercentInstance()
                                            .format(it.score).trim()
                                    }
                                binding.resultText.text = displayResult
                                note.let {
                                    note?.textResult = displayResult
                                    note?.date = DateHelper.getCurrentDate()
                                }
                            } else {
                                binding.resultText.text = ""
                            }
                        }
                    }
                }
            },
            contentResolver = contentResolver
        )
        imageClassifierHelper.classifyStaticImage(imageUri)
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }

}