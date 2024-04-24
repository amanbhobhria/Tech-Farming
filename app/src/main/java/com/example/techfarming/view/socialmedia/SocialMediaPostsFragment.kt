package com.example.techfarming.view.socialmedia

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R
import com.example.techfarming.adapter.SMPostListAdapter
import com.example.techfarming.viewmodel.SocialMediaViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SocialMediaPostsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SocialMediaPostsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var smCreatePostFragment: SMCreatePostFragment
    private var adapter : SMPostListAdapter? = null
    private lateinit var viewModel: SocialMediaViewModel
    private lateinit var postsRecycler: RecyclerView // Declare RecyclerView here
    private lateinit var createPostFloating: FloatingActionButton // Declare RecyclerView here



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(requireActivity())
            .get<SocialMediaViewModel>(SocialMediaViewModel::class.java)

//        viewModel.loadPosts()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_social_media_posts, container, false)
        postsRecycler=view.findViewById(R.id.postsRecycler)
        createPostFloating=view.findViewById(R.id.createPostFloating)

        // Find views by ID




        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SocialMediaPostsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SocialMediaPostsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getData() {
        val firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()


        firebaseFirestore.collection("posts").orderBy("timeStamp", Query.Direction.DESCENDING).get()
            .addOnSuccessListener {
                Log.d("Posts data", it.documents.toString())
                adapter = SMPostListAdapter(requireActivity().applicationContext, it.documents)


                postsRecycler.adapter = adapter
                postsRecycler.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
            }
            .addOnFailureListener {

            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Social Media"

        getData()
        smCreatePostFragment = SMCreatePostFragment()

        createPostFloating.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, smCreatePostFragment, "smCreate")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .addToBackStack("smCreate")
                .commit()
        }
    }


}