package pl.example.myapplication.ui.wydatki

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WydatkiViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is wydatki Fragment"
    }
    val text: LiveData<String> = _text
}