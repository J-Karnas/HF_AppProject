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


        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}