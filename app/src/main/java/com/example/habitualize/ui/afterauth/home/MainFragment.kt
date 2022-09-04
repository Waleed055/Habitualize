package com.example.habitualize.ui.afterauth.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.habitualize.R
import com.example.habitualize.databinding.FragmentMainBinding
import com.example.habitualize.ui.afterauth.home.fragments.ChatFragment
import com.example.habitualize.ui.afterauth.home.fragments.ProfileFragment
import com.example.habitualize.ui.afterauth.home.fragments.taskfragment.TaskFragment

class MainFragment : Fragment() {
    lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater)
        setCurrentFragment(TaskFragment())
        initListener()
        return binding.root
    }

    private fun initListener(){
        binding.taskCard.setOnClickListener {
            setCurrentFragment(TaskFragment())
        }

        binding.feedIcon.setOnClickListener {
            setCurrentFragment(ChatFragment())
        }

        binding.profileIcon.setOnClickListener {
            setCurrentFragment(ProfileFragment())
        }
    }


    private fun setCurrentFragment(fragment:Fragment)=
        childFragmentManager.beginTransaction().apply {
            Log.e("MAINFRAGMENT","${childFragmentManager.primaryNavigationFragment?.id}   ${childFragmentManager.primaryNavigationFragment}")
            replace(R.id.host_fragment_framlayout, fragment)
            commit()
        }

}