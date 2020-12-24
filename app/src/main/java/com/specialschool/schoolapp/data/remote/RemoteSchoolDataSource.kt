package com.specialschool.schoolapp.data.remote

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import com.specialschool.schoolapp.data.SchoolDataSource
import com.specialschool.schoolapp.data.json.SchoolDataJsonParser
import com.specialschool.schoolapp.model.SchoolData
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

/**
 * 학교 데이터를 다운로드 받아 파싱하는 data source
 */
class RemoteSchoolDataSource @Inject constructor(
    @ApplicationContext val context: Context
) : SchoolDataSource {

    /**
     * Http 연결을 통해 학교 데이터를 가져온다.
     */
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

    /**
     * Http 캐시를 통해 학교 데이터를 가져온다.
     */
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
