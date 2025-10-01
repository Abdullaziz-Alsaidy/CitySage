package com.aou.citysage

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.uni.sehhaty.Utilitie.USER_UID
import com.uni.sehhaty.Utilitie.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.text.set


class LoginViewModel(
    private val auth: FirebaseAuth = Firebase.auth
) : ViewModel()
{
    var showProfileDialog by mutableStateOf(false)
        private set
    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set
    // Track email and password as mutable states
    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set
    fun updateFirstName(newFirstName: String) {
        firstName = newFirstName
    }

    fun updateLastName(newLastName: String) {
        lastName = newLastName
    }
    fun updatePhone(newPhone: String) {
        phone = newPhone
    }

    // Track authentication state
    private val _currentUser = MutableStateFlow<FirebaseUser?>(null)
    val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    // Track UI state
    private val _loginState = MutableStateFlow<UiState>(UiState.Idle)
    val loginState: StateFlow<UiState> = _loginState.asStateFlow()

    // NEW: Firestore instance
    private val firestore = Firebase.firestore

//    private val authStateListener = FirebaseAuth.AuthStateListener { auth ->
//        auth.currentUser?.let { user ->
//            USER_UID = user.uid
//            Log.d("T!@#", "Auto-login UID: $USER_UID")
//
//            checkOrCreateUserDoc(user.uid)
//        }
//    }

    init {
        //auth.addAuthStateListener(authStateListener)
    }

    // NEW: Function to check/create blank user doc
    private fun checkOrCreateUserDoc(userId: String) {
        viewModelScope.launch {
            try {
                val doc = firestore.collection("Users").document(userId)
                    .get()
                    .await()
                if (!doc.exists()) {
                    showProfileDialog = true
                } else {
                    _loginState.value = UiState.Success(emptyList())
                 //   _loginState.value = UiState.Success(emptyList())
                }
            } catch (e: Exception) {
                _loginState.value = UiState.Error("Error checking profile: ${e.message}")
            }
        }
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun loginUser() {
        viewModelScope.launch{
            _loginState.value = UiState.Loading
            try {
                val result = auth.signInWithEmailAndPassword("qq@qq.com", "qqwwee").await()
                USER_UID = result.user?.uid ?: "Null"
                Log.d("T!@#","User @Uid:${result.user?.uid}")
                // NEW: Check/create user doc on manual login
                result.user?.uid?.let { checkOrCreateUserDoc(it) }

            } catch (e: Exception)
            {
                _loginState.value = UiState.Error(e.message ?: "Login failed")
            }
        }
    }

    fun saveUserProfile() {
        viewModelScope.launch {
            val userId = auth.currentUser?.uid ?: return@launch
            val authEmail = auth.currentUser?.email ?: ""
            val userProfile = UserProfile(
               // uid = userId,
                firstName = firstName,
                lastName = lastName,
                phone = phone,
                email = authEmail, // Use the existing email from login
                bookings = emptyList()
            )
            try {
                firestore.collection("Users").document(userId)
                    .set(userProfile)
                    .await()
                showProfileDialog = false
                _loginState.value = UiState.Success(emptyList())
            } catch (e: Exception) {
                _loginState.value = UiState.Error("Failed to save profile: ${e.message}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val data: List<Any>) : UiState()
    data class Error(val message: String) : UiState()
}