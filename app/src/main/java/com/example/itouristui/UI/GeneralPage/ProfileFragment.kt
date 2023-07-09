package com.example.itouristui.UI.GeneralPage

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.itouristui.Adapters.ProfileViewPagerAdapter
import com.example.itouristui.FirebaseObj
import com.example.itouristui.R
import com.example.itouristui.models.UserPlainData
import com.google.android.material.tabs.TabLayoutMediator
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.custom_logout_dialog.LogoutYesID
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    var titles = arrayOf("Personal","Reviews","Liked cities","Tours")
    private val PICK_IMAGE_REQUEST: Int = 1
    lateinit var myImageUri: Uri
    private var permissions = arrayOf("")
    private val PERMISSION_FILE = 23
    private val ACCESS_FILE = 43

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseObj.fireStore.collection("Users").document(FirebaseObj.uid).get().addOnSuccessListener {
            val user = it.toObject(UserPlainData::class.java)
            ProfileFullNameTextView.text = user!!.fullName
            ProfileCurrentLocationTextView.text = "${user.city}, ${user.country}"
        }

        ProfileViewPager.adapter = ProfileViewPagerAdapter(requireActivity())

        let {
            TabLayoutMediator(ProfileTabLayout, ProfileViewPager){
                tab, position ->
                    tab.text = titles[position]
            }.attach()

            ProfilePictureImageView.setOnClickListener {
                chooseImage()
            }
        }
    }

    private fun chooseImage(){
        if(ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),PERMISSION_FILE)
        }else {
            Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }.also {
                startActivityForResult(Intent.createChooser(it, "Choose an image"), ACCESS_FILE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ACCESS_FILE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            myImageUri = data.data!!


            CropImage.activity(myImageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setActivityTitle("Crop image")
                .setFixAspectRatio(true)
                .setCropMenuCropButtonTitle("Done")
                .start(requireContext(), this)
        }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            val result = CropImage.getActivityResult(data)
            println("123tt")
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                println("123tt" + resultUri.toString())
                ProfilePictureImageView.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Toast.makeText(requireActivity(), "Error!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLogoutDialogBox() {
        val dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_logout_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val yesButton = dialog.findViewById<AppCompatButton>(R.id.LogoutYesID)
        val noButton = dialog.findViewById<AppCompatButton>(R.id.LogoutNoID)

        yesButton.setOnClickListener {

        }

        noButton.setOnClickListener {

        }
    }
}