package com.specialschool.schoolapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.specialschool.schoolapp.databinding.FragmentSearchBinding
import com.specialschool.schoolapp.ui.school.SchoolAdapter
import com.specialschool.schoolapp.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val model: SearchViewModel by viewModels()

    private lateinit var schoolAdapter: SchoolAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model.searchResults.observe(viewLifecycleOwner, Observer {
            schoolAdapter.submitList(it)
        })

        model.navigateToSchoolDetailAction.observe(viewLifecycleOwner, EventObserver { id ->

        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchBtn.apply {
            setOnClickListener {
                model.onSearchQueryChanged(binding.searchText.text.toString())
            }

            setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            requestFocus()
        }

        schoolAdapter = SchoolAdapter(model, this)

        binding.schoolInfoRecycler.apply {
            adapter = schoolAdapter
        }
    }

    override fun onPause() {
        dismissKeyboard(binding.searchEditLayout)
        super.onPause()
    }

    private fun showKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED)
    }

    private fun dismissKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
