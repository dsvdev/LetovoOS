package io.github.dsvdev.letovo_os.service

import io.github.dsvdev.letovo_os.model.TelegramUser
import io.github.dsvdev.letovo_os.model.UserState

interface TelegramUserService {
    fun getById(id: String): TelegramUser
    fun changeState(id: String, newState: UserState)
}