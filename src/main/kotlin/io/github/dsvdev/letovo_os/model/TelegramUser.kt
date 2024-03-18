package io.github.dsvdev.letovo_os.model

data class TelegramUser(
    val id: String,
    var participantId: Long?,
    var state: UserState,
)
