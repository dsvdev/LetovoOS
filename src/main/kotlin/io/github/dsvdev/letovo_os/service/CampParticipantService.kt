package io.github.dsvdev.letovo_os.service

import io.github.dsvdev.letovo_os.model.CampParticipant

interface CampParticipantService {
    fun unregister(id: String)
    fun register(code: String, id: String, chatId: String) : Boolean
    fun getByTelegramId(id: String) : CampParticipant?
    fun isRegister(id: String) : Boolean
}