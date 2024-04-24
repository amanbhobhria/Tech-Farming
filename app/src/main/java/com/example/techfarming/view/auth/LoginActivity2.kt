package com.example.techfarming.view.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.techfarming.R
import com.example.techfarming.view.dashboard.DashBoardActivity2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity2 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var emailEditLogin: TextInputEditText
    private lateinit var passwordEditLogin: TextInputEditText
    private lateinit var progressLogin: ProgressBar






    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        auth = FirebaseAuth.getInstance()

        // Set up Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Initialize views from XML layout using findViewById()
         emailEditLogin = findViewById(R.id.emailEditLogin)
         passwordEditLogin = findViewById(R.id.passwordEditLogin)
        val loginBtnLogin = findViewById<MaterialButton>(R.id.loginBtnLogin)
        val signGoogleBtnLogin = findViewById<SignInButton>(R.id.signGoogleBtnLogin)
        val forgotPasswdTextLogin = findViewById<TextView>(R.id.forgotPasswdTextLogin)
        val createaccountText = findViewById<TextView>(R.id.createaccountText)
         progressLogin = findViewById(R.id.progressLogin)








        // Handle login button click
        loginBtnLogin.setOnClickListener {
            val email = emailEditLogin.text.toString()
            val password = passwordEditLogin.text.toString()

            if (validateForm(email, password)) {
                loginWithEmailAndPassword(email, password)
            }



        }


        // Handle Google Sign-In button click
        signGoogleBtnLogin.setOnClickListener {
            googleSignIn()
        }

        // Handle create account text click
        createaccountText.setOnClickListener {
            navigateToSignUp()
        }

        // Handle forgot password text click
        forgotPasswdTextLogin.setOnClickListener {
            navigateToForgotPassword()
        }




    }


//    / Validate the login form inputs
    private fun validateForm(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            emailEditLogin.error = "Email is required ."
            return false
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditLogin.error = "Password is required."
            return false
        }

        return true
    }


    // Handle login with email and password
    private fun loginWithEmailAndPassword(email: String, password: String) {
        progressLogin.visibility = android.view.View.VISIBLE
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressLogin.visibility = android.view.View.INVISIBLE
                if (task.isSuccessful) {
                    // Login successful
                    val user = auth.currentUser
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    Intent(this, DashBoardActivity2::class.java).also {
                        startActivity(it)
                        // Navigate to the desired activity (e.g., dashboard)
                    }
                } else {
                    // Login failed
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Start Google Sign-In process
    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }



    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Google Sign-In was successful, authenticate with Firebase
            firebaseAuthWithGoogle(account)
        } catch (e: ApiException) {
            // Google Sign-In failed
            Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in successful
                    Toast.makeText(this, "Google Sign-In successful", Toast.LENGTH_SHORT).show()
                    // Navigate to the desired activity (e.g., dashboard)

                    Intent(this, DashBoardActivity2::class.java).also {
                        startActivity(it)
                        // Navigate to the desired activity (e.g., dashboard)
                    }


                } else {
                    // Sign in failed
                    Toast.makeText(this, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    // Navigation methods
    private fun navigateToSignUp() {

        Intent(this, SignUpActivity2::class.java).also {
            startActivity(it)
        }


        // Implement your navigation logic to the sign-up activity
    }

    private fun navigateToForgotPassword() {
        // Implement your navigation logic to the forgot password activity

        val userEmail = findViewById<TextView>(R.id.emailEditLogin).text.toString()
        if (userEmail.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
        } else {
//                Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
            auth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Email Sent", Toast.LENGTH_LONG).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
        }

    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }

}