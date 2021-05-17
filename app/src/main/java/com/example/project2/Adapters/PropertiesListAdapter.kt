package com.example.project2.Adapters

import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project2.Models.Property
import com.example.project2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_property.view.*
import java.util.*
import kotlin.collections.ArrayList

class PropertiesListAdapter(var mContext: Context, var mList:ArrayList<Property>):
RecyclerView.Adapter<PropertiesListAdapter.MyViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PropertiesListAdapter.MyViewHolder {

        var view = LayoutInflater.from(mContext).inflate(R.layout.raw_property,parent,false)

        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: PropertiesListAdapter.MyViewHolder, position: Int) {

          holder.bind(mList[position])
     }

    override fun getItemCount(): Int {
        return mList.size
    }


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(property: Property){

            itemView.text_adapter_property_name.text = property.city?.replace("\"", "")



            if(!(property.image==null || property.image.isEmpty())){
                Picasso
                    .get()
                    .load(property.image)
                    .into(itemView.text_adapter_property_image)
            }



        }

    }
}