package io.github.dsvdev.letovo_os.service

import io.github.dsvdev.letovo_os.model.TelegramUser
import io.github.dsvdev.letovo_os.model.UserState
import io.github.dsvdev.letovo_os.repository.TelegramUserRepository
import org.springframework.stereotype.Service

@Service
class TelegramUserServiceImpl(
    private val telegramUserRepository: TelegramUserRepository
): TelegramUserService {
    override fun getById(id: String): TelegramUser {
        val user = telegramUserRepository.getByTelegramId(id)
        if (user != null) return user

        return telegramUserRepository.save(
                TelegramUser(
                    id = id,
                    state = UserState.UNREGISTERED,
                    participantId = null
                )
            )

    }

    override fun changeState(id: String, newState: UserState) {
        val user = getById(id)
        user.state = newState
        telegramUserRepository.save(user)
    }
}