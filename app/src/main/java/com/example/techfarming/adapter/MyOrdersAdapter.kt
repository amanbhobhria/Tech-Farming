package com.example.techfarming.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.techfarming.R
import com.example.techfarming.utilities.CartItemBuy
import com.example.techfarming.utilities.CellClickListener
import com.example.techfarming.view.ecommerce.MyOrdersFragment
import com.example.techfarming.viewmodel.EcommViewModel


class MyOrdersAdapter(
    val context: MyOrdersFragment,
    val allData: HashMap<String, Object>,
    val cellClickListener: CellClickListener,
    val cartItemBuy: CartItemBuy
) : RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder>() {
    class MyOrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val myOrderPinCode: TextView = itemView.findViewById(R.id.myOrderPinCode)
        val myOrderItemName: TextView = itemView.findViewById(R.id.myOrderItemName)
        val myOrderItemPrice: TextView = itemView.findViewById(R.id.myOrderItemPrice)
        val myOderDeliveryCharge: TextView = itemView.findViewById(R.id.myOderDeliveryCharge)
        val myOrderDeliveryStatus: TextView = itemView.findViewById(R.id.myOrderDeliveryStatus)
        val myOderItemImage: ImageView = itemView.findViewById(R.id.myOderItemImage)
        val myOrderTimeStamp: TextView = itemView.findViewById(R.id.myOrderTimeStamp)
        val myOrderPurchaseAgain: Button = itemView.findViewById(R.id.myOrderPurchaseAgain)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrdersViewHolder {
        val view = LayoutInflater.from(context.context)
            .inflate(R.layout.single_myorder_item, parent, false)
        return MyOrdersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: MyOrdersViewHolder, position: Int) {
        val myOrder = allData.entries.toTypedArray()[position].value as HashMap<String, Object>
        val viewModel = EcommViewModel()


        val currentItemAllData = viewModel.getSpecificItem("${myOrder.get("productId")}")
            .observe(context, androidx.lifecycle.Observer {
                Log.d("MyOrdersAdapter", it.toString())
                holder.myOrderPinCode.text = myOrder.get("pincode").toString()
                holder.myOrderItemName.text = it!!.getString("title")
                holder.myOrderItemPrice.text =
                    "\u20B9" + (myOrder.get("quantity").toString()
                        .toInt() * myOrder.get("itemCost").toString()
                        .toInt() + myOrder.get("deliveryCost").toString().toInt()).toString()
                holder.myOrderPinCode.text =
                    "Pin Code: " + myOrder.get("pincode").toString()
                holder.myOderDeliveryCharge.text = myOrder.get("deliveryCost").toString()
                holder.myOrderDeliveryStatus.text =
                    myOrder.get("deliveryStatus").toString()
                val allImages = it.get("imageUrl") as List<String>
                Glide.with(context).load(allImages[0]).into(holder.myOderItemImage)
                val date = myOrder.get("time").toString().split(" ") as List<String>
                holder.myOrderTimeStamp.text = date[0].toString()
            })
        holder.myOrderPurchaseAgain.setOnClickListener {
            cartItemBuy.addToOrders(myOrder.get("productId").toString(), myOrder.get("quantity").toString().toInt(), myOrder.get("itemCost").toString().toInt(), myOrder.get("deliveryCost").toString().toInt())
        }
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener("${myOrder.get("productId")}")
        }
    }
}