package com.dicoding.asclepius.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.database.Note
import com.dicoding.asclepius.databinding.ActivityRiwayatBinding
import com.dicoding.asclepius.helper.ViewModelFactory

class RiwayatActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private var note: Note? = null
    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel

    private var _activityRiwayatBinding: ActivityRiwayatBinding? = null
    private val binding get() = _activityRiwayatBinding

    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityRiwayatBinding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        mainViewModel = obtainViewModel(this@RiwayatActivity)

        noteAddUpdateViewModel = obtainViewModelNote(this)
        note = intent.getParcelableExtra(ResultActivity.EXTRA_RESULT)
        note = Note()

        mainViewModel.getAllNotes().observe(this) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }
        adapter = NoteAdapter() { note ->
            noteAddUpdateViewModel.delete(note as Note)
        }
        binding?.tvHistory?.layoutManager = LinearLayoutManager(this)
        binding?.tvHistory?.setHasFixedSize(true)
        binding?.tvHistory?.adapter = adapter
    }

    private fun obtainViewModelNote(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityRiwayatBinding = null
    }

}