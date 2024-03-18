package io.github.dsvdev.letovo_os.processor

import io.github.dsvdev.letovo_os.model.BotAnswer
import org.telegram.telegrambots.meta.api.objects.Update

interface Processor {
    fun process(update: Update) : BotAnswer
}