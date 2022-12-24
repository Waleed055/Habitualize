package com.example.habitualize.ui.home.taskdetail.singleday

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habitualize.db.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleDayViewModel @Inject constructor(val myRepository: MyRepository) : ViewModel()  {




    fun updateTaskOpenDate(id: Long, date: Long, challenge_name: String, userLang: String){
        viewModelScope.launch {
            myRepository.updateTaskDetail(id, date, userLang)
        }
    }

    fun openChallenge(challenge_name: String, userLang: String){
        viewModelScope.launch {
            myRepository.openChallenge(challenge_name, userLang)
        }
    }

}