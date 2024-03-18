package io.github.dsvdev.letovo_os.repository

import io.github.dsvdev.letovo_os.model.TelegramUser
import org.springframework.stereotype.Repository

@Repository
interface TelegramUserRepository {
    fun getByTelegramId(id: String): TelegramUser?
    fun save(user: TelegramUser) : TelegramUser
}