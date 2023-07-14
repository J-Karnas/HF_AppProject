package pl.example.myapplication.ui.statystyki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import pl.example.myapplication.DatabaseHelper
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

        statystykiLista()
        return binding.root
    }

    private fun statystykiLista() {
        val tableLayout: TableLayout = binding.tabelaStatystyki
        val records = displayListPrzychod()

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

    fun displayListPrzychod(): List<String> {
        val recordsList = ArrayList<String>()
        val db = databaseHelper.readableDatabase
        val selectQuery = "SELECT kwota, data_time, notatka FROM dane WHERE id_kategoria=11"
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