package io.github.dsvdev.letovo_os.processor

import io.github.dsvdev.letovo_os.model.BotAnswer
import io.github.dsvdev.letovo_os.model.CampParticipant
import io.github.dsvdev.letovo_os.model.UserState
import io.github.dsvdev.letovo_os.service.CampParticipantService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update


@Component
class MainProcessor(
    private val campParticipantService: CampParticipantService
) : Processor {

    private val keyboard = UserState.MAIN.keyboard
    private val unknownCommandAnswer = BotAnswer(
        message = "Неизвестная команда.\nПожалуйста, пользуйтесь кнопками",
        keyboard = keyboard
    )
    override fun process(update: Update): BotAnswer {
        val message = update.message.text
        val participant = campParticipantService.getByTelegramId(update.message.from.id.toString())
        participant ?: return BotAnswer()
        when (message.lowercase()) {
            "личное дело" -> return personalInfo(participant)
            "выход" -> return unregister(participant)
            else -> return unknownCommandAnswer
        }
    }

    private fun personalInfo(participant: CampParticipant) : BotAnswer {
        val text = StringBuilder()
            .append("${participant.name} ${participant.surname}\n\n")
            .append("Департамент: ${participant.department}\n\n")
            .append("Комната: ${participant.room}\n\n")
            .append("Рейтинг: 100500")
            .toString()

        return BotAnswer(
            message = text,
            photoUrl = participant.photoUrl,
            keyboard = keyboard
        )
    }

    private fun unregister(participant: CampParticipant) : BotAnswer {
        campParticipantService.unregister(participant.telegramId.toString())
        return BotAnswer(
            message = "Вы успешно вышли из системы",
            newState = UserState.UNREGISTERED,
            keyboard = UserState.UNREGISTERED.keyboard
        )
    }
}