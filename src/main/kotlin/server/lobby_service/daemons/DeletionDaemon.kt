package server.lobby_service.daemons

import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import server.lobby_service.mongo.documents.lobbies.Lobby
import server.lobby_service.mongo.documents.lobbies.LobbyState
import server.lobby_service.mongo.repositories.LobbyRepository
import server.lobby_service.mongo.repositories.PlayerRepository
import java.lang.Thread.yield

@Component
@Scope("prototype")
class DeletionDaemon(
    @Autowired private val lobbyRepository: LobbyRepository,
    @Autowired private val playerRepository: PlayerRepository,
) : Runnable {
    @Volatile
    private var running: Boolean = true

    @Volatile
    private var stopped: Boolean = false

    @InternalCoroutinesApi
    @Override
    @Synchronized
    override fun run() {
        while (!stopped) {
            synchronized(running) {
                if (!running)
                    yield()
                val time: Long = System.currentTimeMillis()
                val toDelete: List<Lobby> = lobbyRepository.findAllByCreatedAtBeforeAndState(
                    time - 12 *
                            3600000,
                    LobbyState.PLAYING
                )
                for (lobby in toDelete) {
                    for (player in lobby.players) {
                        playerRepository.delete(player)
                    }
                    lobbyRepository.delete(lobby)
                }
            }
        }
    }

    @Throws(InterruptedException::class)
    fun pause() {
        running = false
    }

    fun stop() {
        stopped = true
    }

    fun resume() {
        running = true
    }
}