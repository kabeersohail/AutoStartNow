package com.example.autostartnow.extensions

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.content.pm.ServiceInfo
import android.view.accessibility.AccessibilityManager

fun Context.isAccessibilityServiceEnabled(
    service: Class<out AccessibilityService?>,
): Boolean {
    val accessibilityManager = this.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
    val enabledServices = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK)
    for (enabledService in enabledServices) {
        val enabledServiceInfo: ServiceInfo = enabledService.resolveInfo.serviceInfo
        if (enabledServiceInfo.packageName.equals(this.packageName) && enabledServiceInfo.name.equals(service.name)) return true
    }
    return false
}