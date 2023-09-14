package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel : ViewModel() {

    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val _loginSuccess = MutableLiveData<Resource<Boolean>>()
    val loginSuccess: LiveData<Resource<Boolean>> get() = _loginSuccess


    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch{
        _loginSuccess.value = Resource.loading(null)
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _loginSuccess.value =  Resource.success(null)
                }else{
                    _loginSuccess.value =  Resource.error(task.exception?.localizedMessage ?: "error try again",null)
                }
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}