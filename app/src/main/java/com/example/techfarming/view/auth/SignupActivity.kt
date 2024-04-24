package com.example.techfarming.view.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.techfarming.R

class SignupActivity : AppCompatActivity() {

//                                           }, AuthListener {

//    lateinit var googleSignInClient: GoogleSignInClient
//    val firebaseAuth = FirebaseAuth.getInstance()
//    lateinit var viewModel: AuthViewModel
//    lateinit var signGoogleBtnSignup: SignInButton




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

//        val loginRedirectTextSignup = findViewById<TextView>(R.id.loginRedirectTextSignup)
//         signGoogleBtnSignup = findViewById(R.id.signGoogleBtnSignup)
//
//
//
//
//
//
//        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
//        viewModel.authListener = this
//
//        loginRedirectTextSignup.setOnClickListener {
//            Intent(this, LoginActivity::class.java).also {
//                startActivity(it)
//            }
//        }
//
//        signGoogleBtnSignup.setOnClickListener {
//            toast("Clicked  Signup")
//            signIn()
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        viewModel.returnActivityResult(requestCode, resultCode, data)
//    }
//
//    fun signIn() {
//        toast("Creating Account")
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//        val signInIntent = googleSignInClient.signInIntent
//        startActivityForResult(signInIntent, RC_SIGN_IN)
//
//        viewModel.signupButtonClicked(signGoogleBtnSignup)
//    }
//
//    companion object {
//        private const val TAG = "GoogleActivity"
//        private const val RC_SIGN_IN = 9001
//    }
//
//    override fun onStarted() {
//        val progressSignup = findViewById<ProgressBar>(R.id.progressSignup)
//        progressSignup.show()
//    }
//
//    override fun onSuccess(authRepo: LiveData<String>) {
//        toast("Account Creating")
//
//        authRepo.observe(this, Observer {
//            val progressSignup = findViewById<ProgressBar>(R.id.progressSignup)
//            progressSignup.hide()
//            if (it.toString() == "Success") {
//                toast("Account Created")
//                Intent(this, DashBoardActivity::class.java).also {
//                    startActivity(it)
//                }
//            } else {
//                toast(it.toString())
//            }
//        })
//    }
//
//    override fun onFailure(message: String) {
//        val progressSignup = findViewById<ProgressBar>(R.id.progressSignup)
//        progressSignup.hide()
//        toast("Failure")
//    }
}