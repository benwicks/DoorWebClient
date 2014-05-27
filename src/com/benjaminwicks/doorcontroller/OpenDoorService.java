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

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

/**
 * This {@link Service} calls the code to open the door at the http://10.3.3.25/open URL.
 * @author ben
 *
 */
public class OpenDoorService extends Service {
	private SharedPreferences sp;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sp = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		new CallDoorOpenTask().execute();
		return START_NOT_STICKY;
	}

	class CallDoorOpenTask extends AsyncTask<Void, Void, String> {
		private Exception exception;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			Toast.makeText(getApplicationContext(), "Opening door...",
					Toast.LENGTH_SHORT).show();
		}

		protected String doInBackground(Void... params) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				String url = "http://10.3.3.25/open";
//				String url = "http://10.3.3."
//						+ sp.getInt(
//								PreferencesActivity.KEY_PREFERENCE_IP_ADDRESS,
//								11) + ":2000/openDoor";
				HttpGet httpGet = new HttpGet(url);
				try {
					HttpResponse response = httpClient.execute(httpGet);
					StatusLine statusLine = response.getStatusLine();
					if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
						HttpEntity entity = response.getEntity();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						entity.writeTo(out);
						out.close();
						String responseStr = out.toString();
						// TODO: do something with response
						return responseStr;
					} else {
						// handle bad response
					}
				} catch (ClientProtocolException e) {
					// handle exception
					this.exception = e;
					Log.e("OpenDoorService ClientProtocolException", e.getMessage());
				} catch (IOException e) {
					// handle exception
					this.exception = e;
					Log.e("OpenDoorService IOException", e.getMessage());
				}
			} catch (Exception e) {
				this.exception = e;
				Log.e("OpenDoorService Exception", e.getMessage());
				return null;
			}
			return null;
		}

		protected void onPostExecute(String response) {
			if (this.exception == null) {
				Toast.makeText(getApplicationContext(),
						"Door opened successfully.", Toast.LENGTH_SHORT).show();
				sp.edit()
						.putLong(
								PreferencesActivity.KEY_PREFERENCE_LAST_OPEN_TIME,
								System.currentTimeMillis()).commit();
			} else {
				// TODO: Problem...
				Toast.makeText(
						getApplicationContext(),
						"Failed to open door :("
								+ this.exception.getClass().toString(),
						Toast.LENGTH_SHORT).show();
			}
			// TODO: do something with the response?
			stopSelf();
		}
	}

}
