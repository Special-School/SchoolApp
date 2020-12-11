package com.specialschool.schoolapp.ui.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.specialschool.schoolapp.R

class SettingsFragment : Fragment() {
    private lateinit var settingViewModel: SettingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel = ViewModelProvider(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val btn1: Button = root.findViewById(R.id.setting_btn1)
        val btn2: Button = root.findViewById(R.id.setting_btn2)
        val btn3: Button = root.findViewById(R.id.setting_btn3)
        val btn4: Button = root.findViewById(R.id.setting_btn4)
        val btn5: Button = root.findViewById(R.id.setting_btn5)

        val builder = AlertDialog.Builder(root.context)

        btn1.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.setting_dialog, null)
            val dialog = dialogView.findViewById<TextView>(R.id.custom_dialog_text)
            val dialogTitle: TextView = dialogView.findViewById<TextView>(R.id.dialog_title)
            dialogTitle.setText(btn1.text)
            dialog.setText(
                "2020년도 백석대학교 ICT 학부 캡스톤 디자인 수업을 위해 개발된 어플리케이션입니다." +
                        "어플리케이션 사용자는 \'특수학교\' 어플리케이션 모든 서비스를 이용할 수 있습니다."
            )

            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                }.show()
        }
        btn2.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.setting_dialog, null)
            val dialog = dialogView.findViewById<TextView>(R.id.custom_dialog_text)
            val dialogTitle: TextView = dialogView.findViewById(R.id.dialog_title)
            dialogTitle.setText((btn2.text))
            dialog.setText("어플리케이션의 위치 기반 서비스를 이용하는 개인위치정보주체(사용자)간의 권리,의무 및 책임사항, 기타 필요한 사항 규정을 목적으로 합니다.")
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                }.show()
        }
        btn3.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.setting_dialog, null)
            val dialog = dialogView.findViewById<TextView>(R.id.custom_dialog_text)
            val dialogTitle: TextView = dialogView.findViewById<TextView>(R.id.dialog_title)
            dialogTitle.setText((btn3.text))
            dialog.setText("1. Google Developers \n www.developers.google.com")
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                }.show()
        }
        btn4.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.setting_dialog, null)
            val dialog = dialogView.findViewById<TextView>(R.id.custom_dialog_text)
            val dialogTitle: TextView = dialogView.findViewById<TextView>(R.id.dialog_title)
            dialogTitle.setText((btn4.text))
            dialog.setText("version : 1.0.0 (2020-11-18)\nversion : 1.1.0 (2020-12-03)")
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                }.show()
        }
        btn5.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.setting_dialog, null)
            val dialog = dialogView.findViewById<TextView>(R.id.custom_dialog_text)
            val dialogTitle: TextView = dialogView.findViewById<TextView>(R.id.dialog_title)
            dialogTitle.setText((btn5.text))
            dialog.setText("백석대학교\n ICT학부\n문창선\n강예찬\n봉성민")
            builder.setView(dialogView)
                .setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->

                }.show()
        }

        return root
    }
}
