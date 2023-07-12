package pl.example.myapplication.ui.przychod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PrzychodViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is przychod Fragment"
    }
    val text: LiveData<String> = _text
}