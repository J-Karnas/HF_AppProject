package pl.example.myapplication.ui.wydatki

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import pl.example.myapplication.DatabaseHelper
import pl.example.myapplication.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CustomDialogFragment : DialogFragment(){

    private lateinit var databaseHelper: DatabaseHelper


    private fun addDane(kwota: Double, date: String, notatka: String, kategoria: Int) {
        val daneValues = ContentValues()
        daneValues.put("kwota", kwota * (-1))
        daneValues.put("data_time", date)
        daneValues.put("notatka", notatka)
        daneValues.put("id_kategoria", kategoria)
        databaseHelper.writableDatabase.insert("dane", null, daneValues)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = LayoutInflater.from(requireActivity())
        val dialogView = inflater.inflate(R.layout.dialog_layout, null)

        val button = dialogView.findViewById<Button>(R.id.confirmButton)
        val textField1 = dialogView.findViewById<EditText>(R.id.textField1)
        val textField2 = dialogView.findViewById<EditText>(R.id.textField2)
        val textView = dialogView.findViewById<TextView>(R.id.errror)

        val args = arguments
        val buttonId = args?.getInt("buttonId") ?: -1

        button.setOnClickListener {
            databaseHelper = DatabaseHelper(requireContext())

            val amount = textField1.text.toString()
            if (amount.isNotEmpty()) {
                val kwota: Double = amount.toDouble()
                val note = textField2.text.toString()
                val currentDate = Date()
                val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
                addDane(kwota, dateFormat.format(currentDate), note, buttonId)
                textView.text = ""
                dismiss()
            } else {
                textView.text = "Wymagane jest podanie kwoty!"
            }

        }

        builder.setView(dialogView)
        return builder.create()
    }

}