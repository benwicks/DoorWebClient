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
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DoorStatusService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new CallDoorStatusTask().execute();
		return START_NOT_STICKY;
	}

	class CallDoorStatusTask extends AsyncTask<Void, Void, String> {
		private Exception exception;

		protected String doInBackground(Void... params) {
			try {
				HttpClient httpClient = new DefaultHttpClient();
				String url = "http://10.3.3.25/stat";
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
						return responseStr;
					} else {
						// handle bad response
						Log.e("DoorStatusService", "Bad HTTP Response: "
								+ statusLine.toString());
					}
				} catch (ClientProtocolException e) {
					// handle exception
					this.exception = e;
					Log.e("DoorStatusService",
							"ClientProtocolException: " + e.getMessage());
				} catch (IOException e) {
					// handle exception
					this.exception = e;
					Log.e("DoorStatusService", "IOException: " + e.getMessage());
				}
			} catch (Exception e) {
				this.exception = e;
				Log.e("DoorStatusService", "Exception: " + e.getMessage());
				return null;
			}
			return null;
		}

		protected void onPostExecute(String response) {
			if (this.exception != null) {
				Toast.makeText(
						getApplicationContext(),
						"Failed to open door :("
								+ this.exception.getClass().toString(),
						Toast.LENGTH_SHORT).show();
				Log.e("DoorController", this.exception.getMessage());
			}
		}
	}

}
