package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.UserModel
import com.gaffaryucel.artbookhlttestingapp.util.Resource
import com.gaffaryucel.artbookhlttestingapp.util.Util.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    private val _registrationChecker = MutableLiveData<Resource<String>>()
    val registrationChecker: LiveData<Resource<String>> get() = _registrationChecker

    private val database = FirebaseDatabase.getInstance(DATABASE_URL)
    private val myRef = database.reference

    fun registerWithEmailAndPassword(name : String,email: String, password: String) {
        _registrationChecker.value = Resource.loading(null)
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _registrationSuccess.value = task.isSuccessful
                if (task.isSuccessful){
                    saveUser(UserModel(name,email,""))
                }
            }
    }
    private fun saveUser(user: UserModel) = viewModelScope.launch{
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        myRef.child("users").child(userId).setValue(user).addOnCompleteListener {
            if (it.isSuccessful){
                _registrationChecker.value = Resource.success(null)
            }else{
                _registrationChecker.value = Resource.error(it.exception?.localizedMessage ?: "error try again",null)
            }
        }
    }
    fun signOut() {
        firebaseAuth.signOut()
    }
}