package com.example.itouristui.UI.DisplayMore

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import com.example.itouristui.R
import kotlinx.android.synthetic.main.fragment_settings.Logout_layout_ID

class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val backArrow: ImageView = view.findViewById(R.id.back_arrow_settings_ID)
        backArrow.setOnClickListener {
            requireActivity().onBackPressed()
        }

        Logout_layout_ID.setOnClickListener {
            showLogoutDialogBox()
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