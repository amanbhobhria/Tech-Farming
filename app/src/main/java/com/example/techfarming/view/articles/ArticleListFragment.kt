package com.example.techfarming.view.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R
import com.example.techfarming.adapter.ArticleListAdapter
import com.example.techfarming.utilities.CellClickListener
import com.example.techfarming.viewmodel.ArticleViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class ArticleListFragment : Fragment(), CellClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: ArticleViewModel
    lateinit var Adapter: ArticleListAdapter
    lateinit var fruitFragment: FruitsFragment

    private lateinit var recyclerArticleListFrag: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        viewModel = ViewModelProvider(requireActivity())
            .get<ArticleViewModel>(ArticleViewModel::class.java)

        viewModel.getAllArticles("article_fruits")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_article_list, container, false)


        // Find views by ID

        recyclerArticleListFrag=view.findViewById(R.id.recyclerArticleListFrag)


        viewModel.message3.observe(viewLifecycleOwner, Observer {

//            Log.d("Art All Data", it[0].data.toString())



                Adapter = ArticleListAdapter(requireActivity().applicationContext, it, this)
                recyclerArticleListFrag.adapter = Adapter
                recyclerArticleListFrag.layoutManager =
                    GridLayoutManager(requireActivity().applicationContext, 2)


        })









        return view



    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArticleListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArticleListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Articles"

    }



    override fun onCellClickListener(name: String) {

        Toast.makeText(requireContext(),"Clicked on Fruits fragment",Toast.LENGTH_SHORT).show()
        fruitFragment = FruitsFragment()
        val bundle = Bundle()
        bundle.putString("name", name)
        fruitFragment.setArguments(bundle)
        val transaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fruitFragment, name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setReorderingAllowed(true)
            .addToBackStack("name")
            transaction.commit()



    }
}