package com.specialschool.schoolapp.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

fun FirebaseFirestore.schoolDataDocument(): DocumentReference =
    collection("school_app_datas").document("data")
