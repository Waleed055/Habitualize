package com.example.habitualize.ui.afterauth.home.fragments.taskfragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.habitualize.R
import com.example.habitualize.ui.afterauth.home.HomeActivity
import com.example.habitualize.ui.afterauth.home.fragments.taskfragment.Adapter.TaskAdapter
import com.example.habitualize.ui.afterauth.home.fragments.taskfragment.taskInterface.TaskItemListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.droidsonroids.gif.GifImageView

class TaskFragment : Fragment() , TaskItemListener {

    lateinit var task_parent : FrameLayout
    lateinit var taskDrawerOpenBtn : ImageView
    lateinit var taskAdapter: TaskAdapter
    lateinit var taskRv: RecyclerView

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

        var view = inflater.inflate(R.layout.fragment_task, container, false)


        initViews(view)
        initAdapter()
        initListeners()

        return view
    }

    private fun initListeners() {
        taskDrawerOpenBtn.setOnClickListener {
            (activity as HomeActivity).openDrawer()
        }
    }

    private fun initAdapter() {
        taskAdapter = TaskAdapter(requireContext(),this)
        taskRv.adapter = taskAdapter
    }

    private fun initViews(view: View) {
        task_parent = view.findViewById(R.id.task_parent)
        taskDrawerOpenBtn = view.findViewById(R.id.task_drawer_open_btn)
        taskRv = view.findViewById(R.id.task_rv)


    }



   private fun showTaskCompleteDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.item_task_complete_dialouge)
        dialog.show()
        var gifView = dialog.findViewById<GifImageView>(R.id.dialoge_gif)
        var gifView2 = dialog.findViewById<GifImageView>(R.id.dialoge_gif2)
        var button = dialog.findViewById<TextView>(R.id.done_btn)



        button.setOnClickListener {
            gifView.visibility = View.GONE
            gifView2.visibility = View.VISIBLE
         GlobalScope.launch {
             delay(2000)
             dialog.dismiss()
         }
        }
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
            showTaskCompleteDialog()
        }
    }

    override fun onItemClicked(position: Int) {
        showConfirmationDialog()
    }

}