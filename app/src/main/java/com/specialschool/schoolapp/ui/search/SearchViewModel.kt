package com.specialschool.schoolapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    //기존 개발 방법과 동일하게 가져가기 위해 추가함 필요 없을시 제거
    private val noticeText = MutableLiveData<String>().apply {
        value = "검색 화면 (Model)"
    }
    val text : LiveData<String> = noticeText

    private val searchEdtHint = MutableLiveData<String>().apply {
        value = "검색하고자 하는 데이터"
    }
    val edtHint : LiveData<String> = searchEdtHint

    private val searchBtn = MutableLiveData<String>().apply {
        value = "검색"
    }
    val btnText : LiveData<String> = searchBtn
}
