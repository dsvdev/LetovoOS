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
            department = "Связи с общественностью",
            room = "42",
            photoUrl = "https://sun1-28.userapi.com/impg/MfJ5qhkhR08z54r2wAQBqZE7vFkD9AL8TCHyRg/77qrZqXuyTw.jpg?size=2560x1707&quality=96&sign=83a30879f274ab39127d83032e7f5fdb&type=album"
        ),
        2L to CampParticipant(
            id = 2,
            name = "Иван",
            surname = "Иванов",
            isAdmin = false,
            registerCode = "test2",
            department = "IT департамент",
            room = "11",
            photoUrl = "https://sun9-58.userapi.com/impg/uKt9DckX_FhRLRQlbKvBN67HsUF6aitS0_TTlA/ljTctHIDodI.jpg?size=325x372&quality=96&sign=096704844e2d1ef72f587a62e003dfea&type=album"
        ),
        3L to CampParticipant(
            id = 3,
            name = "Петр",
            surname = "Петров",
            isAdmin = false,
            registerCode = "test3",
            department = "Служба безопасности",
            room = "22",
            photoUrl = "https://sun9-55.userapi.com/impg/z7gm7o7tyP0vlueaVfaSOKmdOeYMxaAtisHqDw/3eBVsHTQB-Y.jpg?size=292x341&quality=96&sign=da33009d5f8e1db25d71d6ceac32eac0&type=album"
        ),
        4L to CampParticipant(
            id = 4,
            name = "Семен",
            surname = "Семенов",
            isAdmin = false,
            registerCode = "test4",
            department = "Научный",
            room = "33",
            photoUrl = "https://sun9-20.userapi.com/impg/kpsuifSFS5yjUTajlRbREVZMQC94uSUoZmS7uw/I5sCej1zOmY.jpg?size=309x389&quality=96&sign=767d65130dc0b8cfbe8bec7965dc0dc7&type=album"
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