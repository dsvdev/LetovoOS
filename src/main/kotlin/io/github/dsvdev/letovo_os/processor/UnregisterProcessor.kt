package io.github.dsvdev.letovo_os.processor

import io.github.dsvdev.letovo_os.model.BotAnswer
import io.github.dsvdev.letovo_os.model.UserState
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class UnregisterProcessor : Processor {

    private val keyboard = UserState.UNREGISTERED.keyboard
    private val unknownCommandAnswer = BotAnswer(
        message = "Неизвестная команда.\nПожалуйста, пользуйтесь кнопками",
        keyboard = keyboard
    )
    override fun process(update: Update): BotAnswer {
        val message = update.message.text
        message?: return unknownCommandAnswer


        return when (message.lowercase()) {
            "узнать больше" -> moreInfo()
            "регистрация" -> registration()
            "/start" -> firstMeet()
            else -> unknownCommandAnswer
        }
    }

    private fun moreInfo() =
        BotAnswer(
            message = """
                Какой то рекламный информационный текст про лагерь со ссылками и картинкой
                
                Купить путевку можно тут - https://sagacamp.ru/
            """.trimIndent(),
            photoUrl = "https://zashutki.ru/wp-content/uploads/7/1/e/71e517147bb80d3cc1d1c6447579add9.jpeg",
            keyboard = keyboard
        )

    private fun registration() =
        BotAnswer(
            message = "Чтобы зарегистрироваться, введите код регистрации",
            newState = UserState.REGISTRATION,
            keyboard = UserState.REGISTRATION.keyboard
        )

    private fun firstMeet() = BotAnswer(
        message = """
            Доброе пожаловать в LetovoOS!
            
            Если вы участник Letovo CORP нажмите *Регистрация*
            
            Если хотите узнать о нас - нажмите *Узнать больше*
        """.trimIndent(),
        keyboard = keyboard)
}