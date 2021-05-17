package com.example.project2.Models

data class ToDoData(
val priority:String?=null,
val summary:String?=null,
val property:String?=null,
val dueDate:String?=null,
val assignToVendor:String?=null,
val estimatedCost:String?=null,
val status_Info:String?=null,
        )
{
        companion object {
                const val COLL_NAME = "users"
        }
}