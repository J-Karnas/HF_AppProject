package pl.example.myapplication.ui.wydatki


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pl.example.myapplication.DatabaseHelper
import pl.example.myapplication.databinding.FragmentWydatkiBinding

class WydatkiFragment : Fragment() {

    private var _binding: FragmentWydatkiBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWydatkiBinding.inflate(inflater, container, false)
        databaseHelper = DatabaseHelper(requireContext())
        displayAmount()

        val button1 = binding.button1
        button1.setOnClickListener {
            showDialog(1, "Żywność")
            displayAmount()

        }

        val button2 = binding.button2
        button2.setOnClickListener {
            showDialog(2, "Mieszkanie")
            displayAmount()
        }

        val button3 = binding.button3
        button3.setOnClickListener {
            showDialog(3, "Transport")
            displayAmount()
        }

        val button4 = binding.button4
        button4.setOnClickListener {
            showDialog(4, "Odzież i obuwie")
            displayAmount()
        }

        val button5 = binding.button5
        button5.setOnClickListener {
            showDialog(5, "Prezenty")
            displayAmount()
        }

        val button6 = binding.button6
        button6.setOnClickListener {
            showDialog(6, "Rozwój osobisty")
            displayAmount()
        }

        val button7 = binding.button7
        button7.setOnClickListener {
            showDialog(7, "Zdrowie i uroda")
            displayAmount()
        }

        val button8 = binding.button8
        button8.setOnClickListener {
            showDialog(8, "Rozrywka i hobby")
            displayAmount()
        }

        val button9 = binding.button9
        button9.setOnClickListener {
            showDialog(9, "Technologia")
            displayAmount()
        }

        val button10 = binding.button10
        button10.setOnClickListener {
            showDialog(10, "Inne")
            displayAmount()
        }


        return binding.root
    }

    fun displayAmount() {
        val selectQuery = "SELECT ROUND(SUM(kwota), 2) as saldo FROM dane"
        val cursor = databaseHelper.readableDatabase.rawQuery(selectQuery, null)

        if (cursor != null && cursor.moveToFirst()) {
            val sumAmount = cursor.getDouble(0)

            val stanKontaTextView: TextView = binding.stanKonta
            stanKontaTextView.text = "Stan konta: $sumAmount PLN"

        }
        cursor.close()
    }

    private fun showDialog(buttonId: Int, name: String) {
        val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
        val dialogFragment = CustomDialogFragment()
        dialogFragment.setWydatkiFragment(this)
        val bundle = Bundle()
        bundle.putInt("buttonId", buttonId)
        bundle.putString("name", name)
        dialogFragment.arguments = bundle
        dialogFragment.show(fragmentManager, "dialog")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
