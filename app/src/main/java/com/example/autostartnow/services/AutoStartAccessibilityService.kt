package com.example.autostartnow.services

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Toast

class AutoStartAccessibilityService: AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.source?.apply {

            val list: List<AccessibilityNodeInfo> = event.source.findAccessibilityNodeInfosByText("START NOW")

            if(list.isNotEmpty()) {
                list.first().performAction(AccessibilityNodeInfo.ACTION_CLICK)
            }

            recycle()
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()

        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show()

        val accessibilityServiceInfo = AccessibilityServiceInfo()
        accessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPE_VIEW_FOCUSED or AccessibilityEvent.TYPE_WINDOWS_CHANGED or AccessibilityEvent.TYPES_ALL_MASK

        accessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_SPOKEN
        accessibilityServiceInfo.notificationTimeout = 100

        this.serviceInfo = accessibilityServiceInfo

    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show()
    }

    override fun onInterrupt() {
        Toast.makeText(this, "Interrupted", Toast.LENGTH_SHORT).show()
    }

}