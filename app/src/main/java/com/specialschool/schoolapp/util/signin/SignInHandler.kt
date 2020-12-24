package com.specialschool.schoolapp.util.signin

import android.content.Context
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Presentation Layer에서 사용자 인증을 다루는 인터페이스, Auth provider와 상호 작용한다.
 */
interface SignInHandler {

    fun makeSignInIntent(): LiveData<Intent?>

    fun signIn(resultCode: Int, data: Intent?, onComplete: (SignInResult) -> Unit)

    fun signOut(context: Context, onComplete: () -> Unit = {})
}

/**
 * Firebase AuthUI를 호출하는 [SignInHandler]
 */
class FirebaseAuthSignInHandler(private val externalScope: CoroutineScope) : SignInHandler {

    override fun makeSignInIntent(): LiveData<Intent?> {
        val result = MutableLiveData<Intent?>()

        externalScope.launch {
            val providers = mutableListOf(
                AuthUI.IdpConfig.GoogleBuilder().setSignInOptions(
                    GoogleSignInOptions.Builder()
                        .requestId()
                        .requestEmail()
                        .build()
                ).build()
            )

            result.postValue(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build()
            )
        }
        return result
    }

    override fun signIn(resultCode: Int, data: Intent?, onComplete: (SignInResult) -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut(context: Context, onComplete: () -> Unit) {
        AuthUI.getInstance()
            .signOut(context)
            .addOnCompleteListener { onComplete() }
    }
}
