package io.github.dsvdev.letovo_os.util

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

fun buildKeyboard(buttons: List<String>) : ReplyKeyboard {
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