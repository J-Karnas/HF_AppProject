package pl.example.myapplication.ui.ustawienia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pl.example.myapplication.DatabaseHelper
import pl.example.myapplication.databinding.FragmentUstawieniaBinding

class UstawieniaFragment : Fragment() {
    private lateinit var databaseHelper: DatabaseHelper
    private var _binding: FragmentUstawieniaBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        databaseHelper = DatabaseHelper(requireContext())
        _binding = FragmentUstawieniaBinding.inflate(inflater, container, false)

        binding.btnUsunWydatek.setOnClickListener {
            usunWydatek()
        }

        binding.btnUsunPrzychod.setOnClickListener {
            usunPrzychod()
        }

        binding.btnWyczyscBaze.setOnClickListener {
            usunDane()
        }
        return binding.root
    }

    private fun usunWydatek(){
        val db = databaseHelper.writableDatabase

        val query = "DELETE FROM dane WHERE id_dane = (SELECT max(id_dane) FROM dane)"
        db.execSQL(query)

    }

    private fun usunPrzychod(){
        val db = databaseHelper.writableDatabase

        val query = "DELETE FROM dane WHERE id_dane = (SELECT max(id_dane) FROM dane WHERE id_kategoria = 11)"
        db.execSQL(query)

    }

    private fun usunDane(){
        val db = databaseHelper.writableDatabase

        val query = "DELETE FROM dane"
        db.execSQL(query)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}