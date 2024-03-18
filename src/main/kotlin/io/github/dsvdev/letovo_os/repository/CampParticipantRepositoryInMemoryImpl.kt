package io.github.dsvdev.letovo_os.repository

import io.github.dsvdev.letovo_os.model.CampParticipant
import org.springframework.stereotype.Repository

@Repository
class CampParticipantRepositoryInMemoryImpl : CampParticipantRepository {
    private val participants: MutableMap<Long, CampParticipant> = mutableMapOf(
        1L to CampParticipant(
            id = 1,
            name = "Александр",
            surname = "Сахаров",
            isAdmin = false,
            registerCode = "test1",
            department = "Отдел связи с общественностью",
            room = "42",
            photoUrl = "https://sun1-28.userapi.com/impg/MfJ5qhkhR08z54r2wAQBqZE7vFkD9AL8TCHyRg/77qrZqXuyTw.jpg?size=2560x1707&quality=96&sign=83a30879f274ab39127d83032e7f5fdb&type=album"
        ),
        2L to CampParticipant(
            id = 2,
            name = "Елизавета",
            surname = "Степанова",
            isAdmin = false,
            registerCode = "test2",
            department = "IT департамент",
            room = "1026",
            photoUrl = "https://sun9-8.userapi.com/impg/TexQpskkUmY8N685YAYJa3dsXck6AE9c18uD-w/Z3grN54hVVo.jpg?size=804x1080&quality=95&sign=42c2f2be38c149cbac27c55cb190a58a&type=album"
        ),
        3L to CampParticipant(
            id = 3,
            name = "Администратор",
            surname = "Администраторов",
            isAdmin = true,
            registerCode = "admin1",
            department = "Управление по управлению всеми управлениями",
            room = "0",
            photoUrl = "https://sun9-17.userapi.com/impg/zO1NTB8pgQtWDtrl9lgPOT0zMGhnKDgjiK6zPA/7jKDeBnQUYI.jpg?size=1440x2160&quality=95&sign=b6e911ee41aa36b8c3fb951aa482d0cb&type=album"
        ),

        )

    override fun getByTelegramId(id: String): CampParticipant? {
        participants.values.forEach { participant ->
            if (participant.telegramId == id) return participant
        }
        return null
    }

    override fun getByRegisterCode(code: String): CampParticipant? {
        participants.values.forEach { participant ->
            if (participant.registerCode == code) return participant
        }
        return null
    }

    override fun save(participant: CampParticipant) {
        participants[participant.id] = participant
    }


}