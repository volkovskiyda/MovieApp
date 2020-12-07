package com.gmail.volkovskiyda.movieapp

import android.widget.ImageView
import coil.ImageLoader
import coil.bitmap.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.imageLoader
import coil.loadAny
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Size
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import okio.BufferedSource
import okio.buffer
import okio.source
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@JvmSynthetic
inline fun ImageView.load(
    storageReference: StorageReference?,
    imageLoader: ImageLoader = context.imageLoader,
    builder: ImageRequest.Builder.() -> Unit = {}
): Disposable = loadAny(storageReference, imageLoader, builder)

object FirebaseStorageReferenceFetcher : Fetcher<StorageReference> {
    override suspend fun fetch(
        pool: BitmapPool,
        data: StorageReference,
        size: Size,
        options: Options
    ): FetchResult = SourceResult(data.source(), null, DataSource.NETWORK)

    override fun key(data: StorageReference): String = data.path
}

private suspend fun StorageReference.source(): BufferedSource =
    stream.await().stream.source().buffer()

suspend fun <T> Task<T>.await(): T = if (isComplete) when {
    exception != null -> throw exception!!
    isCanceled -> throw CancellationException()
    else -> result!!
} else suspendCancellableCoroutine { continuation ->
    addOnCompleteListener { task ->
        when {
            task.exception != null -> continuation.resumeWithException(exception!!)
            task.isCanceled -> continuation.cancel()
            else -> continuation.resume(task.result!!)
        }
    }
}

@ExperimentalCoroutinesApi
inline fun <reified T : Any> Query.asFlow(): Flow<List<T>> = callbackFlow {
    val listener = addSnapshotListener { snapshot, _ ->
        snapshot?.toObjects<T>()?.let { objects -> offer(objects) }
    }
    awaitClose { listener.remove() }
}