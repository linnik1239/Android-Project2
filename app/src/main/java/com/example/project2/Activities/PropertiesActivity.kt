package com.example.project2.Activities

import android.Manifest
import android.app.Activity
import android.content.CursorLoader
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.project2.Data.network.MyApi_L_JRX
import com.example.project2.Models.Property
import com.example.project2.R
import com.google.gson.JsonObject
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_properties.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream


class PropertiesActivity : AppCompatActivity() {

    var imageData: Uri? =null

    var theLocation:String? = null

    private val REQUEST_CAMERA_CODE  = 100
    private val REQUEST_GALLERY_CODE  = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_properties)


        init()
    }


    private fun init(){
        var api = MyApi_L_JRX()

        button_add_tenant.setOnClickListener {

            var address: String = edit_text_property_address.text.toString()
            var city: String = edit_text_property_city.text.toString()
            var country: String = "Salmon Islands"

            var image: String?
            if(theLocation==null){
                 image  = "https://apolis-property-management.s3.ap-south-1.amazonaws.com/images/1621108418027TexasProperty.jpg"
            }
            else{
                image = theLocation
            }
            var latitude: String ="890"
            var longitude: String="890"
            var mortageInfo: Boolean=true
            var propertyStatus: Boolean=true
            var purchasePrice: String = "8472"
            var state: String = edit_text_property_state_province.text.toString()
            var userId: String="007"
            var userType: String="Tenant"


            var property = Property(
                address, city, country, image, latitude, longitude, mortageInfo,
                propertyStatus, purchasePrice, state, userId, userType
            )



            api.sendProperty(property)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : SingleObserver<ResponseBody> {
                        override fun onSubscribe(d: Disposable) {
                            Log.d("abc", "onSubscribe Property" + d.toString())
                        }

                        override fun onSuccess(t: ResponseBody) {
                            Log.d("abc", "onSuccess Property" + t.toString())
                        }

                        override fun onError(e: Throwable) {
                            Log.d("abc", "onError Property" + e.toString())
                        }

                    }
                )
        }

        button_see_properties.setOnClickListener {
            startActivity(Intent(this, PropertiesListActivity::class.java))

        }

        button_pic_from_gallery_properties.setOnClickListener {
            Log.d("abc", "Before request from gallery")

            requestSingleDextereGalleryPermission()

        }

        button_pic_from_camera_properties.setOnClickListener {
            checkSingleCameraPermission()


        }
        button_pic_upload.setOnClickListener {

//            var key = "image"
//
//            var contentTypy= "Auto"
//            var descrip = "Some description"
//
//
//
//            var desc = "This is a description of this image"
//            val file = File(getRealPathFromURI(imageData!!))



            val fileBody =
                RequestBody.create(MediaType.parse(contentResolver.getType(imageData!!)), file)
            val body = MultipartBody.Builder().addFormDataPart("file-type", "profile")
                .addFormDataPart("photo", "image.png", fileBody)
                .build()




            val requestFile =
                RequestBody.create(MediaType.parse(contentResolver.getType(imageData!!)), file)
            val descBody = RequestBody.create(MediaType.parse("text/plain"), desc)

            val call: Call<JsonObject> = api.uploadImage4(requestFile, descBody)


            call.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                      response?.body()
                    Log.d("abc", "onResponse post image " + response?.body().toString())

                    Log.d("abc", "onResponse post image " + response?.code().toString())
                    Log.d("abc", "onResponse post image " + response?.headers().toString())
                    Log.d("abc", "onResponse post image " + response?.message().toString())
                    Log.d("abc", "onResponse post image " + response?.raw().toString())


                    var ob = response?.body()?.getAsJsonObject("data")

                    theLocation = ob?.getAsJsonPrimitive("location").toString()?.replace("\"", "")

                    Log.d("abc", "The location =" + theLocation)




                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.d("abc", "onResponse post image " + t.toString())

                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })


        }


    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader = CursorLoader(this, contentUri, proj, null, null, null)
        val cursor: Cursor = loader.loadInBackground()
        val column_index: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        val result: String = cursor.getString(column_index)
        cursor.close()
        return result
    }

    fun loadFromUri(photoUri: Uri?): Bitmap? {
        var image: Bitmap? = null
        try {
            // check version of Android on device
            image = if (Build.VERSION.SDK_INT > 27) {
                // on newer versions of Android, use the new decodeBitmap method
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.contentResolver, photoUri!!)
                ImageDecoder.decodeBitmap(source)
            } else {
                // support older versions of Android by using getBitmap
                MediaStore.Images.Media.getBitmap(this.contentResolver, photoUri)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return image
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    private fun requestSingleDextereGalleryPermission(){
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_SHORT)
                        .show()

                    openGalleryForImage()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(applicationContext, "Permission denied", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Permission onPermissionRationaleShouldBeShown",
                        Toast.LENGTH_SHORT
                    )

                    token?.continuePermissionRequest()
                }

            }).check()

    }



    private fun checkSingleCameraPermission(){
        var permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if(permission!= PackageManager.PERMISSION_GRANTED){
            requestCameraPermission()
        }
        else{
            openCamera()
        }

    }

    private fun requestCameraPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            REQUEST_CAMERA_CODE
        )
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALLERY_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "Permission Denied 3", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Permission Granted 3", Toast.LENGTH_SHORT)
                        .show()

                }
            }

            REQUEST_CAMERA_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        applicationContext,
                        "Permission camera Denied 3",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Permission camera Granted 3",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    openCamera()
                }
            }

        }

    }



    private fun openGalleryForImage(){


        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_CODE)

    }

    private fun openCamera(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_CODE){
            Log.d("abc", "Result gallery is ok.")

            imageData = data?.data

        }


        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA_CODE && data != null){
            imageData = data?.data
        }


    }


}