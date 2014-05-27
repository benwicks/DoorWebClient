package com.benjaminwicks.doorcontroller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class WiFiStateReceiver extends BroadcastReceiver {
	private final int mId = 0;
	private String status;
	private Notification notif;
	NotificationManager mNotificationManager;

	class CallDoorStatusTask extends AsyncTask<Void, Void, String> {
		private Exception exception;

		protected String doInBackground(Void... params) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				String url = "http://10.3.3.25/stat";
				HttpGet httpGet = new HttpGet(url);
				// try five times
				int numTries = 0;
				while (numTries < 5) {
					try {
						HttpResponse response = httpClient.execute(httpGet);
						StatusLine statusLine = response.getStatusLine();
						if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
							HttpEntity entity = response.getEntity();
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							entity.writeTo(out);
							out.close();
							status = out.toString();
							Log.i("WiFiStateReceiver", "Status: " + status);

							if (status != null && status.contains("shut.")) {
								showNotification();
							}
							return status;
						} else {
							// handle bad response
							Log.e("DoorStatusService", "Bad HTTP Response: "
									+ statusLine.toString());
						}
					} catch (ClientProtocolException e) {
						// handle exception
						this.exception = e;
						Log.e("DoorStatusService", "ClientProtocolException: "
								+ e.getMessage());
					} catch (IOException e) {
						// handle exception
						this.exception = e;
						Log.e("DoorStatusService",
								"IOException: " + e.getMessage());
					}
					numTries++;
					try {
						Thread.sleep(200);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
					Log.i("DoorStatusService", "Trying again.");
				}
			} catch (Exception e) {
				this.exception = e;
				Log.e("DoorStatusService", "Exception: " + e.getMessage());
			}
			return status;
		}

		/**
		 * Shows the notification to call the {@link OpenDoorService}.
		 */
		private void showNotification() {
			mNotificationManager.notify(mId, notif);
		}

		protected void onPostExecute(String response) {
			if (this.exception != null) {
				Log.e("DoorController", this.exception.getMessage());
			}
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == true) {
			WifiInfo wi = wifi.getConnectionInfo();
			String ssid = wi.getSSID();
			if (ssid != null) {
				if (ssid.equals("APE") || ssid.equals("\"APE\"")) {
					// When was the last time this happened? - sometimes it
					// happens twice in a row!
					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(context);
					long now = System.currentTimeMillis();
					long lastConnTime = sp.getLong(
							PreferencesActivity.KEY_PREFERENCE_LAST_CONN_TIME,
							0);
					sp.edit()
							.putLong(
									PreferencesActivity.KEY_PREFERENCE_LAST_CONN_TIME,
									now).commit();
					if (now - lastConnTime > 2000) {
						Log.i("WiFiStateReceiver", "SSID: " + ssid);
						// clear previous status
						status = "";
						// update the status
						new CallDoorStatusTask().execute();
						// Build the notification
						NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
								context)
								.setSmallIcon(R.drawable.ic_launcher)
								.setContentTitle("Open Door")
								.setContentText("Tap to open your door.")
								.setAutoCancel(true)
								.setDefaults(
										Notification.DEFAULT_VIBRATE
												| Notification.DEFAULT_SOUND)
								.setTicker("Door in range...");
						// Creates an explicit intent for an Activity in
						// your application.
						Intent resultIntent = new Intent(context,
								OpenDoorService.class);
						PendingIntent pendingIntent = PendingIntent.getService(
								context, 0, resultIntent,
								PendingIntent.FLAG_ONE_SHOT);
						mBuilder.setContentIntent(pendingIntent);
						mBuilder.setWhen(System.currentTimeMillis());
						mNotificationManager = (NotificationManager) context
								.getSystemService(Context.NOTIFICATION_SERVICE);
						notif = mBuilder.build();
					}
				}
			} else {
				// ssid was null
			}
		}
	}
}
