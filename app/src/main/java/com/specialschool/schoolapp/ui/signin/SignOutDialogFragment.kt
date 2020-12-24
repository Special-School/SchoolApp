package com.specialschool.schoolapp.ui.signin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.isGone
import androidx.databinding.BindingAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.specialschool.schoolapp.data.signin.AuthenticatedUserInfo
import com.specialschool.schoolapp.databinding.DialogSignOutBinding
import com.specialschool.schoolapp.ui.signin.SignInEvent.REQUEST_SIGN_OUT
import com.specialschool.schoolapp.util.executeAfter
import com.specialschool.schoolapp.util.signin.SignInHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignOutDialogFragment : AppCompatDialogFragment() {

    @Inject
    lateinit var signInHandler: SignInHandler

    private val model: SignInViewModel by viewModels()

    private lateinit var binding: DialogSignOutBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSignOutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.performSignInEvent.observe(viewLifecycleOwner, Observer { request ->
            if (request.peekContent() == REQUEST_SIGN_OUT) {
                request.getContentIfNotHandled()
                signInHandler.signOut(requireContext())
                dismiss()
            }
        })

        binding.executeAfter {
            viewModel = model
            lifecycleOwner = viewLifecycleOwner
        }

        if (showsDialog) {
            (requireDialog() as AlertDialog).setView(binding.root)
        }
    }
}

@BindingAdapter("userName")
fun setUserName(textView: TextView, userInfo: AuthenticatedUserInfo?) {
    val displayName = userInfo?.getDisplayName()
    textView.text = displayName
    textView.isGone = displayName.isNullOrEmpty()
}

@BindingAdapter("userEmail")
fun setUserEmail(textView: TextView, userInfo: AuthenticatedUserInfo?) {
    val email = userInfo?.getEmail()
    textView.text = email
    textView.isGone = email.isNullOrEmpty()
}
