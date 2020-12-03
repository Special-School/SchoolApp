package com.specialschool.schoolapp.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.specialschool.schoolapp.R
import com.specialschool.schoolapp.ui.MainActivity
import kotlinx.android.synthetic.main.search_school_item.view.*

//기존 클래스에서 개발 내용 적용 안됨, 기본적인 Fragment 사용 방법으로 돌아감
// Fragment 사용을 위해 SearchViewModel 생성
class SearchFragment : Fragment() {
    //이 부분은 확실히 왜 써야되는지 모름 기본적인 상황에선 안씀, 하단 네비게이션바 사용하는 코드에 있어서 추가함
    private lateinit var searchViewModel: SearchViewModel
    //onCreateView 생성 기초와 거의 동일한 모습이지만 Linear Layout과 호환 되는지 확인 필요

    //test
    var mainActivity: MainActivity? = null

    //리사이클러뷰 클릭이벤트용


    lateinit var recycler_school: RecyclerView
    val test_array: ArrayList<Memo> = ArrayList()
    val search_array: ArrayList<Memo> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val search_button: Button = root.findViewById(R.id.search_btn)
        val search_edit_text: TextInputEditText = root.findViewById(R.id.search_text)
        val search_spinner: Spinner = root.findViewById(R.id.search_spinner)
        val search_edit_layout: TextInputLayout = root.findViewById(R.id.search_edit_layout)


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
            search_spinner.adapter = adapter

            search_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    search_edit_text.setText("")
                    when (search_spinner.selectedItemPosition) {
                        0 -> {
                            search_edit_layout.setHint("")
                        }
                        1 -> {
                            search_edit_layout.setHint("도시이름을 입력해주세요 (예시) 서울, 경기도")
                        }
                        2 -> {
                            search_edit_layout.setHint("전체 학교명을 입력해주세요 (예시) 서울농학교")
                        }
                        3 -> {
                            search_edit_layout.setHint("국립 / 사립 중에 선택해 입력해주세요")
                        }
                        4 -> {
                            search_edit_layout.setHint("장애 영역을 입력해주세요")
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }


        }


        //리사이클러뷰 확인을 위해 add 로 데이터 추가함
        for (i in 1..10) {
            test_array.add(
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
            test_array.add(
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

        recy.adapter = Search_Adapter(requireContext(), test_array)











        search_button.setOnClickListener {
            //Toast.makeText(root.context, "테스트 ${test_array.size}개가 있음", Toast.LENGTH_SHORT).show()
            //Toast.makeText(root.context, "테스트 ${test_array[0].city.equals("서울")}개가 있음", Toast.LENGTH_SHORT).show()

            //검색을 위한 리사이클러뷰 배열 초기화
            search_array.clear()


            //스피너 사용을 위한 when 문
            //내부 for 문과 if 문으로 리사이클러뷰
            when (search_spinner.selectedItemPosition) {
                0 -> {
                    //전체 학교 리스트 출력
                    search_array.addAll(test_array)
                    search_edit_text.setText("")
                }


                1 -> {
                    //도시 검색만
                    for (num: Int in 1..test_array.size) {
                        if (test_array[num - 1].city.equals(search_edit_text.text.toString())) {
                            search_array.addAll(listOf(test_array[num - 1]))
                        }
                    }
                }


                2 -> {
                    for (num: Int in 1..test_array.size) {
                        if (test_array[num - 1].school_name.equals(search_edit_text.text.toString())) {
                            search_array.addAll(listOf(test_array[num - 1]))
                        }
                    }
                }


                3 -> {
                    for (num: Int in 1..test_array.size) {
                        if (test_array[num - 1].establish.equals(search_edit_text.text.toString())) {
                            search_array.addAll(listOf(test_array[num - 1]))
                        }
                    }
                }

                4 -> {
                    for (num: Int in 1..test_array.size) {
                        if (test_array[num - 1].type.equals(search_edit_text.text.toString())) {
                            search_array.addAll(listOf(test_array[num - 1]))
                        }
                    }
                }


            }

            recy.adapter = Search_Adapter(requireContext(), search_array)


        }

        recy.callOnClick()






        return root
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    class Search_Adapter(val context: Context, val test_Array: ArrayList<Memo>) :
        RecyclerView.Adapter<mViewH>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewH {
            return mViewH(
                LayoutInflater.from(context).inflate(R.layout.search_school_item, parent, false)
            )
        }

        override fun onBindViewHolder(holder: mViewH, position: Int) {
            holder.bind_Item(test_Array[position])


        }

        override fun getItemCount(): Int {
            return test_Array.size
        }


    }


    class mViewH(view: View) : RecyclerView.ViewHolder(view) {
        fun bind_Item(data: Memo) {
            itemView.school_main.text = data.school_name
            itemView.school_name.text = data.addr_detail

            itemView.setOnClickListener {
                //item 터치시 토스트 메시지 생성
                Toast.makeText(
                    itemView.context,
                    "${data.school_name}의 세부 정보 화면으로 이동합니다.",
                    Toast.LENGTH_SHORT
                ).show()


                val intent = Intent(itemView?.context, Search_Test_Activity::class.java)
                var intent_city: Intent = intent.putExtra("city", data.city)
                var intent_establish: Intent = intent.putExtra("establish", data.establish)
                var intent_school_name: Intent = intent.putExtra("school_name", data.school_name)
                var intent_type = intent.putExtra("type", data.type)
                var intent_opening: Intent = intent.putExtra("opening", data.open_date)
                var intent_tel_num: Intent = intent.putExtra("tel1", data.tel1)
                var intent_tel: Intent = intent.putExtra("tel2", data.tel2)
                var intent_addr_num: Intent = intent.putExtra("addr_num", data.addr_num)
                var intent_addr_detail: Intent = intent.putExtra("addr_detail", data.addr_detail)
                var intent_url: Intent = intent.putExtra("url", data.url)

                startActivity(itemView.context, intent, null)


            }


        }


    }


}
