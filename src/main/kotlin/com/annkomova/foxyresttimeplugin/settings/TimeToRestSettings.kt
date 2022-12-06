package com.annkomova.foxyresttimeplugin.settings

import com.intellij.ide.util.PropertyName

data class TimeToRestSettings (
    @PropertyName("kitty.enabled") var enabled: Boolean = false,
    @PropertyName("kitty.workTime") var workTime: Int = 60,
    @PropertyName("kitty.restTime") var restTime: Int = 5
)