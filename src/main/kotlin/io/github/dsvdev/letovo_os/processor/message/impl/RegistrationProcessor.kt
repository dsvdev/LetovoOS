package io.github.dsvdev.letovo_os.processor.message.impl

import io.github.dsvdev.letovo_os.model.BotAnswer
import io.github.dsvdev.letovo_os.model.UserState
import io.github.dsvdev.letovo_os.processor.message.Processor
import io.github.dsvdev.letovo_os.service.CampParticipantService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class RegistrationProcessor(
    private val campParticipantService: CampParticipantService
) : Processor {

    private val keyboard = UserState.REGISTRATION.keyboard
    private val unknownCommandAnswer = BotAnswer(
        message = "Код доступа неверен или данный пользователь уже зарегистрирован.\n\nЧтобы вернуться в основное меню нажмите *Назад*",
        keyboard = keyboard
    )
    override fun process(message: Message): BotAnswer {
        val text = message.text
        when (text.lowercase()) {
            "назад" -> return back()
            else -> return tryToRegister(message)
        }
    }

    private fun back() = BotAnswer(
        message= "Основное меню",
        newState = UserState.UNREGISTERED,
        keyboard = UserState.UNREGISTERED.keyboard)

    private fun tryToRegister(message: Message) : BotAnswer {
        val code = message.text.lowercase()
        if (campParticipantService.register(
                code,
                message.from.id.toString(),
                message.chatId.toString()
        )) {
            val participant = campParticipantService.getByTelegramId(message.from.id.toString())
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