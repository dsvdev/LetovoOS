package io.github.dsvdev.letovo_os.service

import io.github.dsvdev.letovo_os.model.CampParticipant
import io.github.dsvdev.letovo_os.repository.CampParticipantRepository
import org.springframework.stereotype.Service

@Service
class CampParticipantServiceImpl(
        private val campParticipantRepository: CampParticipantRepository
): CampParticipantService {
    override fun unregister(id: String) {
        val participant = campParticipantRepository.getByTelegramId(id)
        participant?: return
        participant.isRegister = false
        participant.telegramId = ""
        participant.chatId = ""
        campParticipantRepository.save(participant)
    }

    override fun register(code: String, id: String, chatId: String) : Boolean {
        val participant = campParticipantRepository.getByRegisterCode(code) ?: return false
        participant.isRegister = true
        participant.telegramId = id
        participant.chatId = chatId
        campParticipantRepository.save(participant)
        return true
    }

    override fun getByTelegramId(id: String): CampParticipant? {
        return campParticipantRepository.getByTelegramId(id)
    }

    override fun isRegister(id: String): Boolean {
        return getByTelegramId(id) != null
    }
}