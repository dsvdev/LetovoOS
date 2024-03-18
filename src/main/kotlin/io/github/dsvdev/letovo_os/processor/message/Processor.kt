package io.github.dsvdev.letovo_os.processor.message

import io.github.dsvdev.letovo_os.model.BotAnswer
import org.telegram.telegrambots.meta.api.objects.Message

interface Processor {
    fun process(message: Message) : BotAnswer
}