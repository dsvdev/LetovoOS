package io.github.dsvdev.letovo_os.processor

import io.github.dsvdev.letovo_os.model.BotAnswer
import io.github.dsvdev.letovo_os.model.UserState
import io.github.dsvdev.letovo_os.service.CampParticipantService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class RegistrationProcessor(
    private val campParticipantService: CampParticipantService
) : Processor {

    private val keyboard = UserState.REGISTRATION.keyboard
    private val unknownCommandAnswer = BotAnswer(
        message = "Неизвестная команда.\nПожалуйста, пользуйтесь кнопками",
        keyboard = keyboard
    )
    override fun process(update: Update): BotAnswer {
        val message = update.message.text
        when (message.lowercase()) {
            "назад" -> return back()
            else -> return tryToRegister(update)
        }
    }

    private fun back() = BotAnswer(
        message= "Основное меню",
        newState = UserState.UNREGISTERED,
        keyboard = UserState.UNREGISTERED.keyboard)

    private fun tryToRegister(update: Update) : BotAnswer {
        val code = update.message.text.lowercase()
        if (campParticipantService.register(
                code,
                update.message.from.id.toString(),
                update.message.chatId.toString()
        )) {
            val participant = campParticipantService.getByTelegramId(update.message.from.id.toString())
            return BotAnswer(
                newState = UserState.MAIN,
                keyboard = UserState.MAIN.keyboard,
                message = "Добро пожаловать ${participant!!.name} ${participant.surname}"
            )
        } else {
            return unknownCommandAnswer
        }
    }
}