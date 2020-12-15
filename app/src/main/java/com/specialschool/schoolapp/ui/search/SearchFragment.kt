package com.specialschool.schoolapp.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.specialschool.schoolapp.databinding.FragmentSearchBinding
import com.specialschool.schoolapp.ui.school.SchoolAdapter
import com.specialschool.schoolapp.ui.search.SearchFragmentDirections.Companion.toSchoolDetail
import com.specialschool.schoolapp.ui.signin.SignInDialogFragment
import com.specialschool.schoolapp.util.EventObserver
import com.specialschool.schoolapp.util.doOnApplyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
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

        model.navigateToEventAction.observe(viewLifecycleOwner, EventObserver { id ->
            findNavController().navigate(toSchoolDetail(id))
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    dismissKeyboard(this@apply)
                    return true
                }

                override fun onQueryTextChange(p0: String): Boolean {
                    model.onSearchQueryChanged(p0)
                    return true
                }
            })

            setOnQueryTextFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    showKeyboard(view.findFocus())
                }
            }
            requestFocus()
        }

        schoolAdapter = SchoolAdapter(model, this)

        binding.schoolInfoRecycler.apply {
            adapter = schoolAdapter
            doOnApplyWindowInsets { v, insets, padding ->
                v.updatePadding(bottom = padding.bottom + insets.systemWindowInsetBottom)
            }
        }

        model.navigateToSignInDialogAction.observe(viewLifecycleOwner, EventObserver {
            openSignInDialog()
        })
    }

    override fun onPause() {
        dismissKeyboard(binding.searchView)
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

    private fun openSignInDialog() {
        val dialog = SignInDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, "dialog_sign_in")
    }
}
