package io.github.dsvdev.letovo_os.model

data class CampParticipant(
        val id: Long,
        val name: String,
        val surname: String,
        var chatId: String? = "",
        var isRegister: Boolean = false,
        val room: String,
        val department: String,
        val isAdmin: Boolean,
        var telegramId: String? = "",
        val registerCode: String,
        val photoUrl: String
) {
        override fun toString(): String = buildString {
                append("$name $surname - личное дело\n\n")
                append("Департамент: $department\n\n")
                append("Комната: $room\n\n")
                append("Рейтинг: 100500\n\n")
        }
}
