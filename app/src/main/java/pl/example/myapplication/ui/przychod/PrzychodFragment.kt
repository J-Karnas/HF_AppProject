package pl.example.myapplication.ui.przychod

import android.content.ContentValues
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import pl.example.myapplication.DatabaseHelper
import pl.example.myapplication.databinding.FragmentPrzychodBinding
import java.text.SimpleDateFormat
import java.util.*

class PrzychodFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private var _binding: FragmentPrzychodBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        databaseHelper = DatabaseHelper(requireContext())

        _binding = FragmentPrzychodBinding.inflate(inflater, container, false)

        binding.btnPrzychod.setOnClickListener {
            handleButtonPrzychodClick()
            przychodLista()
        }

        przychodLista()

        return binding.root
    }

    private fun handleButtonPrzychodClick() {
        databaseHelper = DatabaseHelper(requireContext())

        val textView2 = binding.errorrr2
        val textFieldKwota = binding.inputKwota
        val textFieldNotka = binding.inputNotka

        val kwotaText = textFieldKwota.text.toString()
        val notkaText = textFieldNotka.text.toString()

        if (kwotaText.isNotEmpty()) {
            val kwota: Double = kwotaText.toDouble()
            val currentDate = Date()
            val dateFormat = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
            val daneValues = ContentValues()
            daneValues.put("kwota", kwota)
            daneValues.put("data_time", dateFormat.format(currentDate))
            daneValues.put("notatka", notkaText)
            daneValues.put("id_kategoria", 11)
            databaseHelper.writableDatabase.insert("dane", null, daneValues)

            textFieldKwota.text.clear()
            textFieldNotka.text.clear()
        } else {
            textView2.text = "Wymagane jest podanie kwoty!"
        }


    }

    private fun przychodLista() {
        val tableLayout: TableLayout = binding.tabelaWplaty
        val records = displayListPrzychod()
        tableLayout.removeAllViews()
        for (record in records) {
            val row = TableRow(requireContext())
            val layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            row.layoutParams = layoutParams

            val columns = record.split(" - ")
            for (column in columns) {
                val textView = TextView(requireContext())
                textView.text = column
                textView.setPadding(10, 10, 10, 10)
                textView.textSize = 17f
                textView.gravity = Gravity.CENTER

                val params = TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f)
                params.setMargins(0, 20, 0, 20)
                textView.layoutParams = params

                row.addView(textView)
            }

            tableLayout.addView(row)
        }
    }

    private fun displayListPrzychod(): List<String> {
        val recordsList = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val selectQuery =
            "SELECT kwota, data_time, notatka FROM dane WHERE id_kategoria=11 ORDER BY id_dane DESC LIMIT 5"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val column1 = cursor.getString(cursor.getColumnIndexOrThrow("kwota"))
                val column2 = cursor.getString(cursor.getColumnIndexOrThrow("data_time"))
                val column3 = cursor.getString(cursor.getColumnIndexOrThrow("notatka"))

                val record = "$column1 - $column2 - $column3"
                recordsList.add(record)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return recordsList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}