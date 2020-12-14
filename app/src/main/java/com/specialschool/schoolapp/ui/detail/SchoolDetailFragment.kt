package com.specialschool.schoolapp.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.specialschool.schoolapp.databinding.FragmentSchoolDetailBinding
import com.specialschool.schoolapp.model.School
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SchoolDetailFragment : Fragment() {

    private val model: SchoolDetailViewModel by viewModels()

    private var school: School? = null

    private lateinit var schoolName: String

    private lateinit var binding: FragmentSchoolDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSchoolDetailBinding.inflate(inflater, container, false).apply {
            viewModel = model
            lifecycleOwner = viewLifecycleOwner
        }

        model.school.observe(viewLifecycleOwner, Observer {
            school = it
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        requireNotNull(arguments).apply {
            val schoolId = getString("SCHOOL_ID")
                ?: SchoolDetailFragmentArgs.fromBundle(this).schoolId
            model.setSchoolId(schoolId)
        }
    }

    override fun onStop() {
        super.onStop()
        model.setSchoolId(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        model.userEvent.observe(viewLifecycleOwner, Observer { userEvent ->
            userEvent?.let {

            }
        })
    }

    companion object {
        fun newInstance(schoolId: String): SchoolDetailFragment {
            val bundle = Bundle().apply {
                putString("SCHOOL_ID", schoolId)
            }
            return SchoolDetailFragment().apply { arguments = bundle }
        }
    }
}
