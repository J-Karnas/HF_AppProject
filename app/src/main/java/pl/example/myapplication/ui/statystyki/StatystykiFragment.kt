package pl.example.myapplication.ui.statystyki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.example.myapplication.databinding.FragmentStatystykiBinding

class StatystykiFragment : Fragment() {

    private var _binding: FragmentStatystykiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val statystykiViewModel =
            ViewModelProvider(this).get(StatystykiViewModel::class.java)

        _binding = FragmentStatystykiBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textStatystyki
//        statystykiViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}