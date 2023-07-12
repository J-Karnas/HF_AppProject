package pl.example.myapplication.ui.wydatki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import pl.example.myapplication.databinding.FragmentWydatkiBinding

class WydatkiFragment : Fragment() {

    private var _binding: FragmentWydatkiBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val wydatkiViewModel =
            ViewModelProvider(this).get(WydatkiViewModel::class.java)

        _binding = FragmentWydatkiBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textWydatki
//        wydatkiViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}