package com.kay.todopublish.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kay.todopublish.data.models.Priority
import com.kay.todopublish.util.Constants.PREFERENCE_KEY
import com.kay.todopublish.util.Constants.PREFERENCE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/** CREATING THIS CLASS FOR SAVING OUR PRIORITY ORDER */

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCE_NAME)

/**
 * I have actually never seen this annotation before, and it works for this app because it is only
 * used in one ViewModel, but I would have added this to DatabaseModule instead and made it a singleton.
 * A singleton means that there will only be one instance of the class for the entire app, which is
 * what you want with a database or persistent storage. If it is scoped to a viewmodel, then every
 * viewmodel you use it in gets a different version of the class, which can cause exceptions and bugs
 * if multiple instances of a database are being accessed.
 */
// ViewModelScoped is needed to inject this dataStore repository inside our ViewModel
@ViewModelScoped
class DataStoreRepository @Inject constructor(
    // Need context in dataStore Repository
    @ApplicationContext private val context: Context
) {
    // Specify the key for our sortState using our constant value
    private object PreferenceKeys {
        val sortKey = stringPreferencesKey(name = PREFERENCE_KEY)
    }

    private val dataStore = context.dataStore

    // Function for saving or persisting that sort state
    // Get the priority from our viewModel and extract the name and store it the preference.
    suspend fun persistSortState(priority: Priority) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.sortKey] = priority.name
        }
    }

    val readSortState: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortState = preferences[PreferenceKeys.sortKey] ?: Priority.NONE.name
            sortState
        }
}
