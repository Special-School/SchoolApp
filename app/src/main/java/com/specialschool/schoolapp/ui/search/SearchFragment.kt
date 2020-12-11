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
import com.specialschool.schoolapp.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_school_item.view.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

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

        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val searchButton: Button = root.findViewById(R.id.search_btn)
        val searchEditText: TextInputEditText = root.findViewById(R.id.search_text)
        val searchSpinner: Spinner = root.findViewById(R.id.search_spinner)
        val searchEditLayout: TextInputLayout = root.findViewById(R.id.search_edit_layout)

        //test

        //test_array.add(Memo(1,"중탑초등학교","성남시"))
        //test_array.add(Memo(2,"상탑초등학교","서울시"))

        //spinner 구현
        ArrayAdapter.createFromResource(
            root.context,
            R.array.search_spinner_item,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            searchSpinner.adapter = adapter
            searchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    searchEditText.setText("")
                    when (searchSpinner.selectedItemPosition) {
                        0 -> {
                            searchEditLayout.setHint("")
                        }
                        1 -> {
                            searchEditLayout.setHint("도시이름을 입력해주세요 (예시) 서울, 경기도")
                        }
                        2 -> {
                            searchEditLayout.setHint("전체 학교명을 입력해주세요 (예시) 서울농학교")
                        }
                        3 -> {
                            searchEditLayout.setHint("국립 / 사립 중에 선택해 입력해주세요")
                        }
                        4 -> {
                            searchEditLayout.setHint("장애 영역을 입력해주세요")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        }

        //리사이클러뷰 확인을 위해 add 로 데이터 추가함
        for (i in 1..10) {
            testArray.add(
                Memo(
                    "서울",
                    "사립",
                    "서울맹학교",
                    "시각장애",
                    "1913.04.01",
                    "02-731-6773",
                    "02-731-6772",
                    "3032",
                    "서울특별시 종로구 필운대로 97 (신교동, 국립서울맹학교)",
                    "www.bl.sc.kr"
                )
            )
            testArray.add(
                Memo(
                    "경기도",
                    "국립",
                    "서울농학교",
                    "청각장애",
                    "1913.04.01",
                    "02-737-0659",
                    "02-737-0378",
                    "3032",
                    "서울특별시 종로구 필운대로 103 서울농학교",
                    "seoulnong.sen.sc.kr"
                )
            )
        }

        //리사이클러뷰 선언 및 layoutManager, adapter 선언
        val recy: RecyclerView = root.findViewById(R.id.school_info_recycler!!) as RecyclerView
        recy.layoutManager = LinearLayoutManager(requireContext())
        recy.setHasFixedSize(true)

        recy.adapter = SearchAdapter(requireContext(), testArray)

        searchButton.setOnClickListener {
            //Toast.makeText(root.context, "테스트 ${test_array.size}개가 있음", Toast.LENGTH_SHORT).show()
            //Toast.makeText(root.context, "테스트 ${test_array[0].city.equals("서울")}개가 있음", Toast.LENGTH_SHORT).show()

            //검색을 위한 리사이클러뷰 배열 초기화
            searchArray.clear()

            //스피너 사용을 위한 when 문
            //내부 for 문과 if 문으로 리사이클러뷰
            when (searchSpinner.selectedItemPosition) {
                0 -> {
                    //전체 학교 리스트 출력
                    searchArray.addAll(testArray)
                    searchEditText.setText("")
                }
                1 -> {
                    //도시 검색만
                    for (num: Int in 1..testArray.size) {
                        if (testArray[num - 1].city.equals(searchEditText.text.toString())) {
                            searchArray.addAll(listOf(testArray[num - 1]))
                        }
                    }
                }
                2 -> {
                    for (num: Int in 1..testArray.size) {
                        if (testArray[num - 1].schoolName.equals(searchEditText.text.toString())) {
                            searchArray.addAll(listOf(testArray[num - 1]))
                        }
                    }
                }
                3 -> {
                    for (num: Int in 1..testArray.size) {
                        if (testArray[num - 1].establish.equals(searchEditText.text.toString())) {
                            searchArray.addAll(listOf(testArray[num - 1]))
                        }
                    }
                }
                4 -> {
                    for (num: Int in 1..testArray.size) {
                        if (testArray[num - 1].type.equals(searchEditText.text.toString())) {
                            searchArray.addAll(listOf(testArray[num - 1]))
                        }
                    }
                }
            }

            recy.adapter = SearchAdapter(requireContext(), searchArray)
        }

        recy.callOnClick()

        return root
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

    class SearchAdapter(val context: Context, val test_Array: ArrayList<Memo>) :
        RecyclerView.Adapter<SearchViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
            return SearchViewHolder(
                LayoutInflater.from(context).inflate(R.layout.search_school_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
            holder.bindItem(test_Array[position])


        }

        override fun getItemCount(): Int {
            return test_Array.size
        }
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
