package com.specialschool.schoolapp.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.IdpResponse
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.util.inTransaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SchoolDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_school_detail)

        val schoolId = getSchoolId(intent)
        if (schoolId == null) {
            finish()
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.inTransaction {
                    add(R.id.school_detail_container, SchoolDetailFragment.newInstance(schoolId))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_CANCELED) {
            val response = IdpResponse.fromResultIntent(data)
            response?.error?.let {
                // TODO: Snackbar
            }
        }
    }

    private fun getSchoolId(intent: Intent): String? {
        return intent.data?.getQueryParameter("school_id")
    }
}
