package com.example.myapplication1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class AuthViewModel:ViewModel() {
    private val auth :FirebaseAuth = FirebaseAuth.getInstance()
    private val database:FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState


    init{
          checkAuthStatus()
    }


    fun checkAuthStatus(){
        if(auth.currentUser==null)
        {
            _authState.value = AuthState.Unauthenticated
        }
        else{
            _authState.value = AuthState.Authenticated
        }
    }

    fun login(email:String,password:String){

        if(email.isEmpty()|| password.isEmpty())
        {
            _authState.value = AuthState.Error("Email or password can't be empty!")
            return
        }


        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                }
                else{
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }

            }
    }

    fun signup(name:String,surname:String,email:String,password:String){

        if(email.isEmpty()|| password.isEmpty())
        {
            _authState.value = AuthState.Error("Email or password can't be empty!")
            return
        }
        if(name.isEmpty()|| surname.isEmpty())
        {
            _authState.value = AuthState.Error("Name and Surname can't be empty ")
            return
        }


        _authState.value = AuthState.Loading





        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    _authState.value = AuthState.Authenticated

                    val user = FirebaseAuth.getInstance().currentUser
                    val uid = user?.uid ?: return@addOnCompleteListener

                    val userData = mapOf(
                        "name" to name,
                        "surname" to surname,
                        "email" to email
                    )

                    database.reference.child("users").child(uid).setValue(userData)

                }
                else{
                    _authState.value =
                        AuthState.Error(task.exception?.message ?: "Something went wrong")
                }

            }
    }

    fun signout(){
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }



}

sealed class AuthState{
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message:String): AuthState()

}