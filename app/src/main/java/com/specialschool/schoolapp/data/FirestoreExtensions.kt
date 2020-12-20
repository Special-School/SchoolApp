package com.specialschool.schoolapp.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

/**
 * Firestore에 저장된 데이터 컬렉션을 가져온다.
 */
fun FirebaseFirestore.schoolDataDocument(): DocumentReference =
    collection("school_app_datas").document("data")
