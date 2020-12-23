package com.specialschool.schoolapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * 위치 좌표 model, [Parcelable] 직렬화 적용
 */
@Parcelize
data class Coordinate(
    val latitude: Double,
    val longitude: Double
) : Parcelable
