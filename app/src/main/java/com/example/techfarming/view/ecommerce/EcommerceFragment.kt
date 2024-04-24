package com.example.techfarming.view.ecommerce

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R
import com.example.techfarming.adapter.EcommerceAdapter
import com.example.techfarming.utilities.CellClickListener
import com.example.techfarming.viewmodel.EcommViewModel
import com.google.android.material.chip.ChipGroup

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class EcommerceFragment : Fragment(), CellClickListener {
    private lateinit var viewmodel: EcommViewModel
    private var adapter: EcommerceAdapter? = null
    lateinit var ecommerceItemFragment: EcommerceItemFragment
    private lateinit var ecommrcyclr: RecyclerView
    private lateinit var chipgrp: ChipGroup


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            viewmodel = ViewModelProvider(requireActivity())
                .get<EcommViewModel>(EcommViewModel::class.java)



            viewmodel.loadAllEcommItems()
        }


    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewmodel = ViewModelProvider(requireActivity())
            .get<EcommViewModel>(EcommViewModel::class.java)

        viewmodel.ecommLiveData.observe(viewLifecycleOwner, Observer {
            adapter = EcommerceAdapter(requireActivity().applicationContext, it, this)
            ecommrcyclr.adapter = adapter
            ecommrcyclr.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        })

        val view = inflater.inflate(R.layout.fragment_ecommerce, container, false)


        // Find views by ID
        ecommrcyclr = view.findViewById(R.id.ecommrcyclr)
        chipgrp = view.findViewById(R.id.chipgrp)




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "E-Commerce"

        chipgrp.check(R.id.chip1)
        viewmodel.loadAllEcommItems()

        chipgrp.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.chip1 -> {
                    viewmodel.loadAllEcommItems().observe(viewLifecycleOwner, Observer {
                        ecommrcyclr.adapter =
                            EcommerceAdapter(requireActivity().applicationContext, it, this)
                    })
                }
                R.id.chip2 -> {
                    viewmodel.getSpecificCategoryItems("fertilizer")
                        .observe(viewLifecycleOwner, Observer {
                            ecommrcyclr.adapter =
                                EcommerceAdapter(requireActivity().applicationContext, it, this)
                        })
                }

                R.id.chip3 -> {
                    viewmodel.getSpecificCategoryItems("pestiside")
                        .observe(viewLifecycleOwner, Observer {
                            ecommrcyclr.adapter =
                                EcommerceAdapter(requireActivity().applicationContext, it, this)
                        })
                }

                R.id.chip4 -> {
                    viewmodel.getSpecificCategoryItems("machine")
                        .observe(viewLifecycleOwner, Observer {
                            ecommrcyclr.adapter =
                                EcommerceAdapter(requireActivity().applicationContext, it, this)
                        })
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EcommerceFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EcommerceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCellClickListener(name: String) {
        ecommerceItemFragment = EcommerceItemFragment()
        val bundle = Bundle()
        bundle.putString("name", name)
        ecommerceItemFragment.setArguments(bundle)

        val transaction = requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, ecommerceItemFragment, name)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .setReorderingAllowed(true)
            .addToBackStack("ecommItem")
            .commit()

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.cart_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.cart_item -> {
                val cartFragment = CartFragment()
                val transaction = requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, cartFragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .setReorderingAllowed(true)
                    .addToBackStack("cart")
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}