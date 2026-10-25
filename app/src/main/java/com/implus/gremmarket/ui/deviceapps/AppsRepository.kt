package com.implus.gremmarket.ui.deviceapps

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

// Хранилище приложений и их загрузок. Живет вместе с приложением (см. GremApplication),
// поэтому состояние и загрузки сохраняются при уходе с экрана и возвращении на него.
class AppsRepository(
    private val scope: CoroutineScope
) {
    // Индекс приложения в списке используется как его идентификатор
    var apps by mutableStateOf(sampleApps)
        private set
    var activeDownloads by mutableStateOf<Map<Int, DownloadProgress>>(emptyMap())
        private set

    fun startDownload(appIds: Collection<Int>) {
        appIds.forEach { appId ->
            if (appId in activeDownloads) return@forEach
            val app = apps[appId]
            activeDownloads = activeDownloads + (appId to DownloadProgress(totalBytes = app.sizeBytes))

            scope.launch {
                simulateAppDownload(appId, app.sizeBytes)
            }
        }
    }

    private suspend fun simulateAppDownload(appId: Int, totalBytes: Long) {
        var currentProgress = .0f
        var currentState = DownloadState.RETRIEVAL
        val appName = apps[appId].name

        while (currentState != DownloadState.FINISHED) {
            Log.d("GREM", "App \"$appName\" $currentState simulating with progress of $currentProgress")

            delay(currentState.getRandomDownloadValue().milliseconds)

            val chunk = currentState.getRandomDownloadChunk()
            currentProgress = (currentProgress + chunk).coerceAtMost(1f)

            activeDownloads = activeDownloads + (appId to DownloadProgress(
                progress = currentProgress,
                downloadState = currentState,
                downloadedBytes = (totalBytes * currentProgress).toLong(),
                totalBytes = totalBytes
            ))
            if(currentProgress >= 1f) {
                delay(200.milliseconds)
                currentState = currentState.advanceState()
                currentProgress = 0f
                activeDownloads = activeDownloads + (appId to DownloadProgress(
                    progress = 0f,
                    downloadState = currentState,
                    totalBytes = totalBytes
                ))
            }
        }

        delay(1000.milliseconds)
        activeDownloads = activeDownloads - appId
        updateApp(appId) { it.copy(installed = true) }
    }

    private fun updateApp(appId: Int, transform: (AppInfo) -> AppInfo) {
        apps = apps.toMutableList().also { it[appId] = transform(it[appId]) }
    }
}
