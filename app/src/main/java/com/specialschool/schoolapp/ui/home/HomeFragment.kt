package com.specialschool.schoolapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.common.collect.ImmutableMap
import com.specialschool.schoolapp.databinding.FragmentHomeBinding
import com.specialschool.schoolapp.ui.home.HomeFragmentDirections.Companion.toSchoolDetail
import com.specialschool.schoolapp.ui.home.HomeFragmentDirections.Companion.toSchoolMap
import com.specialschool.schoolapp.ui.signin.SignInDialogFragment
import com.specialschool.schoolapp.ui.signin.SignOutDialogFragment
import com.specialschool.schoolapp.util.EventObserver
import com.specialschool.schoolapp.util.doOnApplyWindowInsets
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val model: HomeViewModel by viewModels()

    private lateinit var binding: FragmentHomeBinding

    private var adapter: HomeItemAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        model.navigateToSignInDialogAction.observe(viewLifecycleOwner, EventObserver {
            openSignInDialog()
        })

        model.navigateToSignOutDialogAction.observe(viewLifecycleOwner, EventObserver {
            openSignOutDialog()
        })

        binding.recyclerView.apply {
            doOnApplyWindowInsets { v, insets, padding ->
                v.updatePadding(bottom = padding.bottom + insets.systemWindowInsetBottom)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        model.itemResults.observe(viewLifecycleOwner, Observer {
            showItems(binding.recyclerView, it)
        })

        model.navigateToEventAction.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toSchoolDetail(it))
        })

        model.navigateToMapAction.observe(viewLifecycleOwner, EventObserver {
            findNavController().navigate(toSchoolMap(it))
        })
    }

    private fun openSignInDialog() {
        val dialog = SignInDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, "dialog_sign_in")
    }

    private fun openSignOutDialog() {
        val dialog = SignOutDialogFragment()
        dialog.show(requireActivity().supportFragmentManager, "dialog_sign_out")
    }

    private fun showItems(recyclerView: RecyclerView, list: List<Any>?) {
        if (adapter == null) {
            val headerViewBinder = BookmarkItemHeaderViewBinder()
            val itemsViewBinder = BookmarkItemViewBinder(this, model)
            val emptyViewBinder = BookmarkItemEmptyViewBinder()

            @Suppress("UNCHECKED_CAST")
            val viewBinders = ImmutableMap.builder<HomeItemClass, HomeItemBinder>()
                .put(
                    headerViewBinder.modelClass,
                    headerViewBinder as HomeItemBinder
                )
                .put(
                    itemsViewBinder.modelClass,
                    itemsViewBinder as HomeItemBinder
                )
                .put(
                    emptyViewBinder.modelClass,
                    emptyViewBinder as HomeItemBinder
                )
                .build()

            adapter = HomeItemAdapter(viewBinders)
        }

        if (recyclerView.adapter == null) {
            recyclerView.adapter = adapter
        }

        if (list.isNullOrEmpty()) {
            (recyclerView.adapter as HomeItemAdapter).submitList(
                listOf(BookmarkItemHeader, BookmarkItemEmpty)
            )
        } else {
            val items = listOf(BookmarkItemHeader).plus(list)
            (recyclerView.adapter as HomeItemAdapter).submitList(items)
        }

        binding.recyclerView.doOnLayout {
            activity?.reportFullyDrawn()
        }
    }
}
