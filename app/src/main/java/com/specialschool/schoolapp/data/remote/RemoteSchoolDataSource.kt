package com.specialschool.schoolapp.data.remote

import android.content.Context
import com.specialschool.schoolapp.data.SchoolDataSource
import com.specialschool.schoolapp.model.School
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

class RemoteSchoolDataSource @Inject constructor(
    @ApplicationContext val context: Context
) : SchoolDataSource {

    override fun getRemoteSchoolDatas(): List<School>? {
        val responseSource = try {
            SchoolDataDownloader(context, "1").fetch()
        } catch (e: IOException) {
            throw e
        }
        val body = responseSource.body?.byteStream() ?: return null

        TODO("parse json")
    }

    override fun getSchoolData(): List<School>? {
        val responseSource = try {
            SchoolDataDownloader(context, "1").fetchCached()
        } catch (e: IOException) {
            throw e
        }
        val body = responseSource?.body?.byteStream() ?: return null

        TODO("parse json")
    }
}
