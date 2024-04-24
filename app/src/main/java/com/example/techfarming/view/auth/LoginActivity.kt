package com.example.techfarming.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.techfarming.R
import com.example.techfarming.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth

 class LoginActivity : AppCompatActivity() {
    lateinit var googleSignInClient: GoogleSignInClient

    val firebaseAuth = FirebaseAuth.getInstance()
    private lateinit var viewModel: AuthViewModel

//    protected lateinit var createAccountText: TextView
//    protected lateinit var signInButton: SignInButton
//    protected lateinit var forgotPasswordText: TextView
//



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContentView(R.layout.activity_login)


//
//
//        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
//
//        initializeViews()
//
//
//        viewModel.authListener=this
//
//        if (firebaseAuth.currentUser != null) {
//            startActivity(Intent(this, DashBoardActivity2::class.java))
//            finish()
//        }
//
//
//
//        createAccountText.setOnClickListener {
//            Intent(this, SignUpActivity2::class.java).also {
//                startActivity(it)
//            }
//        }
//
//
//
//        signInButton.setOnClickListener {
//
//            signIn()
//        }
//
//
//        forgotPasswordText.setOnClickListener {
//            val userEmail = findViewById<TextView>(R.id.emailEditLogin).text.toString()
//            if (userEmail.isNullOrEmpty()) {
//                Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
//            } else {
////                Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show()
//                firebaseAuth.sendPasswordResetEmail(userEmail)
//                    .addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            Toast.makeText(this, "Email Sent", Toast.LENGTH_LONG).show()
//                        }
//                    }
//                    .addOnFailureListener {
//                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }
//
//    }
//
//
//
//
//    private fun initializeViews() {
//        createAccountText = findViewById(R.id.createaccountText)
//        signInButton = findViewById(R.id.signGoogleBtnLogin)
//        forgotPasswordText = findViewById(R.id.forgotPasswdTextLogin)
//    }
//
//
//    //googlesignIn
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        viewModel.returnActivityResult(requestCode, resultCode, data)
//    }
//
//    fun signIn() {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//
//        viewModel.loginButtonClicked(null)
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        val a = Intent(Intent.ACTION_MAIN)
//        a.addCategory(Intent.CATEGORY_HOME)
//        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(a)
//    }
//    companion object {
//        private const val TAG = "GoogleActivity"
//        private const val RC_SIGN_IN = 9001
//    }
//
//    override fun onStarted() {
//
//        findViewById<ProgressBar>(R.id.progressLogin).show()
//    }
//
//    override fun onSuccess(authRepo: LiveData<String>) {
//        toast("opened on success")
//        authRepo.observe(this, Observer {
//            findViewById<ProgressBar>(R.id.progressLogin).hide()
//
//            if (it.toString() == "Success") {
//                toast("Logged In")
//                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
//                Intent(this, DashBoardActivity2::class.java).also {
//                    startActivity(it)
//                }
//            }
//
//
//        })
//    }
//
//    override fun onFailure(message: String) {
//        findViewById<ProgressBar>(R.id.progressLogin).hide()
//        toast("Failure")
//    }

}}