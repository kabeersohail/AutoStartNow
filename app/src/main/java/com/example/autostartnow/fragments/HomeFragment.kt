package com.example.autostartnow.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.autostartnow.databinding.FragmentHomeBinding
import com.example.autostartnow.extensions.isAccessibilityServiceEnabled
import com.example.autostartnow.services.AutoStartAccessibilityService


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private var mediaProjectionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            Toast.makeText(requireContext(),"Accepted $data",Toast.LENGTH_SHORT).show()
        }
    }

    private var requestAccessibilityPermissionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            Toast.makeText(requireContext(),"Accessibility permission granted $data",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val enabled: Boolean = requireContext().isAccessibilityServiceEnabled(AutoStartAccessibilityService::class.java)

        if(!enabled) {
            val requestAccessibilityPermissionIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            requestAccessibilityPermissionLauncher.launch(requestAccessibilityPermissionIntent)
        }

        val intent = Intent(context, AutoStartAccessibilityService::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        requireContext().applicationContext.startService(intent)

        binding.screenCast.setOnClickListener {
            val mediaProjectionManager = requireContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            val mediaProjectionIntent: Intent = mediaProjectionManager.createScreenCaptureIntent()
            mediaProjectionLauncher.launch(mediaProjectionIntent)
        }
    }

}