package com.sanadedu.parent.client.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.sanadedu.parent.client.data.ClientStatus
import com.sanadedu.parent.client.data.UserCredentials
import com.sanadedu.parent.client.domain.repository.ClientRepository
import kotlinx.coroutines.flow.first


private val Context.dataStore by preferencesDataStore(name = "user_credentials")
class ClientRepositoryImpl(context: Context): ClientRepository {
    private val dataStore = context.dataStore

    override suspend fun getUserPreferences(): UserCredentials {
        val preferences = dataStore.data.first()
        return UserCredentials(
            clientStatus = preferences[KEY_CLIENT_STATUS] ?: ClientStatus.NEW_USER.name,
            authToken = preferences[KEY_AUTH_TOKEN] ?: "",
            username = preferences[KEY_USERNAME] ?: "",
            profileImage = preferences[KEY_PROFILE_IMAGE] ?: ""
        )
    }

    override suspend fun setUserCredentials(
        token: String,
        username: String,
        profileImage: String
    ) {
        Log.e("client", token)
        Log.e("client", username)

        dataStore.edit { preferences ->
            preferences[KEY_AUTH_TOKEN] = token
            preferences[KEY_USERNAME] = username
            preferences[KEY_PROFILE_IMAGE] = profileImage
        }
    }

    override suspend fun updateClientStatus(status: ClientStatus) {
        dataStore.edit { preferences ->
            preferences[KEY_CLIENT_STATUS] = status.name
        }
    }

    override suspend fun clearUserData() {
        dataStore.edit { preferences ->
            preferences[KEY_CLIENT_STATUS] = ClientStatus.REGISTERED.name
            preferences[KEY_AUTH_TOKEN] = ""
            preferences[KEY_USERNAME] = ""
            preferences[KEY_PROFILE_IMAGE] = ""
        }
    }

    companion object {
        private val KEY_AUTH_TOKEN = stringPreferencesKey("authToken")
        private val KEY_CLIENT_STATUS = stringPreferencesKey("clientStatus")
        private val KEY_USERNAME = stringPreferencesKey("username")
        private val KEY_PROFILE_IMAGE = stringPreferencesKey("profileImage")
    }
}