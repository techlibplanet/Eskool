package net.rmitsolutions.eskool.fcm

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import net.rmitsolutions.eskool.dashboard.DashboardActivity
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by RMIT on 26/10/2017.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "MyFirebaseMsgService"


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage?.data!!.isNotEmpty()) {
            Log.e(TAG, "Data Payload: " + remoteMessage.data.toString())
            try {
                val json = JSONObject(remoteMessage.data.toString())
                sendPushNotification(json)
            } catch (e: Exception) {
                Log.e(TAG, "Exception: " + e.message)
            }
        }
    }

    private fun sendPushNotification(json: JSONObject) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString())
        try {
            //getting the json data
            val data = json.getJSONObject("data")

            //parsing json data
            val title = data.getString("title")
            val message = data.getString("message")
            val imageUrl = data.getString("image")

            //creating MyNotificationManager object
            val mNotificationManager = MyNotificationManager(applicationContext)

            //creating an intent for the notification
            val intent = Intent(applicationContext, DashboardActivity::class.java)

            //if there is no image
            if (imageUrl == "null") {
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent)
            } else {
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent)
            }
        } catch (e: JSONException) {
            Log.e(TAG, "Json Exception: " + e.message)
        } catch (e: Exception) {
            Log.e(TAG, "Exception: " + e.message)
        }
    }
}