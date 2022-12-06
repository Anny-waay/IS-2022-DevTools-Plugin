package com.annkomova.foxyresttimeplugin.services

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.PreloadingActivity
import com.intellij.openapi.progress.ProgressIndicator

class PreloadingService : PreloadingActivity() {
    override fun preload(indicator: ProgressIndicator) {
        ApplicationManager.getApplication().getService(AppService::class.java)
    }
}