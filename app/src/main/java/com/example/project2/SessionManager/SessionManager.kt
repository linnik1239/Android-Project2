 package com.example.project2.SessionManager

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.practice20mvvm.models.User
import com.example.practice20mvvm.models.UserR

class SessionManager(val mContext: Context) {
    private val FILE_NAME = "my_pref5"
    private val KEY_NAME = "name"
    private val KEY_EMAIL = "email"
    private val KEY_EMAIL_LANDLORD = "email_landlord"
    private val KEY_PASSWORD = "password"
    private val KEY_TYPE = "type"

    private val KEY_IS_LOGGED_IN = "isLoggedIn"
    val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)



    var sharedPreferences = EncryptedSharedPreferences.create(
        FILE_NAME,
        masterKey,
        mContext,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)


//    var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE) //getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun register(user: UserR) {
        editor.putString(KEY_NAME,user.name)
        editor.putString(KEY_EMAIL,user.email)
        editor.putString(KEY_PASSWORD,user.password)
        editor.putString(KEY_TYPE,user.type)
        editor.putString(KEY_EMAIL_LANDLORD,user.landlordEmail)

        editor.putBoolean(KEY_IS_LOGGED_IN,false)
        editor.commit()

    }

    fun setLogin( status:Boolean){
        editor.putBoolean(KEY_IS_LOGGED_IN,status)
        editor.commit()

    }

    fun isLoggedIN(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false)
    }

    fun login(user: User):Boolean{
        var saveEmail = sharedPreferences.getString(KEY_EMAIL,null)
        var savePassword = sharedPreferences.getString(KEY_PASSWORD,null)


        var LogIn = saveEmail.equals(user.email) && savePassword.equals(user.password)
        setLogin(LogIn)

        return LogIn
    }

    fun getName():String?{
        return sharedPreferences.getString(KEY_NAME,null)
    }

    fun getEmail():String?{
        return sharedPreferences.getString(KEY_EMAIL,null)
    }
    fun getType():String?{
        return sharedPreferences.getString(KEY_TYPE,null)
    }


    fun logout(){
        editor.clear()
        editor.commit()
    }


}