package server.lobby_service.daemons

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class DaemonHooks(
    private val deletionDaemon: DeletionDaemon
) {
    private val deletionDaemonThread = Thread(
        deletionDaemon,
        "DeletionDaemon"
    )

    @EventListener(ApplicationReadyEvent::class)
    fun startDeletionDaemon(
    ) {
        deletionDaemonThread.start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stopDeletionDaemon() {
        deletionDaemon.stop()
        deletionDaemonThread.join()
    }
}