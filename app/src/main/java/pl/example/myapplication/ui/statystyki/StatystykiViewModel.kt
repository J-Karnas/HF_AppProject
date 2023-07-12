package pl.example.myapplication.ui.statystyki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StatystykiViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is statystyki Fragment"
    }
    val text: LiveData<String> = _text
}