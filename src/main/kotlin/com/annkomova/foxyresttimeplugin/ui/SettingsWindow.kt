package com.annkomova.foxyresttimeplugin.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.annkomova.foxyresttimeplugin.services.AppService
import com.annkomova.foxyresttimeplugin.settings.TimeToRestSettings

class SettingsWindow (private val service: AppService) : DialogWrapper(true) {
    private val setting: TimeToRestSettings = service.getSettings()

    init {
        title = "Rest Time Settings"
        setSize(400, 260)
        init()
    }

    override fun createCenterPanel() = panel {
        row {
            checkBox("Enabled", setting::enabled, comment = "Click to enable the function")
        }
        titledRow("Time Setting") {
            row("Work Time") {
                cell {
                    spinner(setting::workTime, minValue = 1, maxValue = 120, step = 5)
                    label("minute")
                }
            }
            row("Rest Time") {
                cell {
                    spinner(setting::restTime, minValue = 1, maxValue = 20, step = 1)
                    label("minute")
                }
            }
        }
    }
}