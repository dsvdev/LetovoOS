package io.github.dsvdev.letovo_os.model

import io.github.dsvdev.letovo_os.util.buildKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard

enum class UserState(val keyboard: ReplyKeyboard) {
    UNREGISTERED(unregisteredKeyboard()),
    REGISTRATION(registrationKeyboard()),
    MAIN(mainKeyboard())
}

private fun unregisteredKeyboard(): ReplyKeyboard =
    buildKeyboard(listOf("Узнать больше", "Регистрация"))

private fun registrationKeyboard(): ReplyKeyboard =
    buildKeyboard(listOf("Назад"))

private fun mainKeyboard(): ReplyKeyboard =
    buildKeyboard(listOf("Личное дело", "Выход"))