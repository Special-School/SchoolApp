package com.specialschool.schoolapp.data.remote

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.specialschool.schoolapp.data.SchoolDataSource
import com.specialschool.schoolapp.data.json.SchoolDataJsonParser
import com.specialschool.schoolapp.model.School
import com.specialschool.schoolapp.model.SchoolData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.lang.RuntimeException
import javax.inject.Inject

class RemoteSchoolDataSource @Inject constructor(
    @ApplicationContext val context: Context
) : SchoolDataSource {

    override fun getRemoteSchoolData(): SchoolData? {
        if (!hasNetworkConnection()) {
            return null
        }

        val responseSource = try {
            SchoolDataDownloader(context, "1").fetch()
        } catch (e: IOException) {
            throw e
        }
        val body = responseSource.body?.byteStream() ?: return null

        val parsedData = try {
            SchoolDataJsonParser.parseJsonData(body)
        } catch (e: RuntimeException) {
            null
        }
        responseSource.close()
        return parsedData
    }

    private fun hasNetworkConnection(): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun getSchoolData(): SchoolData? {
        val responseSource = try {
            SchoolDataDownloader(context, "1").fetchCached()
        } catch (e: IOException) {
            throw e
        }
        val body = responseSource?.body?.byteStream() ?: return null

        val parsedData = try {
            SchoolDataJsonParser.parseJsonData(body)
        } catch (e: RuntimeException) {
            null
        }
        responseSource.close()
        return parsedData
    }
}
