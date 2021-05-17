package com.example.project2.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project2.Adapters.PropertiesListAdapter
import com.example.project2.Data.network.MyApi_L_JRX
import com.example.project2.Models.Property
import com.example.project2.R
import com.google.gson.JsonElement
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_properties.*
import kotlinx.android.synthetic.main.activity_properties_list.*
import org.json.JSONObject

class PropertiesListActivity : AppCompatActivity() {

    lateinit var prepertiesList: ArrayList<Property>
    lateinit var propertyListAdapter : PropertiesListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_properties_list)


        prepertiesList= ArrayList<Property>()

        init()


    }

    private fun init(){



        var api = MyApi_L_JRX()
        api.getProperties()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object: SingleObserver<JsonElement>{
                    override fun onSubscribe(d: Disposable) {
                        Log.d("abc","onSubscribe Property List "+d.toString())
                    }

                    override fun onSuccess(t: JsonElement) {
                        Log.d("abc","onSuccess Property List "+t.toString())

                       // t.asJsonObject

                       // var it0 = t as JSONObjectgetJSONArray

                    //    t.asJsonObject.get("data")
                        val array = t.asJsonObject.get("data").asJsonArray //as ArrayList<Address>()
                        if(array!=null) {
                            for (i in 0 until array.size()) {

                                val property0 = array.get(i).asJsonObject


                               // property0.getAsJsonPrimitive("address").toString()




                                var address: String? =  property0.getAsJsonPrimitive("address")?.toString()?.replace("\"", "")
                                var city: String? = property0.getAsJsonPrimitive("city")?.toString()?.replace("\"", "")
                                var country: String? =  property0.getAsJsonPrimitive("country")?.toString()?.replace("\"", "")
                                var image: String?  =  property0.getAsJsonPrimitive("image")?.toString()?.replace("\"", "")
                                var latitude: String? = property0.getAsJsonPrimitive("latitude")?.toString()?.replace("\"", "")
                                var longitude: String?= property0.getAsJsonPrimitive("longitude")?.toString()?.replace("\"", "")
                                var mortageInfo: Boolean= property0.getAsJsonPrimitive("mortageInfo").isBoolean
                                var propertyStatus: Boolean= property0.getAsJsonPrimitive("propertyStatus").isBoolean
                                var purchasePrice: String? = property0.getAsJsonPrimitive("purchasePrice")?.toString()?.replace("\"", "")
                                var state: String? =  property0.getAsJsonPrimitive("state")?.toString()?.replace("\"", "")
                                var userId: String?= property0.getAsJsonPrimitive("userId")?.toString()?.replace("\"", "")
                                var userType: String?= property0.getAsJsonPrimitive("userType")?.toString()?.replace("\"", "")


                                var property = Property(address,city,country,image,latitude,longitude,mortageInfo,
                                    propertyStatus,purchasePrice,state,userId,userType)
                                prepertiesList.add(property)

                             //   prepertiesList.add(array.getJSONObject(i) as )
                            }
                        }









                        propertyListAdapter = PropertiesListAdapter(applicationContext,prepertiesList)
                        recycler_view_properties.adapter = propertyListAdapter
                        recycler_view_properties.layoutManager = LinearLayoutManager(applicationContext)





                    }

                    override fun onError(e: Throwable) {
                        Log.d("abc","onError Property List "+e.toString())
                    }

                }
            )



    }


}