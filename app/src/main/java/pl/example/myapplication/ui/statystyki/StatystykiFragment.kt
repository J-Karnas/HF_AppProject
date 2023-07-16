package pl.example.myapplication.ui.statystyki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import pl.example.myapplication.DatabaseHelper
import pl.example.myapplication.R
import pl.example.myapplication.databinding.FragmentStatystykiBinding
import java.util.ArrayList

class StatystykiFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private var _binding: FragmentStatystykiBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databaseHelper = DatabaseHelper(requireContext())
        _binding = FragmentStatystykiBinding.inflate(inflater, container, false)
        val spinnerStatystyki = binding.spinnerStatystyki
        val addButton: Button = binding.btnPotwierdz1
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.statystyki_miesiac, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatystyki.adapter = adapter

        statystykiLista("1")
        addButton.setOnClickListener {
            val selectedDate = spinnerStatystyki.selectedItem as String

            statystykiLista(selectedDate)

        }
        return binding.root
    }

    private fun statystykiLista(mies: String) {
        val tableLayout: TableLayout = binding.tabelaStatystyki
        val records = displayListaStatystyki(mies)
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
                textView.setPadding(8, 8, 250, 8)
                textView.textSize = 17f
                row.addView(textView)
            }

            tableLayout.addView(row)
        }
    }

    fun displayListaStatystyki(mies1: String): List<String> {
        val recordsList = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val selectQuery = "SELECT SUM(kwota) AS suma_kwota, round((-SUM(kwota)*100)/(SELECT SUM(kwota)*(-1) AS suma_koszt   FROM dane WHERE dane.data_time BETWEEN date('now', '-$mies1 month') AND date('now') AND id_kategoria != 11), 2) as procent, nazwa_kat FROM (SELECT dane.kwota, kategoria.nazwa_kat FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria WHERE dane.data_time BETWEEN date('now', '-$mies1 month') AND date('now') AND dane.id_kategoria != 11) AS test GROUP BY nazwa_kat"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val column1 = cursor.getString(cursor.getColumnIndexOrThrow("suma_kwota"))
                val column2 = cursor.getString(cursor.getColumnIndexOrThrow("procent"))
                val column3 = cursor.getString(cursor.getColumnIndexOrThrow("nazwa_kat"))

                val record = "$column1 - $column2% - $column3"
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