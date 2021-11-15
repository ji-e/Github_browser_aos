package com.jieun.githubbrowser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.jieun.githubbrowser.model.data.ErrorData

/**
 * date 2021-11-13
 * create by jieun
 */
open class BaseViewModel : ViewModel() {
    private val _liveErrorDialog = MutableLiveData<String?>()
    var liveErrorDialog: LiveData<String?> = _liveErrorDialog


    /** 에러 다이얼로그*/
    fun setErrorDialog(error: String?) {
        if(error == null){
            _liveErrorDialog.postValue(null)
            return
        }
        val errorData = Gson().fromJson(error, ErrorData::class.java)
        _liveErrorDialog.postValue(errorData.message)
    }
}