package com.specialschool.schoolapp.data.remote

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.specialschool.schoolapp.BuildConfig
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import kotlin.Throws

/**
 * 학교 데이터 다운로드 모듈, OkHttp3 클라이언트로 구현
 *
 * @property context ApplicationContext
 * @property bootstrapVersion
 * @property client OkHttp3 클라이언트
 */
class SchoolDataDownloader(
    private val context: Context,
    private val bootstrapVersion: String
) {

    private val client: OkHttpClient by lazy {
        // logcat
        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BASIC

        // http/2
        val protocols = arrayListOf(Protocol.HTTP_1_1, Protocol.HTTP_2)

        // cache
        val size = 2L * 1024 * 1024
        val dir = context.getDir("schools_data", Context.MODE_PRIVATE)
        val cache = Cache(dir, size)

        OkHttpClient.Builder()
            .protocols(protocols)
            .cache(cache)
            .addInterceptor(logInterceptor)
            .build()
    }

    /**
     * Firebase storage endpoint에서 학교 데이터 파일(json)을 네트워크를 통해 불러온다.
     * 처음 데이터를 불러 올 때나 cache를 다시 설정할 때 호출한다.
     *
     * @return 반환된 Response를 byte stream으로 열어서 json parser에 넘긴다.
     * @throws IOException 실패했을 경우 발생
     */
    @Throws(IOException::class)
    @WorkerThread
    fun fetch(): Response {
        val url = BuildConfig.SCHOOL_DATA_URL

        val httpBuilder = url.toHttpUrlOrNull()?.newBuilder()
            ?: throw IllegalArgumentException("malformed data url")
        httpBuilder.addQueryParameter("bootstrapVersion", bootstrapVersion)

        val request = Request.Builder()
            .url(httpBuilder.build())
            .cacheControl(CacheControl.FORCE_NETWORK)
            .build()

        val response = client.newCall(request).execute()
        Log.d("DATA_DOWNLOADER", "Download bytes: ${response.body?.contentLength() ?: 0}")

        return response ?: throw IOException("Network error")
    }

    /**
     * Firebase storage endpoint에서 학교 데이터 파일(json)을 Http 캐시를 통해 불러온다.
     */
    fun fetchCached(): Response? {
        val url = BuildConfig.SCHOOL_DATA_URL

        val httpBuilder = url.toHttpUrlOrNull()?.newBuilder()
            ?: throw IllegalArgumentException("malformed data url")
        httpBuilder.addQueryParameter("bootstrapVersion", bootstrapVersion)

        val request = Request.Builder()
            .url(httpBuilder.build())
            .cacheControl(CacheControl.FORCE_CACHE)
            .build()

        val response = client.newCall(request).execute()
        Log.d("DATA_DOWNLOADER", "Loaded Cache bytes: ${response.body?.contentLength() ?: 0}")

        if (response.code == 504) {
            return null
        }
        return response ?: throw IOException("Network error")
    }
}
