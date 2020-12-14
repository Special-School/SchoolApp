package com.specialschool.schoolapp.ui.signin

import androidx.appcompat.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.specialschool.schoolapp.databinding.DialogSignInBinding
import com.specialschool.schoolapp.util.EventObserver
import com.specialschool.schoolapp.util.signin.SignInHandler
import com.specialschool.schoolapp.ui.signin.SignInEvent.REQUEST_SIGN_IN
import com.specialschool.schoolapp.util.executeAfter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignInDialogFragment : AppCompatDialogFragment() {

    @Inject
    lateinit var signInHandler: SignInHandler

    private val model: SignInViewModel by viewModels()

    private lateinit var binding: DialogSignInBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext()).create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSignInBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        model.performSignInEvent.observe(viewLifecycleOwner, EventObserver { request ->
            if (request == REQUEST_SIGN_IN) {
                activity?.let { activity ->
                    val signInIntent = signInHandler.makeSignInIntent()
                    val observer = object : Observer<Intent?> {
                        override fun onChanged(t: Intent?) {
                            activity.startActivityForResult(t, 42)
                            signInIntent.removeObserver(this)
                        }
                    }
                    signInIntent.observeForever(observer)
                }
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
