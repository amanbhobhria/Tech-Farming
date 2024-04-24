package com.example.techfarming.view.user

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R
import com.example.techfarming.utilities.CellClickListener
import com.example.techfarming.viewmodel.UserDataViewModel
import com.example.techfarming.viewmodel.UserProfilePostsViewModel
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.IOException
import java.util.UUID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var viewModel: UserProfilePostsViewModel
private lateinit var userDataViewModel: UserDataViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var postID: UUID? = null
    private var storageReference: StorageReference? = null
    private var bitmap: Bitmap? = null
    private var uploadProfOrBack: Int? = null

    val firebaseFirestore = FirebaseFirestore.getInstance()
    val firebaseAuth = FirebaseAuth.getInstance()




    private lateinit var cityEditUserProfile: EditText
    private lateinit var uploadUserBackgroundImage: ImageView
    private lateinit var uploadProfilePictureImage: ImageView
    private lateinit var uploadProgressBarProfile: ProgressBar
    private lateinit var uploadBackProgressProfile: ProgressBar
    private lateinit var userNameUserProfileFrag: TextView
    private lateinit var userCityUserProfileFrag: TextView
    private lateinit var userImageUserFrag: ImageView
    private lateinit var userBackgroundImage: ImageView
    private lateinit var userPostsCountUserProfileFrag: TextView
    private lateinit var userEmailUserProfileFrag: TextView
    private lateinit var aboutValueUserProfileFrag: TextView
    private lateinit var aboutValueEditUserProfileFrag: EditText
    private lateinit var saveBtnAboutUserProfileFrag: Button
    private lateinit var imageEdit: ImageView
    private lateinit var imageChecked: ImageView
    private lateinit var userProfilePostsRecycler: RecyclerView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        viewModel = ViewModelProvider(requireActivity())
            .get<UserProfilePostsViewModel>(UserProfilePostsViewModel::class.java)

        userDataViewModel = ViewModelProvider(requireActivity())
            .get<UserDataViewModel>(UserDataViewModel::class.java)

        storageReference = FirebaseStorage.getInstance().reference
        viewModel.getAllPosts(firebaseAuth.currentUser!!.email)
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_user, container, false)


        cityEditUserProfile = view.findViewById(R.id.cityEditUserProfile)
        uploadUserBackgroundImage = view.findViewById(R.id.uploadUserBackgroundImage)
        uploadProfilePictureImage = view.findViewById(R.id.uploadProfilePictureImage)
        uploadProgressBarProfile = view.findViewById(R.id.uploadProgressBarProfile)
        uploadBackProgressProfile = view.findViewById(R.id.uploadBackProgressProfile)
        userNameUserProfileFrag = view.findViewById(R.id.userNameUserProfileFrag)
        userCityUserProfileFrag = view.findViewById(R.id.userCityUserProfileFrag)
        userImageUserFrag = view.findViewById(R.id.userImageUserFrag)
        userBackgroundImage = view.findViewById(R.id.userBackgroundImage)
        userPostsCountUserProfileFrag = view.findViewById(R.id.userPostsCountUserProfileFrag)
        userEmailUserProfileFrag = view.findViewById(R.id.userEmailUserProfileFrag)
        aboutValueUserProfileFrag = view.findViewById(R.id.aboutValueUserProfileFrag)
        aboutValueEditUserProfileFrag = view.findViewById(R.id.aboutValueEditUserProfileFrag)
        saveBtnAboutUserProfileFrag = view.findViewById(R.id.saveBtnAboutUserProfileFrag)
        imageEdit = view.findViewById(R.id.imageEdit)
        imageChecked = view.findViewById(R.id.imageChecked)
        userProfilePostsRecycler = view.findViewById(R.id.userProfilePostsRecycler)





//        viewModel.liveData1.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
//                viewModel.getAllPostsOfUser(it)
//            }
//        })
//
//
//        viewModel.liveData2.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
//                Log.d("Live Data In Frag", it.toString())
//            }
//        })
//
//        viewModel.userProfilePostsLiveData2.observe(viewLifecycleOwner, Observer {
//            Log.d("Some Part 2", it.toString())
//        })

        return view




    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Profile"





//        addAboutTextUserFrag.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        cityEditUserProfile.visibility = View.GONE

//        viewModel.userProfilePostsLiveData.observe(viewLifecycleOwner, Observer {
//
//            Log.d("Some Part", it.toString())
//        })

