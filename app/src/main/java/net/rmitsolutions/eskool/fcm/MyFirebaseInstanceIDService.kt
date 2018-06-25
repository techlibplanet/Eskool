package net.rmitsolutions.eskool.fcm

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import net.rmitsolutions.eskool.helpers.SharedPrefKeys
import net.rmitsolutions.eskool.helpers.addBulkPrefs
import net.rmitsolutions.eskool.helpers.addToPrefEditor
import net.rmitsolutions.eskool.helpers.getPref

/**
 * Created by RMIT on 26/10/2017.
 */
class MyFirebaseInstanceIDService : FirebaseInstanceIdService() {


    private val TAG = "MyFirebaseIDService"
    private val DEFAULT = "DEFAULT"

    override fun onTokenRefresh() {
        val token: String? = FirebaseInstanceId.getInstance().token
        Log.d(TAG, token)

        storeToken(token)
    }

    private fun storeToken(token: String?) {
        val token : String = getPref(SharedPrefKeys.FCM_TOKEN_KEY,DEFAULT)
        //Log.d("MyToken", token)
        if (token == DEFAULT)
        addBulkPrefs { editor ->
            addToPrefEditor(editor, SharedPrefKeys.FCM_TOKEN_KEY, token)
        }else {
            Log.d("MyToken", token)

        }
    }
}