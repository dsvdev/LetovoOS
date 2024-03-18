package io.github.dsvdev.letovo_os.processor.message.impl

import io.github.dsvdev.letovo_os.model.UserState
import io.github.dsvdev.letovo_os.processor.message.Processor
import org.springframework.stereotype.Service

@Service
class ProcessorService(
    private val unregisterProcessor: UnregisterProcessor,
    private val registrationProcessor: RegistrationProcessor,
    private val mainProcessor: MainProcessor
) {
    fun getProcessorByState(state: UserState) : Processor {
        return when (state) {
            UserState.UNREGISTERED -> unregisterProcessor
            UserState.REGISTRATION -> registrationProcessor
            UserState.MAIN -> mainProcessor
        }
    }
}