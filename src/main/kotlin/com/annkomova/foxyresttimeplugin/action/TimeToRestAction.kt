package com.annkomova.foxyresttimeplugin.action

import com.annkomova.foxyresttimeplugin.services.AppService
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.annkomova.foxyresttimeplugin.ui.SettingsWindow

class TimeToRestAction : AnAction("Rest Time") {
    override fun actionPerformed(e: AnActionEvent) {
        val applicationService = ApplicationManager.getApplication().getService(AppService::class.java)
        val dialog = SettingsWindow(applicationService)
        if (dialog.showAndGet()) {
            applicationService.saveSettings()
            applicationService.start()
        }
    }
}
