package io.github.dsvdev.letovo_os.repository

import io.github.dsvdev.letovo_os.model.CampParticipant

interface CampParticipantRepository {
    fun getByTelegramId(id: String): CampParticipant?
    fun getByRegisterCode(code: String): CampParticipant?
    fun save(participant: CampParticipant)
}