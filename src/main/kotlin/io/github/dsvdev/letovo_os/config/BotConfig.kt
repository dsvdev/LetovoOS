package io.github.dsvdev.letovo_os.config

import org.springframework.context.annotation.Configuration
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotConfig(bots: List<LongPollingBot>) {
    private val telegramBotsApi: TelegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)

    init {
        bots.forEach {bot ->
            telegramBotsApi.registerBot(bot)
        }
    }
}