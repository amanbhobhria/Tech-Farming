package com.example.techfarming.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.example.techfarming.utilities.CartItemBuy
import com.example.techfarming.view.ecommerce.CartFragment
import com.example.techfarming.viewmodel.EcommViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore


class CartItemsAdapter(
    val context: CartFragment,
    val allData: HashMap<String, Object>,
    val cartitembuy: CartItemBuy
) :
    RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder>() {
    var itemCost = 0
    var deliveryCharge = 0
    var quantity = 0
    class CartItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cartItemBuyBtn: Button = itemView.findViewById(R.id.cartItemBuyBtn)
        val removeCartBtn: Button = itemView.findViewById(R.id.removeCartBtn)
        val increaseQtyBtn: Button = itemView.findViewById(R.id.increaseQtyBtn)
        val decreaseQtyBtn: Button = itemView.findViewById(R.id.decreaseQtyBtn)
        val itemNameCart: TextView = itemView.findViewById(R.id.itemNameCart)
        val itemPriceCart: TextView = itemView.findViewById(R.id.itemPriceCart)
        val quantityCountEcomm: TextView = itemView.findViewById(R.id.quantityCountEcomm)
        val deliveryChargeCart: TextView = itemView.findViewById(R.id.deliveryChargeCart)
        val cartItemFirm: TextView = itemView.findViewById(R.id.cartItemFirm)
        val cartItemImage: ImageView = itemView.findViewById(R.id.cartItemImage)
        val cartItemAvailability: TextView = itemView.findViewById(R.id.cartItemAvailability)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemsViewHolder {
        val view = LayoutInflater.from(context.context).inflate(R.layout.single_cart_item, parent, false)
        return CartItemsAdapter.CartItemsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: CartItemsViewHolder, position: Int) {
        val currentData = allData.entries.toTypedArray()[position]
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val firebaseAuth = FirebaseAuth.getInstance()

        val itemQtyRef =
            firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("cart")
                .child("${currentData.key}").child("quantity")

        val itemRef =
            firebaseDatabase.getReference("${firebaseAuth.currentUser!!.uid}").child("cart")
                .child("${currentData.key}")

        holder.cartItemBuyBtn.setOnClickListener {
            var qty = holder.quantityCountEcomm.text.toString().toInt()
            var itemPrice =
                holder.itemPriceCart.text.toString().split("₹") as ArrayList<String>
            var deliveryCharge = holder.deliveryChargeCart.text.toString().toInt()
            Log.d("totalPrice", quantity.toString())
            Log.d("totalPrice", itemCost.toString())
            Log.d("totalPrice", deliveryCharge.toString())
            cartitembuy.addToOrders("${currentData.key}", qty,itemPrice[1].toInt() , deliveryCharge)
        }

        holder.removeCartBtn.setOnClickListener {
            itemRef.removeValue()
        }

        holder.increaseQtyBtn.setOnClickListener {

            holder.quantityCountEcomm.text =
                (holder.quantityCountEcomm.text.toString().toInt() + 1).toString()
            itemQtyRef.setValue(holder.quantityCountEcomm.text.toString().toInt())
        }

        holder.decreaseQtyBtn.setOnClickListener {
            if (holder.quantityCountEcomm.text.toString().toInt() != 1) {
                holder.quantityCountEcomm.text =
                    (holder.quantityCountEcomm.text.toString().toInt() - 1).toString()
                itemQtyRef.setValue(holder.quantityCountEcomm.text.toString().toInt())
            }
        }

        val curr = currentData.value as Map<String, Object>

        val ecommViewModel = EcommViewModel()

        ecommViewModel.getSpecificItem("${currentData.key}").observe(context, Observer {
            itemCost = it.get("price").toString().toInt()
            deliveryCharge = it.get("delCharge").toString().toInt()
            quantity = curr.get("quantity").toString().toInt()
            holder.itemNameCart.text = it.getString("title").toString()
            holder.itemPriceCart.text = "\u20B9" + itemCost.toString()
            holder.quantityCountEcomm.text = quantity.toString()
            holder.deliveryChargeCart.text = deliveryCharge.toString()
            holder.cartItemFirm.text = it.get("retailer").toString()
            holder.cartItemAvailability.text = it.get("availability").toString()


            val allImages = it.get("imageUrl") as ArrayList<String>
            Glide.with(context).load(allImages[0].toString()).into(holder.cartItemImage)
            holder.cartItemBuyBtn.text = "Buy Now: " + "\u20B9" + (itemCost!!*curr.get("quantity").toString().toInt() + deliveryCharge!!).toString()
        })
    }
}