//        userDataViewModel.userliveData.observe(viewLifecycleOwner, Observer {
//            Log.d("User Fragment", it.data.toString())
//        })




        uploadProgressBarProfile.visibility = View.GONE
        uploadBackProgressProfile.visibility = View.GONE


        uploadUserBackgroundImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/* video/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
            uploadProfOrBack = 1

            Toast.makeText(requireActivity().applicationContext, "Uploading Background Image", Toast.LENGTH_SHORT).show()
        }

        uploadProfilePictureImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/* video/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE_REQUEST
            )
            uploadProfOrBack = 0
            Toast.makeText(requireActivity().applicationContext, "Uploading your Image", Toast.LENGTH_SHORT).show()
        }


        // Find remaining views using findViewById here

//        userDataViewModel.userliveData.observe(viewLifecycleOwner, Observer {
//            Log.d("User Data in VM Frag", it.get("name").toString())
//            Log.d("Data in User", it.toString())
//
//
//
//            // Add more views as needed
//
//
//
//
//            userNameUserProfileFrag.text = it!!.getString("name")
//            val city = it?.getString("city")
//            if(city == null){
//                userCityUserProfileFrag.text = "City: "
//            } else{
//                userCityUserProfileFrag.text = "City: " + it.getString("city")
//            }
//
//            if(it?.get("profileImage") == null || it?.getString("profileImage").isNullOrBlank()){
//                uploadProfilePictureImage.visibility = View.VISIBLE
//            } else{
//                uploadProfilePictureImage.visibility = View.GONE
//                Glide.with(requireActivity().applicationContext).load(it?.get("profileImage"))
//                    .into(userImageUserFrag)
//            }
//
//
//            if(it?.get("backImage") == null || it?.getString("backImage").isNullOrBlank()){
//                uploadUserBackgroundImage.visibility = View.VISIBLE
//            } else{
//                uploadUserBackgroundImage.visibility = View.GONE
//                Glide.with(requireActivity().applicationContext).load(it?.getString("backImage"))
//                    .into(userBackgroundImage)
//            }
//
//            val posts = it.get("posts") as List<String>
//            userPostsCountUserProfileFrag.text = "Posts: " + posts.size.toString()
//            userEmailUserProfileFrag.text = firebaseAuth.currentUser!!.email
//            val about = it?.getString("about")
//
//            if (about == null || about == "") {
//                aboutValueUserProfileFrag.visibility = View.GONE
//                aboutValueEditUserProfileFrag.visibility = View.GONE
//                saveBtnAboutUserProfileFrag.visibility = View.GONE
//            } else {
//                aboutValueUserProfileFrag.visibility = View.VISIBLE
//                aboutValueEditUserProfileFrag.visibility = View.GONE
//                saveBtnAboutUserProfileFrag.visibility = View.GONE
//                aboutValueUserProfileFrag.text = about
//            }
//
//        })



        imageEdit.setOnClickListener {
            uploadProfilePictureImage.visibility = View.VISIBLE
            uploadUserBackgroundImage.visibility = View.VISIBLE
            imageChecked.visibility = View.VISIBLE
            imageEdit.visibility = View.GONE
            cityEditUserProfile.setText(userCityUserProfileFrag!!.text.toString().removePrefix("City: "))
            cityEditUserProfile.visibility = View.VISIBLE
            aboutValueEditUserProfileFrag.visibility = View.VISIBLE
            aboutValueEditUserProfileFrag.setText(aboutValueUserProfileFrag.text.toString())
            aboutValueUserProfileFrag.visibility = View.GONE
        }

        imageChecked.setOnClickListener {
            uploadProfilePictureImage.visibility = View.GONE
            uploadUserBackgroundImage.visibility = View.GONE
            imageEdit.visibility = View.VISIBLE
            cityEditUserProfile.visibility = View.GONE
            imageChecked.visibility = View.GONE
            userDataViewModel.updateUserField(requireActivity().applicationContext, firebaseAuth.currentUser!!.email.toString() as String, aboutValueEditUserProfileFrag.text.toString(), cityEditUserProfile.text.toString())
            userDataViewModel.getUserData(firebaseAuth.currentUser!!.email.toString())
            aboutValueEditUserProfileFrag.visibility = View.GONE
            aboutValueUserProfileFrag.visibility = View.VISIBLE
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            filePath = data.data

            try {

                bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)

                if(bitmap!=null){
                    Log.d("UserFragment", bitmap.toString())

                    if(uploadProfOrBack == 0){
                        uploadProgressBarProfile.visibility = View.VISIBLE
                        uploadProfilePictureImage.visibility = View.GONE
                    } else if(uploadProfOrBack == 1){
                        uploadBackProgressProfile.visibility = View.VISIBLE
                        uploadUserBackgroundImage.visibility = View.GONE
                    }

                    uploadImage2().setImageBitmap(bitmap)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

    }


    private fun uploadImage2() {
        if (filePath != null) {
            postID = UUID.randomUUID()
            val ref = storageReference?.child("users/" + postID.toString())
            val uploadTask = ref?.putFile(filePath!!)
            Log.d("UserFragment", filePath.toString())
            val urlTask =
                uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(requireActivity().applicationContext, "Error in Uploading", Toast.LENGTH_SHORT).show()
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        Toast.makeText(requireActivity().applicationContext, "Uploading...", Toast.LENGTH_SHORT).show()
                        uploadUserPhotos(downloadUri.toString(), postID!!)

                    } else {
                        // Handle failures
                        Toast.makeText(requireActivity().applicationContext, "Error", Toast.LENGTH_SHORT).show()
                        uploadProgressBarProfile.visibility = View.GONE
                        uploadBackProgressProfile.visibility = View.GONE
                        uploadUserBackgroundImage.visibility = View.VISIBLE
                        uploadProfilePictureImage.visibility = View.VISIBLE
                    }
                }?.addOnFailureListener {
                    Toast.makeText(requireActivity().applicationContext, "Error2", Toast.LENGTH_SHORT).show()
                    uploadProgressBarProfile.visibility = View.GONE
                    uploadBackProgressProfile.visibility = View.GONE
                    uploadUserBackgroundImage.visibility = View.VISIBLE
                    uploadProfilePictureImage.visibility = View.VISIBLE
                }
        } else {

        }
    }




    fun uploadUserPhotos(uri: String?, postID: UUID?){

        if(uploadProfOrBack == 0){
            firebaseFirestore.collection("users").document(firebaseAuth.currentUser!!.email!!)
                .update(mapOf(
                    "profileImage" to uri
                ))
                .addOnSuccessListener {
                    Toast.makeText(requireActivity().applicationContext, "Profile Updated", Toast.LENGTH_SHORT).show()
                    uploadProgressBarProfile.visibility = View.GONE
                    imageEdit.visibility = View.VISIBLE
                    imageChecked.visibility = View.GONE
                    userImageUserFrag.setImageBitmap(bitmap)
                    userDataViewModel.getUserData(firebaseAuth!!.currentUser!!.email.toString())
                }
                .addOnFailureListener {
                    uploadProgressBarProfile.visibility = View.GONE
                    userImageUserFrag.visibility = View.VISIBLE
                    Toast.makeText(requireActivity().applicationContext, "Failed to Update Profile", Toast.LENGTH_SHORT).show()

                }
        }
        else if(uploadProfOrBack == 1){
            firebaseFirestore.collection("users").document(firebaseAuth.currentUser!!.email!!)
                .update(mapOf(
                    "backImage" to uri
                ))
                .addOnSuccessListener {
                    Toast.makeText(requireActivity().applicationContext, "Profile Updated 2", Toast.LENGTH_SHORT).show()
                    uploadBackProgressProfile.visibility = View.GONE
                    userBackgroundImage.setImageBitmap(bitmap)
                    imageEdit.visibility = View.VISIBLE
                    imageChecked.visibility = View.GONE
                    userDataViewModel.getUserData(firebaseAuth!!.currentUser!!.email.toString())
                }
                .addOnFailureListener {
                    uploadBackProgressProfile.visibility = View.GONE
                    userBackgroundImage.setImageBitmap(bitmap)
                    Toast.makeText(requireActivity().applicationContext, "Failed to Update Profile", Toast.LENGTH_SHORT).show()
                }
        }


    }

    override fun onCellClickListener(name: String) {
        val dialog = AlertDialog.Builder(activity)

        dialog.setTitle("Your Post")
            .setMessage("Do you want to edit your post?")
            .setPositiveButton("View") { dialogInterface, i ->

            }
            .setNegativeButton("Delete") { dialogInterface, i ->
                userDataViewModel.deleteUserPost(firebaseAuth.currentUser!!.email!!, name)
                userDataViewModel.getUserData(firebaseAuth.currentUser!!.email.toString())
                viewModel.getAllPosts(firebaseAuth.currentUser!!.email)
            }
            .setNeutralButton("Cancel"){
                    dialogInterface, i ->

            }
            .show()

        Toast.makeText(requireActivity().applicationContext, "You Clicked" + name.toString(), Toast.LENGTH_SHORT).show()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel.liveData3.observe(viewLifecycleOwner, Observer {
//            Log.d("All Posts", it.toString())
//            val adapter = PostListUserProfileAdapter(requireActivity().applicationContext, it, this)
//            userProfilePostsRecycler.adapter = adapter
//
//            userProfilePostsRecycler.layoutManager =
//                LinearLayoutManager(requireActivity().applicationContext)
//            adapter.notifyDataSetChanged()
//
//        })
    }
}
private fun Any.setImageBitmap(bitmap: Bitmap?) {

}