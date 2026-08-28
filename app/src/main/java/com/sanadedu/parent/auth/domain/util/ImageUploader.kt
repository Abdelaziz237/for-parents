package com.sanadedu.parent.auth.domain.util

import com.sanadedu.parent.feed.data.data_source.remote.KtorClient
import io.ktor.client.request.header
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import java.io.File

object ImageUploader {
    const val BASE_IMAGE_URL = "https://images.sanadedu.com"
    const val BASE_VENDOR = "parents"

//    suspend fun uploadIdmage(preSignedUrl: String, imageFile: File): Boolean {
//        val requestBody: RequestBody = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
//        val request = Request.Builder()
//            .url(preSignedUrl)
//            .put(requestBody)
//            .build()
//
//        val client = OkHttpClient()
//        return try {
//            val response = client.newCall(request).execute()
//            response.isSuccessful
//        } catch (e: Exception) {
//            Log.e("Upload Error", "Error uploading image", e)
//            false
//        }
//    }

    suspend fun uploadImage(preSignedUrl: String, imageFile: File): Boolean {
        val ktorClient = KtorClient.client
        return try {
            val response = ktorClient.put(preSignedUrl) {
                setBody(imageFile.readBytes())
                header(HttpHeaders.ContentType, "image/*")
            }

            response.status.isSuccess()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}