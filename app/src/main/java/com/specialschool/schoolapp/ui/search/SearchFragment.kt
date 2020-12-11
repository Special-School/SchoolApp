package com.specialschool.schoolapp.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.databinding.FragmentHomeBinding
import com.specialschool.schoolapp.databinding.FragmentSearchBinding
import com.specialschool.schoolapp.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_school_item.view.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val model: SearchViewModel by viewModels()

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()

    lateinit var recyclerSchool: RecyclerView
    private val testArray: ArrayList<Memo> = ArrayList()
    private val searchArray: ArrayList<Memo> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSearchBinding.inflate(
            inflater, container, false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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


    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(data: Memo) {
            itemView.school_main.text = data.schoolName
            itemView.school_name.text = data.addrDetail

            itemView.setOnClickListener {
                //item 터치시 토스트 메시지 생성
                Toast.makeText(
                    itemView.context,
                    "${data.schoolName}의 세부 정보 화면으로 이동합니다.",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(itemView?.context, SearchTestActivity::class.java)
                var intent_city: Intent = intent.putExtra("city", data.city)
                var intent_establish: Intent = intent.putExtra("establish", data.establish)
                var intent_school_name: Intent = intent.putExtra("school_name", data.schoolName)
                var intent_type = intent.putExtra("type", data.type)
                var intent_opening: Intent = intent.putExtra("opening", data.openDate)
                var intent_tel_num: Intent = intent.putExtra("tel1", data.tel1)
                var intent_tel: Intent = intent.putExtra("tel2", data.tel2)
                var intent_addr_num: Intent = intent.putExtra("addr_num", data.addrNum)
                var intent_addr_detail: Intent = intent.putExtra("addr_detail", data.addrDetail)
                var intent_url: Intent = intent.putExtra("url", data.url)

                startActivity(itemView.context, intent, null)
            }
        }
    }
}
