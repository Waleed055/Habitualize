package com.example.habitualize.ui.afterauth.home

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.transition.TransitionInflater
import com.example.habitualize.R
import com.example.habitualize.databinding.FragmentTaskBinding
import com.example.habitualize.ui.afterauth.home.Adapter.TaskAdapter
import com.example.habitualize.ui.afterauth.home.taskInterface.TaskItemListener
import com.example.habitualize.ui.afterauth.premium.PremiumActivity
import com.example.habitualize.ui.afterauth.taskschedule.TaskSchedule

class TaskFragment : Fragment() , TaskItemListener {

    lateinit var taskAdapter: TaskAdapter
    lateinit var binding: FragmentTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide_right)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskBinding.inflate(layoutInflater)

        initAdapter()
        initListeners()

        return binding.root
    }

    private fun initListeners() {
        binding.taskDrawerOpenBtn.setOnClickListener {
            (activity as HomeActivity).openDrawer()
        }
        binding.premiumButton.setOnClickListener {
            startActivity(Intent(requireContext(), PremiumActivity::class.java))
        }
    }

    private fun initAdapter() {
        taskAdapter = TaskAdapter(requireContext(),this)
        binding.taskRv.adapter = taskAdapter
    }

   private fun showConfirmationDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_confirmation_dialouge)
        dialog.show()
        var button = dialog.findViewById<TextView>(R.id.confirmation_btn)
        button.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(requireContext(),TaskSchedule::class.java))
        }
    }

    override fun onItemClicked(position: Int) {
        showConfirmationDialog()
    }

}