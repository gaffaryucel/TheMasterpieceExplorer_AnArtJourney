package com.gaffaryucel.artbookhlttestingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaffaryucel.artbookhlttestingapp.model.UserModel
import com.gaffaryucel.artbookhlttestingapp.util.Util.DATABASE_URL
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    private val database = FirebaseDatabase.getInstance(DATABASE_URL)
    private val myRef = database.reference

    fun registerWithEmailAndPassword(name : String,email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _registrationSuccess.value = task.isSuccessful
                if (task.isSuccessful){
                    saveUser(UserModel(name,email))
                }
            }
    }
    private fun saveUser(user: UserModel) = viewModelScope.launch{
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        myRef.child("users").child(userId).setValue(user).addOnCompleteListener {
            println("complate")
        }
    }
    fun signOut() {
        firebaseAuth.signOut()
    }
}