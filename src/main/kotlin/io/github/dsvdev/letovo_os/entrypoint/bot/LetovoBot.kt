package io.github.dsvdev.letovo_os.entrypoint.bot

import io.github.dsvdev.letovo_os.model.BotAnswer
import io.github.dsvdev.letovo_os.processor.message.impl.ProcessorService
import io.github.dsvdev.letovo_os.service.TelegramUserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

@Component
class LetovoBot(
    private val processorService: ProcessorService,
    private val telegramUserService: TelegramUserService,
    @Value("\${bot.username}")
    private val botUsername: String,
    @Value("\${bot.token}")
    private val botToken: String
) : TelegramLongPollingBot(botToken) {
    override fun getBotUsername() = botUsername

    override fun onUpdateReceived(update: Update?) {
        update ?: return
        if (update.hasMessage()) {
            processMessage(update.message)
        }
        if (update.hasCallbackQuery()) {
            processCallbackQuery(update.callbackQuery)
        }
    }

    private fun processMessage(message: Message) {
        val telegramId = message.from.id.toString()
        val chatId = message.chatId.toString()
        val telegramUser = telegramUserService.getById(telegramId)
        val state = telegramUser.state
        val processor = processorService.getProcessorByState(state)
        val answer = processor.process(message)
        answer.send(chatId, message.from.id.toString())
    }

    private fun processCallbackQuery(callbackQuery: CallbackQuery) {
        TODO()
    }

    private fun BotAnswer.send(chatId: String, userId: String) {
        this.newState?.let { telegramUserService.changeState(userId, it) }
        this.photoUrl?.sendPhoto(chatId)
        this.message?.sendMessage(chatId, this.keyboard)
    }

    private fun String.sendMessage(chatId: String, keyboard: ReplyKeyboard?) {
        val message = SendMessage()
        message.parseMode = ParseMode.MARKDOWN
        message.chatId = chatId
        message.text = this
        message.replyMarkup = keyboard
        execute(message)
    }

    private fun String.sendPhoto(chatId: String) {
        val photo = SendPhoto()
        photo.chatId = chatId
        photo.photo = InputFile(this)
        execute(photo)
    }


//    private fun SendMessage.processRegister(update: Update) {
//        val participant = campParticipantService.getByTelegramId(update.message.from.id.toString())
//        if (update.message.text == "Выход") {
//            campParticipantService.unregister(update.message.from.id.toString())
//            this.text = "Вы успешно вышли\nДля повторного входа введите код регистрации"
//            this.replyMarkup = keyboard(listOf("Узнать больше"))
//            return
//        }
//        if (update.message.text == "Личное дело") {
//            this.text = campParticipantService.getByTelegramId(update.message.from.id.toString()).toString()
//            sendImageFromUrl(participant!!.photoUrl, update.message.chatId.toString())
//        } else {
//            this.text = "Добро пожаловать ${participant!!.name} ${participant.surname}" +
//                    "\nДля получения информации напишите `Личное дело`"
//        }
//        this.replyMarkup = keyboard(listOf("Личное дело", "Выход"))
//    }
//
//    private fun SendMessage.processUnregister(update: Update) {
//        this.replyMarkup = keyboard(listOf("Узнать больше", "Регистрация"))
//        if (update.message.text == "Узнать больше") {
//            this.text = """
//                Какой то рекламный информационный текст про лагерь со ссылками и картинкой
//
//                Купить путевку можно тут - https://sagacamp.ru/
//            """.trimIndent()
//            sendImageFromUrl("https://zashutki.ru/wp-content/uploads/7/1/e/71e517147bb80d3cc1d1c6447579add9.jpeg", update.message.chatId.toString())
//            return
//        }
//
//        if (update.message.text == "Регистрация") {
//            this.text = "Чтобы зарегистрироваться введите код регистрации"
//            sendImageFromUrl("https://zashutki.ru/wp-content/uploads/7/1/e/71e517147bb80d3cc1d1c6447579add9.jpeg", update.message.chatId.toString())
//            return
//        }
//
//        if (registerUser(update)) {
//            this.text = "Вы успешно зарегистрированы"
//            this.replyMarkup = keyboard(listOf("Личное дело", "Выход"))
//        } else {
//            this.text = """
//                Вы не зарегистрированы. Если вы участник Letovo Corp - введите код регистрации
//
//                Хотите узнать больше про LeеtovoCorp? Введите `Узнать больше`
//            """.trimIndent()
//            this.replyMarkup = buildK(listOf("Узнать больше"))
//        }
//    }
//
//    private fun registerUser(update: Update): Boolean {
//        return campParticipantService.register(
//            update.message.text,
//            update.message.from.id.toString(),
//            update.message.chatId.toString()
//        )
//    }
//
//    fun sendImageFromUrl(url: String, chatId: String) {
//        val sendPhotoRequest = SendPhoto()
//
//        sendPhotoRequest.chatId = chatId
//
//        sendPhotoRequest.photo = InputFile(url)
//        try {
//            execute(sendPhotoRequest)
//        } catch (e: TelegramApiException) {
//            e.printStackTrace()
//        }
//    }
}