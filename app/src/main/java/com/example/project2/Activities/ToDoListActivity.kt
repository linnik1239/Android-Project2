package com.example.project2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.project2.Models.ToDoData
import com.example.project2.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_to_do_list.*

class ToDoListActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var fireabaseDatabase : FirebaseDatabase
    lateinit var databaseReference: DatabaseReference

    var theText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)


        fireabaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = fireabaseDatabase.getReference("users")


        init()


    }



    private fun init(){






        button_pic_save_to_do.setOnClickListener(this)
        button_pic_read_to_do.setOnClickListener(this)


        databaseReference.addValueEventListener(
                object: ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        theText = ""
                        for(data in snapshot.children){
                            var toDo = data.getValue(ToDoData::class.java)
                            edit_text_to_do_priority.setText(toDo?.priority.toString())
                            edit_text_to_do_summary.setText(toDo?.summary)
                            edit_text_to_do_property.setText(toDo?.property)
                            edit_text_to_do_due_date.setText(toDo?.dueDate)
                            edit_text_to_do_assign_to_vendor.setText(toDo?.assignToVendor)
                            edit_text_to_do_estimated_cost.setText(toDo?.estimatedCost)
                            edit_text_to_do_status_info.setText(toDo?.status_Info)
                          //  var toDo = data.getValue(ToDoData::class.java)
                         //   var key = data.key
                         //   theText += toDo?.toString()+" "+key+"\n"
                        }
//                        for(data in snapshot.children){
//
//                            var toDo = data.getValue(ToDoData::class.java)
//                            var key = data.key
//                            databaseReference.child(key!!).removeValue()
//
//                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                     }

                }
        )

    }








    override fun onClick(v: View?) {
        when(v){
            button_pic_save_to_do->{




                val priority:String?= edit_text_to_do_priority.text.toString()
                val summary:String?=  edit_text_to_do_summary.text.toString()
                val property:String?=  edit_text_to_do_property.text.toString()
                val dueDate:String?=  edit_text_to_do_due_date.text.toString()
                val assignToVendor:String?= edit_text_to_do_assign_to_vendor.text.toString()
                val estimatedCost:String?= edit_text_to_do_estimated_cost.text.toString()
                val status_Info:String?=  edit_text_to_do_status_info.text.toString()

                var toDo = ToDoData(priority,summary,property,dueDate,
                        assignToVendor,estimatedCost,status_Info)


                databaseReference.removeValue()


                var toDoID = databaseReference.push().key


                databaseReference.child(toDoID!!).setValue(toDo)
                Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_SHORT).show()


            }
            button_pic_read_to_do ->{
                Toast.makeText(applicationContext, "read: "+theText, Toast.LENGTH_SHORT).show()

            }
        }
    }
}