package net.rmitsolutions.eskool.fcm

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.text.Html
import net.rmitsolutions.eskool.R
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by RMIT on 26/10/2017.
 */
class MyNotificationManager(applicationContext: Context) {

    val ID_BIG_NOTIFICATION = 234
    val ID_SMALL_NOTIFICATION = 235

    var mCtx: Context? = applicationContext

    fun showBigNotification(title: String, message: String, url: String, intent: Intent) {
        val resultPendingIntent = PendingIntent.getActivity(
                mCtx,
                ID_BIG_NOTIFICATION,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )
        val bigPictureStyle = NotificationCompat.BigPictureStyle()
        bigPictureStyle.setBigContentTitle(title)
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString())
        bigPictureStyle.bigPicture(getBitmapFromURL(url))
        val mBuilder = NotificationCompat.Builder(mCtx)
        val notification: Notification
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx?.resources, R.mipmap.ic_launcher))
                .setContentText(message)
                .build()

        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        val notificationManager = mCtx?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ID_BIG_NOTIFICATION, notification)
    }

    fun showSmallNotification(title: String, message: String, intent: Intent) {
        val resultPendingIntent = PendingIntent.getActivity(
                mCtx,
                ID_SMALL_NOTIFICATION,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )


        val mBuilder = NotificationCompat.Builder(mCtx)
        val notification: Notification
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx?.resources, R.mipmap.ic_launcher))
                .setContentText(message)
                .build()

        notification.flags = notification.flags or Notification.FLAG_AUTO_CANCEL

        val notificationManager = mCtx?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification)
    }

    private fun getBitmapFromURL(strURL: String): Bitmap? {
        try {
            val url = URL(strURL)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

    }
}