package com.example.newcanalcollection.data.local.sharedPreference

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.newcanalcollection.data.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class UserDatastore(private val context: Context) {

    companion object {
        private val TOKEN = stringPreferencesKey("token")
        private val USER_NAME = stringPreferencesKey("receiver_name")
        private val USER_PASSWORD = stringPreferencesKey("receiver_password")
        private val USER_FULL_NAME = stringPreferencesKey("receiver_full_name")
        private val USER_MOBILE = stringPreferencesKey("receiver_mobile")
        private val USER_CODE = intPreferencesKey("receiver_code")
        private val IS_ACTIVE = booleanPreferencesKey("is_active")
        private val MOBILE_SERIAL = stringPreferencesKey("mobile_serial")
        private val VERSION_NUMBER = floatPreferencesKey("version_no")
        private val COMPANY_CHAIRMAN = stringPreferencesKey("CompanyChairman")
        private val CLEAR_DATA_PASSWORD = stringPreferencesKey("ClearDataPassword")
        private val AUTO_SYNC = booleanPreferencesKey("auto_sync")
        private val TIME_OUT = longPreferencesKey("timeout")
    }

    val getUser: Flow<User?> = context.dataStore.data
        .map { userPreferences ->
            // No type safety.
            val token = userPreferences[TOKEN] ?: ""
            val userName = userPreferences[USER_NAME] ?: ""
            val password = userPreferences[USER_PASSWORD] ?: ""
            val fullName = userPreferences[USER_FULL_NAME] ?: ""
            val userMobile = userPreferences[USER_MOBILE] ?: ""
            val userCode = userPreferences[USER_CODE] ?: 0
            val isActive = userPreferences[IS_ACTIVE] ?: true
            val mobileSerial = userPreferences[MOBILE_SERIAL] ?: ""
            val versionNo = userPreferences[VERSION_NUMBER] ?: 0.0f
            val companyChairman = userPreferences[COMPANY_CHAIRMAN] ?: ""
            val clearDataPassword = userPreferences[CLEAR_DATA_PASSWORD] ?: ""
            val autoSync = userPreferences[AUTO_SYNC] ?: false
            val timeout = userPreferences[TIME_OUT] ?: 15

            val user = User(
                token = token,
                userName = userName,
                password = password,
                userFullName = fullName,
                userMobile = userMobile,
                userCode = userCode,
                isActive = isActive,
                mobileSerial = mobileSerial,
                versionNo = versionNo,
                companyChairman = companyChairman,
                clearDataPassword = clearDataPassword,
                autoSync = autoSync,
                timeout = timeout
            )

            Log.d("offlineUser changed", "getUser user : $user")

            if (token.isEmpty())
                null
            else
                user
        }

    suspend fun insertUser(user: User?) {
        user?.let {
            context.dataStore.edit { userPreferences ->
                userPreferences[TOKEN] = user.token
                userPreferences[USER_NAME] = user.userName
                userPreferences[USER_PASSWORD] = user.password
                userPreferences[USER_FULL_NAME] = user.userFullName
                userPreferences[USER_MOBILE] = user.userMobile
                userPreferences[USER_CODE] = user.userCode
                userPreferences[IS_ACTIVE] = user.isActive
                userPreferences[MOBILE_SERIAL] = user.mobileSerial
                userPreferences[VERSION_NUMBER] = user.versionNo
                userPreferences[COMPANY_CHAIRMAN] = user.companyChairman
                userPreferences[CLEAR_DATA_PASSWORD] = user.clearDataPassword
                userPreferences[AUTO_SYNC] = user.autoSync
                userPreferences[TIME_OUT] = user.timeout

                Log.d("offlineUser changed", "insert user : $it")

            }
        }
    }

    // Without token & password
    suspend fun updateUser(user: User?) {
        user?.let {
            context.dataStore.edit { userPreferences ->
                userPreferences[USER_NAME] = user.userName
                userPreferences[USER_FULL_NAME] = user.userFullName
                userPreferences[USER_MOBILE] = user.userMobile
                userPreferences[USER_CODE] = user.userCode
                userPreferences[IS_ACTIVE] = user.isActive
                userPreferences[MOBILE_SERIAL] = user.mobileSerial
                userPreferences[VERSION_NUMBER] = user.versionNo
                userPreferences[COMPANY_CHAIRMAN] = user.companyChairman
                userPreferences[CLEAR_DATA_PASSWORD] = user.clearDataPassword
                userPreferences[AUTO_SYNC] = user.autoSync
                userPreferences[TIME_OUT] = user.timeout

                Log.d("offlineUser changed", "insert user : $it")
            }
        }
    }


    suspend fun deleteUser() {
        context.dataStore.edit { userPreferences ->
            userPreferences.clear()
        }
    }

}