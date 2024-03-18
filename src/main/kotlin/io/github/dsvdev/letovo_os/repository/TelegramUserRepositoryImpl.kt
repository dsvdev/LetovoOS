package io.github.dsvdev.letovo_os.repository

import io.github.dsvdev.letovo_os.model.TelegramUser
import org.springframework.stereotype.Repository

@Repository
class TelegramUserRepositoryImpl: TelegramUserRepository {
    private val users: MutableMap<String, TelegramUser> = HashMap()
    override fun getByTelegramId(id: String): TelegramUser? {
        return users.getOrDefault(id, null)
    }

    override fun save(user: TelegramUser) : TelegramUser {
        users[user.id] = user
        return user
    }
}