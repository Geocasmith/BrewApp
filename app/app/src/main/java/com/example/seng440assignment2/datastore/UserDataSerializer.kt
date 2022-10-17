package com.example.seng440assignment2.datastore

import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object UserDataSerializer: Serializer<UserData> {

    override val defaultValue: UserData
        get() = UserData()

    override suspend fun readFrom(input: InputStream): UserData {
        return try {
            Json.decodeFromString(
                deserializer = UserData.serializer(),
                string = input.readBytes().decodeToString())
        } catch (e: SerializationException) {
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = UserData.serializer(),
                value = t).encodeToByteArray()
        )
    }

}