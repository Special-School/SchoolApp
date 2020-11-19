package com.specialschool.schoolapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager

class SearchViewModel : ViewModel() {
    //기존 개발 방법과 동일하게 가져가기 위해 추가함 필요 없을시 제거
    private val notice_text = MutableLiveData<String>().apply {
        value = "검색 화면 (Model)"
    }
    val text : LiveData<String> = notice_text

    private val search_edt_hint = MutableLiveData<String>().apply {
        value = "검색하고자 하는 데이터"
    }
    val edt_hint : LiveData<String> = search_edt_hint

    private val search_btn = MutableLiveData<String>().apply {
        value = "검색"
    }
    val btn_text : LiveData<String> = search_btn




}