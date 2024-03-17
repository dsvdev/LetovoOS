package io.github.dsvdev.letovo_os.bot

import io.github.dsvdev.letovo_os.service.CampParticipantService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


@Component
class LetovoBot(
    private val campParticipantService: CampParticipantService
) : TelegramLongPollingBot("6413730465:AAFdtN9JHiDLjS43pdEsZvO9qTWYP8Q8E-c") {
    override fun getBotUsername() = "LetovoOSBot"

    override fun onUpdateReceived(update: Update?) {
        update ?: return
        val telegramId = update.message.from.id.toString()
        val message = SendMessage()
        message.chatId = update.message.chatId.toString()
        message.parseMode = "Markdown"
        if (campParticipantService.isRegister(telegramId)) {
            message.processRegister(update)
        } else {
            message.processUnregister(update)
        }
        execute(message)
    }

    private fun SendMessage.processRegister(update: Update) {
        val participant = campParticipantService.getByTelegramId(update.message.from.id.toString())
        if (update.message.text == "Выход") {
            campParticipantService.unregister(update.message.from.id.toString())
            this.text = "Вы успешно вышли\nДля повторного входа введите код регистрации"
            this.replyMarkup = keyboard(listOf("Узнать больше"))
            return
        }
        if (update.message.text == "Личное дело") {
            this.text = campParticipantService.getByTelegramId(update.message.from.id.toString()).toString()
            sendImageFromUrl(participant!!.photoUrl, update.message.chatId.toString())
        } else {
            this.text = "Добро пожаловать ${participant!!.name} ${participant.surname}" +
                    "\nДля получения информации напишите `Личное дело`"
        }
        this.replyMarkup = keyboard(listOf("Личное дело", "Выход"))
    }

    private fun SendMessage.processUnregister(update: Update) {
        if (update.message.text == "Узнать больше") {
            this.text = """
                Какой то рекламный информационный текст про лагерь со ссылками и картинкой
                
                Купить путевку можно тут - https://sagacamp.ru/
            """.trimIndent()
            sendImageFromUrl("https://zashutki.ru/wp-content/uploads/7/1/e/71e517147bb80d3cc1d1c6447579add9.jpeg", update.message.chatId.toString())
            return
        }
        if (registerUser(update)) {
            this.text = "Вы успешно зарегистрированы"
            this.replyMarkup = keyboard(listOf("Личное дело", "Выход"))
        } else {
            this.text = """
                Вы не зарегистрированы. Если вы участник Letovo Corp - введите код регистрации
                
                Хотите узнать больше про LeеtovoCorp? Введите `Узнать больше`
            """.trimIndent()
            this.replyMarkup = keyboard(listOf("Узнать больше"))
        }
    }

    private fun registerUser(update: Update): Boolean {
        return campParticipantService.register(
            update.message.text,
            update.message.from.id.toString(),
            update.message.chatId.toString()
        )
    }

    private fun keyboard(buttons: List<String>) : ReplyKeyboard {
        val keyboard = ReplyKeyboardMarkup()
        keyboard.keyboard = ArrayList()
        buttons.forEach {text ->
            val row = KeyboardRow()
            row.add(text)
            keyboard.keyboard.add(row)
        }
        keyboard.resizeKeyboard = true
        return keyboard
    }

    fun sendImageFromUrl(url: String, chatId: String) {
        val sendPhotoRequest = SendPhoto()

        sendPhotoRequest.chatId = chatId

        sendPhotoRequest.photo = InputFile(url)
        try {
            execute(sendPhotoRequest)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }
}