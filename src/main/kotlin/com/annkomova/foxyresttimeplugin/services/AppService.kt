package com.annkomova.foxyresttimeplugin.services

import com.intellij.ide.util.PropertiesComponent
import com.annkomova.foxyresttimeplugin.settings.TimeToRestSettings
import com.annkomova.foxyresttimeplugin.ui.DialogWindow
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class AppService {
    private val propertiesComponent: PropertiesComponent = PropertiesComponent.getInstance()
    private val scheduler: ScheduledExecutorService = Executors.newScheduledThreadPool(2)
    private val setting: TimeToRestSettings = TimeToRestSettings()
    private val dialog: DialogWindow = DialogWindow(this)

    var status: Status = Status.IDLE

    private var workFuture: ScheduledFuture<*>? = null
    private var restFuture: ScheduledFuture<*>? = null
    private var lastWorkTime: Long = 0
    private var countdown: Int = 60

    init {
        propertiesComponent.loadFields(setting)
        start()
    }

    fun getSettings(): TimeToRestSettings = setting

    fun saveSettings() {
        propertiesComponent.saveFields(setting)
    }

    fun start() {
        restFuture?.cancel(true)
        workFuture?.cancel(true)
        status = Status.IDLE
        if (setting.enabled) {
            work()
        }
    }

    private fun work() {
        status = Status.WORK
        workFuture = scheduler.schedule(this::rest, getDelay(), TimeUnit.MILLISECONDS)
    }

    private fun rest() {
        status = Status.REST
        countdown = setting.restTime * 60
        restFuture = scheduler.scheduleAtFixedRate(this::countdown, 0, 1, TimeUnit.SECONDS)
        dialog.setTime(countdownToString(countdown))
        dialog.showRestWindow()
    }

    private fun countdown() {
        countdown--
        dialog.setTime(countdownToString(countdown))
        if (countdown <= 0) {
            dialog.dispose()
            restFuture?.cancel(true)
            work()
        }
    }

    private fun getDelay(): Long {
        val now = System.currentTimeMillis()
        val delay = TimeUnit.MINUTES.toMillis(setting.workTime.toLong())
        val targetTime = lastWorkTime + delay
        return if (targetTime > now) {
            targetTime - now
        } else {
            lastWorkTime = now
            delay
        }
    }

    private fun countdownToString(countdown: Int): String {
        val minutes = countdown / 60
        val seconds = countdown % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}

enum class Status {
    IDLE,
    WORK,
    REST
}
