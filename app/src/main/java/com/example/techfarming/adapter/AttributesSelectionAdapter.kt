package com.example.techfarming.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.techfarming.R
import com.example.techfarming.utilities.CellClickListener

class AttributesSelectionAdapter(var context: Context, var allData: List<Map<String, Any>>, private val cellClickListener: CellClickListener): RecyclerView.Adapter<AttributesSelectionAdapter.AttributesSelectionViewHolder>() {
    class AttributesSelectionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val attributeTitle: TextView = itemView.findViewById(R.id.attributeTitle)
        val attribute1: TextView = itemView.findViewById(R.id.attribute1)
        val attribute1Price: TextView = itemView.findViewById(R.id.attribute1Price)
        val attribute2: TextView = itemView.findViewById(R.id.attribute2)
        val attribute2Price: TextView = itemView.findViewById(R.id.attribute2Price)
        val attribute3: TextView = itemView.findViewById(R.id.attribute3)
        val attribute3Price: TextView = itemView.findViewById(R.id.attribute3Price)
        val cardSize1: CardView = itemView.findViewById(R.id.cardSize1)
        val cardSize2: CardView = itemView.findViewById(R.id.cardSize2)
        val cardSize3: CardView = itemView.findViewById(R.id.cardSize3)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttributesSelectionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_selection_attributes_ecomm, parent, false)
        return AttributesSelectionAdapter.AttributesSelectionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return allData.size
    }

    override fun onBindViewHolder(holder: AttributesSelectionViewHolder, position: Int) {
        val currentData = allData[position] as Map<String, Any>


        for ((key, values) in currentData){

            cellClickListener.onCellClickListener("1 ${key}")

            holder.attributeTitle.text = key
            var allValues = values as ArrayList<String>
            var currentValue = allValues[0].toString().split(" ")
            holder.attribute1.text = currentValue[0].toString()
            holder.attribute1Price.text = currentValue[1].toString()

            currentValue = allValues[1].toString().split(" ")
            holder.attribute2.text = currentValue[0].toString()
            holder.attribute2Price.text = currentValue[1].toString()

            currentValue = allValues[2].toString().split(" ")
            holder.attribute3.text = currentValue[0].toString()
            holder.attribute3Price.text = currentValue[1].toString()

//            holder.attribute1.text = currentValue[0].toString()
//            holder.attribute1Price.text = currentValue[1].toString()

            holder.cardSize1.setOnClickListener {
                cellClickListener.onCellClickListener("1 ${key}")
                Toast.makeText(context, "You Clicked 1", Toast.LENGTH_SHORT).show()
                holder.cardSize1.backgroundTintList = context.getResources().getColorStateList(R.color.colorPrimary)
                holder.attribute1.setTextColor(Color.parseColor("#FFFFFF"))
                holder.attribute1Price.setTextColor(Color.parseColor("#FFFFFF"))
                holder.attribute1.setTypeface(null, Typeface.BOLD)
                holder.attribute1Price.setTypeface(null, Typeface.BOLD)

                holder.cardSize2.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.attribute2.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute2Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute2.setTypeface(null, Typeface.NORMAL)
                holder.attribute2Price.setTypeface(null, Typeface.NORMAL)

                holder.cardSize3.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.attribute3.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute3Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute3.setTypeface(null, Typeface.NORMAL)
                holder.attribute3Price.setTypeface(null, Typeface.NORMAL)
            }

            holder.cardSize2.setOnClickListener {
                cellClickListener.onCellClickListener("2 ${key}")


                holder.cardSize2.backgroundTintList = context.getResources().getColorStateList(R.color.colorPrimary)
                holder.attribute2.setTextColor(Color.parseColor("#FFFFFF"))
                holder.attribute2Price.setTextColor(Color.parseColor("#FFFFFF"))
                holder.attribute2.setTypeface(null, Typeface.BOLD)
                holder.attribute2Price.setTypeface(null, Typeface.BOLD)

                holder.cardSize3.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.attribute3.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute3Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute3.setTypeface(null, Typeface.NORMAL)
                holder.attribute3Price.setTypeface(null, Typeface.NORMAL)

                holder.cardSize1.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.attribute1.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute1Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute1.setTypeface(null, Typeface.NORMAL)
                holder.attribute1Price.setTypeface(null, Typeface.NORMAL)

                Toast.makeText(context, "You Clicked 2", Toast.LENGTH_SHORT).show()
            }

            holder.cardSize3.setOnClickListener {
                cellClickListener.onCellClickListener("3 ${key}")
                Toast.makeText(context, "You Clicked 3", Toast.LENGTH_SHORT).show()

                holder.cardSize3.backgroundTintList = context.getResources().getColorStateList(R.color.colorPrimary)
                holder.attribute3.setTextColor(Color.parseColor("#FFFFFF"))
                holder.attribute3Price.setTextColor(Color.parseColor("#FFFFFF"))
                holder.attribute3.setTypeface(null, Typeface.BOLD)
                holder.attribute3Price.setTypeface(null, Typeface.BOLD)

                holder.cardSize1.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.attribute1.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute1Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute1.setTypeface(null, Typeface.NORMAL)
                holder.attribute1Price.setTypeface(null, Typeface.NORMAL)

                holder.cardSize2.backgroundTintList = context.getResources().getColorStateList(R.color.secondary)
                holder.attribute2.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute2Price.setTextColor(Color.parseColor("#FF404A3A"))
                holder.attribute2.setTypeface(null, Typeface.NORMAL)
                holder.attribute2Price.setTypeface(null, Typeface.NORMAL)
            }
        }
    }
}