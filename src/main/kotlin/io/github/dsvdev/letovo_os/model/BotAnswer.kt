package io.github.dsvdev.letovo_os.model

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

data class BotAnswer(
    val message: String? = null,
    val photoUrl: String? = null,
    val newState: UserState? = null,
    val keyboard: ReplyKeyboard? = null
)
