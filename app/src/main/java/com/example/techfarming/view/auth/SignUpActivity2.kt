package com.example.techfarming.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.techfarming.R
import com.example.techfarming.view.dashboard.DashBoardActivity2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class SignUpActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    // Google Sign-In client
    private lateinit var googleSignInClient: GoogleSignInClient

    // Request code for Google Sign-In
    companion object {
        const val RC_SIGN_IN = 9001
    }


    // Declare views
    private lateinit var titleTextSignup: TextView
    private lateinit var title2TextSignup: TextView
    private lateinit var nameContSignup: TextInputLayout
    private lateinit var nameEditSignup: TextInputEditText
    private lateinit var numberContSignup: TextInputLayout
    private lateinit var numberEditSignup: TextInputEditText
    private lateinit var emailContSignup: TextInputLayout
    private lateinit var emailEditSignup: TextInputEditText
    private lateinit var cityContSignup: TextInputLayout
    private lateinit var cityEditSignup: TextInputEditText
    private lateinit var passwdContSignup: TextInputLayout
    private lateinit var passwdEditSignup: TextInputEditText
    private lateinit var confPasswdContSignup: TextInputLayout
    private lateinit var confPasswdEditSignup: TextInputEditText
    private lateinit var signupBtnSignup: MaterialButton
    private lateinit var orTextSignup: TextView
    private lateinit var signGoogleBtnSignup: SignInButton
    private lateinit var loginRedirectTextSignup: TextView
    private lateinit var progressSignup: ProgressBar



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()



        // Find views by ID
        titleTextSignup = findViewById(R.id.titleTextSignup)
        title2TextSignup = findViewById(R.id.title2TextSignup)
        nameContSignup = findViewById(R.id.nameContSignup)
        nameEditSignup = findViewById(R.id.nameEditSignup)
        numberContSignup = findViewById(R.id.numberContSignup)
        numberEditSignup = findViewById(R.id.numberEditSignup)
        emailContSignup = findViewById(R.id.emailContSignup)
        emailEditSignup = findViewById(R.id.emailEditSignup)
        cityContSignup = findViewById(R.id.cityContSignup)
        cityEditSignup = findViewById(R.id.cityEditSignup)
        passwdContSignup = findViewById(R.id.passwdContSignup)
        passwdEditSignup = findViewById(R.id.passwdEditSignup)
        confPasswdContSignup = findViewById(R.id.confPasswdContSignup)
        confPasswdEditSignup = findViewById(R.id.confPasswdEditSignup)
        signupBtnSignup = findViewById(R.id.signupBtnSignup)
        orTextSignup = findViewById(R.id.orTextSignup)
        signGoogleBtnSignup = findViewById(R.id.signGoogleBtnSignup)
        loginRedirectTextSignup = findViewById(R.id.loginRedirectTextSignup)
        progressSignup = findViewById(R.id.progressSignup)




        // Configure Google Sign-In options
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        // Initialize Google Sign-In client
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Set click listeners
        signupBtnSignup.setOnClickListener {
            signUpWithEmail()
        }
        signGoogleBtnSignup.setOnClickListener {
            signInWithGoogle()
        }
        loginRedirectTextSignup.setOnClickListener {
            // Redirect to login activity
            // You may want to use an Intent to start your login activity
            Intent(this, LoginActivity2::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun signUpWithEmail() {
        val email = emailEditSignup.text.toString()
        val password = passwdEditSignup.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            progressSignup.visibility = View.VISIBLE

            // Create user with email and password
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    progressSignup.visibility = View.GONE
                    if (task.isSuccessful) {
                        // Sign up successful, get user data
                        val user: FirebaseUser? = auth.currentUser
                        updateUI(user)
                    } else {
                        // Sign up failed, display a message to the user
                        Toast.makeText(
                            baseContext,
                            "Sign up failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        updateUI(null)
                    }
                }
        } else {
            Toast.makeText(
                baseContext,
                "Please fill in all fields",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Check if the request code matches the Google Sign-In request code
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign-In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let {
                    firebaseAuthWithGoogle(it)
                }
            } catch (e: ApiException) {
                Log.e("SignupActivity", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in was successful, get user data
                    val user: FirebaseUser? = auth.currentUser
                    updateUI(user)
                } else {
                    // Sign in failed, display a message to the user
                    Toast.makeText(
                        baseContext,
                        "Sign in with Google failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // Handle user sign-in success here, e.g. redirecting to the main activity
            Toast.makeText(
                baseContext,
                "Sign up successful! Welcome, ${user.displayName}",
                Toast.LENGTH_SHORT
            ).show()

            Intent(this, DashBoardActivity2::class.java).also {
                startActivity(it)
            }
            // Redirect to another activity if necessary
        } else {
            // Handle sign-in failure
            Toast.makeText(
                baseContext,
                "Sign up failed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}