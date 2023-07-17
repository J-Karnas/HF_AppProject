package pl.example.myapplication.ui.historia

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import pl.example.myapplication.DatabaseHelper
import pl.example.myapplication.R
import pl.example.myapplication.databinding.FragmentHistoriaBinding

class HistoriaFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private var _binding: FragmentHistoriaBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databaseHelper = DatabaseHelper(requireContext())
        _binding = FragmentHistoriaBinding.inflate(inflater, container, false)

        val spinnerKategoria = binding.spinnerKategoria
        val addButton: Button = binding.btnPotwierdz
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.kategorie_array,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKategoria.adapter = adapter

        spinnerKategoria.setPadding(10,10, 10, 10)

        historiaLista("Żywność", "test")
        addButton.setOnClickListener {
            val selectedDate = spinnerKategoria.selectedItem as String

            historiaLista(selectedDate, "test")

        }

        return binding.root
    }

    private fun historiaLista(kategoria: String, mode: String) {
        val tableLayout: TableLayout = binding.tabelaraport
        val records = displayListaHistoria(kategoria, mode)
        tableLayout.removeAllViews()
        for (record in records) {
            val row = TableRow(requireContext())
            val layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            row.layoutParams = layoutParams

            val columns = record.split(" - ")

//            val firstValue = columns.firstOrNull()
//
//            if (firstValue != null && firstValue.startsWith("-")) {
//                row.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.minus))
//            } else {
//                row.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.plus))
//            }

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

    fun displayListaHistoria(kategoria1: String, mode1: String): List<String> {
        val recordsList = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val selectQuery =
            "SELECT dane.kwota, dane.data_time, dane.notatka FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria  WHERE kategoria.nazwa_kat= '$kategoria1' ORDER BY id_dane DESC LIMIT 30"
//        val selectQuery2 = "SELECT dane.kwota, dane.data_time, dane.notatka FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria  WHERE dane.id_kategoria != 11 LIMIT 30"
//        val selectQuery3 = "SELECT dane.kwota, dane.data_time, dane.notatka FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria LIMIT 30"
//        when(mode1){
//            "wydatki" -> selectQuery = "SELECT dane.kwota, dane.data_time, dane.notatka FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria  WHERE dane.id_kategoria != 11 LIMIT 30"
//            "wszystkieKategorie" -> "SELECT dane.kwota, dane.data_time, dane.notatka FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria  WHERE dane.id_kategoria != 11 LIMIT 30"
//            "" -> "SELECT dane.kwota, dane.data_time, dane.notatka FROM dane INNER JOIN kategoria ON dane.id_kategoria=kategoria.id_kategoria  WHERE dane.id_kategoria != 11 LIMIT 30"
//        }

        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val column1 = cursor.getString(cursor.getColumnIndexOrThrow("kwota"))
                val column2 = cursor.getString(cursor.getColumnIndexOrThrow("data_time"))
                val column3 = cursor.getString(cursor.getColumnIndexOrThrow("notatka"))

                val record = "$column1 PLN - $column2 - $column3"
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