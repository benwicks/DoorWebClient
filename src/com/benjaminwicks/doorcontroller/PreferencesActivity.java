package com.benjaminwicks.doorcontroller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PreferencesActivity extends Activity {
	public static final String KEY_PREFERENCE_IP_ADDRESS = "ipAddress";
	public static final String KEY_PREFERENCE_LAST_CONN_TIME = "lastConnTime";
	public static final String KEY_PREFERENCE_LAST_OPEN_TIME = "lastOpenTime";
	// private SharedPreferences sp;

	// private OnClickListener btnOpenDoorListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	// if (wifi.isWifiEnabled() == true) {
	// String ssid = wifi.getConnectionInfo().getSSID();
	// if (ssid.equals("APE")) {
	// Intent i = new Intent(PreferencesActivity.this,
	// OpenDoorService.class);
	// startService(i);
	// } else {
	// Log.e("DoorController", "Bad ssid: " + ssid);
	// Toast.makeText(getApplicationContext(),
	// "You're not on the correct network",
	// Toast.LENGTH_LONG).show();
	// }
	// } else {
	// Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// };
	//
	// private OnClickListener btnSetMessageListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	// if (wifi.isWifiEnabled() == true) {
	// String ssid = wifi.getConnectionInfo().getSSID();
	// if (ssid.equals("APE")) {
	// // Acquire message
	// DialogFragment df = new EnterMessageDialogFragment();
	// df.show(getFragmentManager(), "enterMessage");
	// } else {
	// Log.e("DoorController", "Bad ssid: " + ssid);
	// Toast.makeText(getApplicationContext(),
	// "You're not on the correct network",
	// Toast.LENGTH_LONG).show();
	// }
	// } else {
	// Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// };
	//
	// private OnClickListener btnRainbowListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	// if (wifi.isWifiEnabled() == true) {
	// String ssid = wifi.getConnectionInfo().getSSID();
	// if (ssid.equals("APE")) {
	// new CallRainbowServerTask().execute();
	// } else {
	// Log.e("DoorController", "Bad ssid: " + ssid);
	// Toast.makeText(getApplicationContext(),
	// "You're not on the correct network",
	// Toast.LENGTH_LONG).show();
	// }
	// } else {
	// Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// };
	//
	// private OnClickListener btnRainbowCycleListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	// if (wifi.isWifiEnabled() == true) {
	// String ssid = wifi.getConnectionInfo().getSSID();
	// if (ssid.equals("APE")) {
	// new CallRainbowCycleServerTask().execute();
	// } else {
	// Log.e("DoorController", "Bad ssid: " + ssid);
	// Toast.makeText(getApplicationContext(),
	// "You're not on the correct network",
	// Toast.LENGTH_LONG).show();
	// }
	// } else {
	// Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// };
	//
	// private OnClickListener btnClearColorsListener = new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	// if (wifi.isWifiEnabled() == true) {
	// String ssid = wifi.getConnectionInfo().getSSID();
	// if (ssid.equals("APE")) {
	// new CallClearColorsServerTask().execute();
	// } else {
	// Log.e("DoorController", "Bad ssid: " + ssid);
	// Toast.makeText(getApplicationContext(),
	// "You're not on the correct network",
	// Toast.LENGTH_LONG).show();
	// }
	// } else {
	// Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// };
	//
	// private OnClickListener btnToggleLookingListener = new OnClickListener()
	// {
	// @Override
	// public void onClick(View v) {
	// WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
	// if (wifi.isWifiEnabled() == true) {
	// String ssid = wifi.getConnectionInfo().getSSID();
	// if (ssid.equals("APE")) {
	// new CallToggleServerTask().execute();
	// } else {
	// Log.e("DoorController", "Bad ssid: " + ssid);
	// Toast.makeText(getApplicationContext(),
	// "You're not on the correct network",
	// Toast.LENGTH_LONG).show();
	// }
	// } else {
	// Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
	// Toast.LENGTH_LONG).show();
	// }
	// }
	// };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String action = getIntent().getAction();
		Log.i("PreferencesActivity", "Action: " + action);
		
		WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi.isWifiEnabled() == true) {
			String ssid = wifi.getConnectionInfo().getSSID();
			if (ssid.equals("APE") || ssid.equals("\"APE\"")) {
				Log.i("PreferencesActivity", "Opening Door...");
				Intent i = new Intent(PreferencesActivity.this,
						OpenDoorService.class);
				startService(i);
			} else {
				Log.e("NotificationManager ", "Wrong ssid: " + ssid);
				Toast.makeText(getApplicationContext(),
						"You're not on the correct network", Toast.LENGTH_LONG)
						.show();
			}
		} else {
			Log.e("PreferencesActivity", "Not on Wi-Fi");
			Toast.makeText(getApplicationContext(), "You must be on Wi-Fi",
					Toast.LENGTH_LONG).show();
		}
		finish();
		// setContentView(R.layout.activity_preferences);
		// findViewById(R.id.btn_openDoor).setOnClickListener(btnOpenDoorListener);
		/*
		 * findViewById(R.id.btn_setMessage).setOnClickListener(
		 * btnSetMessageListener);
		 * findViewById(R.id.btn_rainbow).setOnClickListener
		 * (btnRainbowListener);
		 * findViewById(R.id.btn_rainbowCycle).setOnClickListener(
		 * btnRainbowCycleListener);
		 * findViewById(R.id.btn_clearColors).setOnClickListener(
		 * btnClearColorsListener);
		 * findViewById(R.id.btn_toggleLooking).setOnClickListener(
		 * btnToggleLookingListener);
		 */
	}

	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // getMenuInflater().inflate(R.menu.activity_preferences, menu);
	// return true;
	// }

	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.menu_change_ip:
	// changeIPAddress();
	// break;
	// default:
	// break;
	// }
	// return super.onOptionsItemSelected(item);
	// }

	// private void changeIPAddress() {
	// DialogFragment df = new ChangeIPDialogFragment();
	// df.show(getFragmentManager(), "changeIP");
	// }

	// @Override
	// protected void onResume() {
	// super.onResume();
	// sp = PreferenceManager.getDefaultSharedPreferences(this);
	// }

	// private class ChangeIPDialogFragment extends DialogFragment {
	//
	// public ChangeIPDialogFragment() {
	//
	// }
	//
	// @Override
	// public Dialog onCreateDialog(Bundle savedInstanceState) {
	// // Use the Builder class for convenient dialog construction
	// AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	// final NumberPicker np = new NumberPicker(getApplicationContext());
	// np.setMinValue(0);
	// np.setMaxValue(255);
	// np.setValue(sp.getInt(KEY_PREFERENCE_IP_ADDRESS, 11));
	// builder.setView(np);
	// builder.setTitle(R.string.menu_change_ip)
	// .setPositiveButton(R.string.change,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// sp.edit()
	// .putInt(KEY_PREFERENCE_IP_ADDRESS,
	// np.getValue()).commit();
	// }
	// })
	// .setNegativeButton(R.string.cancel,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// // User cancelled the dialog
	// }
	// });
	//
	// // Create the AlertDialog object and return it
	// return builder.create();
	// }
	// }
	//
	// public class EnterMessageDialogFragment extends DialogFragment {
	// @Override
	// public Dialog onCreateDialog(Bundle savedInstanceState) {
	// // Use the Builder class for convenient dialog construction
	// AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	// final EditText et = new EditText(getApplicationContext());
	// et.setHint("Enter your message");
	// et.setTextColor(Color.parseColor("#000000"));
	// builder.setView(et);
	// builder.setTitle(R.string.enter_message)
	// .setPositiveButton(R.string.set,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// String message = et.getText().toString();
	// if (message.length() > 0) {
	// new CallMessageServerTask()
	// .execute(message);
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "You didn't enter anything.",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// })
	// .setNegativeButton(R.string.cancel,
	// new DialogInterface.OnClickListener() {
	// public void onClick(DialogInterface dialog,
	// int id) {
	// // User cancelled the dialog
	// }
	// });
	//
	// // Create the AlertDialog object and return it
	// return builder.create();
	// }
	// }
	//
	// private class CallMessageServerTask extends AsyncTask<String, Void,
	// String> {
	// private Exception exception;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// Toast.makeText(getApplicationContext(), "Setting message...",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// protected String doInBackground(String... params) {
	// String message = convertMessage(params[0]);
	//
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// String url = "http://10.3.3."
	// + sp.getInt(
	// PreferencesActivity.KEY_PREFERENCE_IP_ADDRESS,
	// 11) + ":2000/m/" + message + '/';
	// // String url = "http://10.3.3.10:2000/m/" + message + '/';
	// HttpGet httpGet = new HttpGet(url);
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// StatusLine statusLine = response.getStatusLine();
	// if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	// HttpEntity entity = response.getEntity();
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// entity.writeTo(out);
	// out.close();
	// String responseStr = out.toString();
	// return responseStr;
	// } else {
	// // handle bad response
	// }
	// } catch (ClientProtocolException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService ClientProtocolException",
	// e.getMessage());
	// } catch (IOException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService IOException", e.getMessage());
	// }
	// } catch (Exception e) {
	// this.exception = e;
	// Log.e("OpenDoorService Exception", e.getMessage());
	// return null;
	// }
	// return null;
	// }
	//
	// private String convertMessage(String string) {
	// string = string.replace(' ', '+').trim();
	// if (string.length() > 20) {
	// int pos = string.lastIndexOf('+', 20);
	// if (pos == 20) {
	// string = string.substring(0, 20)
	// + string.substring(21, string.length());
	// } else if (string.charAt(19) != '+' && string.charAt(20) != '+') {
	// while (string.charAt(19) != '+') {
	// int i = 1;
	// while (string.indexOf('+', i) < 19) {
	// i++;
	// }
	// string = string.substring(0, i) + '+'
	// + string.substring(i, string.length());
	// }
	// }
	// }
	// if (string.length() > 40) {
	// int pos = string.lastIndexOf('+', 40);
	// if (pos == 40) {
	// string = string.substring(0, 40)
	// + string.substring(41, string.length());
	// } else if (string.charAt(39) != '+' && string.charAt(40) != '+') {
	// while (string.charAt(39) != '+') {
	// int i = 1;
	// while (string.indexOf('+', i) < 39) {
	// i++;
	// }
	// string = string.substring(0, i) + '+'
	// + string.substring(i, string.length());
	// }
	// }
	// }
	// if (string.length() > 60) {
	// int pos = string.lastIndexOf('+', 60);
	// if (pos == 60) {
	// string = string.substring(0, 60)
	// + string.substring(61, string.length());
	// } else if (string.charAt(59) != '+' && string.charAt(60) != '+') {
	// while (string.charAt(59) != '+') {
	// int i = 1;
	// while (string.indexOf('+', i) < 59) {
	// i++;
	// }
	// string = string.substring(0, i) + '+'
	// + string.substring(i, string.length());
	// }
	// }
	// }
	// if (string.length() > 80) {
	// string = string.substring(0, 80);
	// }
	// Log.i("convertMessage", "String end: " + string);
	// return string;
	// }
	//
	// protected void onPostExecute(String response) {
	// if (this.exception == null) {
	// Toast.makeText(getApplicationContext(),
	// "Message set successfully.", Toast.LENGTH_SHORT).show();
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "Failed to set message :(", Toast.LENGTH_SHORT).show();
	// }
	// }
	// }
	//
	// private class CallRainbowServerTask extends AsyncTask<Void, Void, String>
	// {
	// private Exception exception;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// Toast.makeText(getApplicationContext(), "Starting rainbow...",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// protected String doInBackground(Void... params) {
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// String url = "http://10.3.3."
	// + sp.getInt(
	// PreferencesActivity.KEY_PREFERENCE_IP_ADDRESS,
	// 11) + ":2000/rainbow";
	// HttpGet httpGet = new HttpGet(url);
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// StatusLine statusLine = response.getStatusLine();
	// if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	// HttpEntity entity = response.getEntity();
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// entity.writeTo(out);
	// out.close();
	// String responseStr = out.toString();
	// return responseStr;
	// } else {
	// // handle bad response
	// }
	// } catch (ClientProtocolException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService ClientProtocolException",
	// e.getMessage());
	// } catch (IOException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService IOException", e.getMessage());
	// }
	// } catch (Exception e) {
	// this.exception = e;
	// Log.e("OpenDoorService Exception", e.getMessage());
	// return null;
	// }
	// return null;
	// }
	//
	// protected void onPostExecute(String response) {
	// if (this.exception == null) {
	// Toast.makeText(getApplicationContext(),
	// "Rainbowed successfully.", Toast.LENGTH_SHORT).show();
	// } else {
	// Toast.makeText(getApplicationContext(), "Failed to rainbow :(",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	// }
	//
	// private class CallRainbowCycleServerTask extends
	// AsyncTask<Void, Void, String> {
	// private Exception exception;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// Toast.makeText(getApplicationContext(), "Rainbow cycling...",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// protected String doInBackground(Void... params) {
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// String url = "http://10.3.3."
	// + sp.getInt(
	// PreferencesActivity.KEY_PREFERENCE_IP_ADDRESS,
	// 11) + ":2000/rainbowCycle";
	// HttpGet httpGet = new HttpGet(url);
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// StatusLine statusLine = response.getStatusLine();
	// if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	// HttpEntity entity = response.getEntity();
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// entity.writeTo(out);
	// out.close();
	// String responseStr = out.toString();
	// return responseStr;
	// } else {
	// // handle bad response
	// }
	// } catch (ClientProtocolException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService ClientProtocolException",
	// e.getMessage());
	// } catch (IOException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService IOException", e.getMessage());
	// }
	// } catch (Exception e) {
	// this.exception = e;
	// Log.e("OpenDoorService Exception", e.getMessage());
	// return null;
	// }
	// return null;
	// }
	//
	// protected void onPostExecute(String response) {
	// if (this.exception == null) {
	// Toast.makeText(getApplicationContext(),
	// "Rainbow cycled successfully.", Toast.LENGTH_SHORT)
	// .show();
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "Failed to rainbow cycle :(", Toast.LENGTH_SHORT)
	// .show();
	// }
	// }
	// }
	//
	// private class CallClearColorsServerTask extends
	// AsyncTask<Void, Void, String> {
	// private Exception exception;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// Toast.makeText(getApplicationContext(), "Clearing colors...",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// protected String doInBackground(Void... params) {
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// String url = "http://10.3.3."
	// + sp.getInt(
	// PreferencesActivity.KEY_PREFERENCE_IP_ADDRESS,
	// 11) + ":2000/clearColors";
	// HttpGet httpGet = new HttpGet(url);
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// StatusLine statusLine = response.getStatusLine();
	// if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	// HttpEntity entity = response.getEntity();
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// entity.writeTo(out);
	// out.close();
	// String responseStr = out.toString();
	// return responseStr;
	// } else {
	// // handle bad response
	// }
	// } catch (ClientProtocolException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService ClientProtocolException",
	// e.getMessage());
	// } catch (IOException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService IOException", e.getMessage());
	// }
	// } catch (Exception e) {
	// this.exception = e;
	// Log.e("OpenDoorService Exception", e.getMessage());
	// return null;
	// }
	// return null;
	// }
	//
	// protected void onPostExecute(String response) {
	// if (this.exception == null) {
	// Toast.makeText(getApplicationContext(),
	// "Colors cleared successfully.", Toast.LENGTH_SHORT)
	// .show();
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "Failed to clear colors :(", Toast.LENGTH_SHORT).show();
	// }
	// }
	// }
	//
	// private class CallToggleServerTask extends AsyncTask<Void, Void, String>
	// {
	// private Exception exception;
	//
	// @Override
	// protected void onPreExecute() {
	// super.onPreExecute();
	// Toast.makeText(getApplicationContext(), "Toggling Looking Mode...",
	// Toast.LENGTH_SHORT).show();
	// }
	//
	// protected String doInBackground(Void... params) {
	// try {
	// HttpClient httpClient = new DefaultHttpClient();
	// String url = "http://10.3.3."
	// + sp.getInt(
	// PreferencesActivity.KEY_PREFERENCE_IP_ADDRESS,
	// 11) + ":2000/toggle";
	// HttpGet httpGet = new HttpGet(url);
	// try {
	// HttpResponse response = httpClient.execute(httpGet);
	// StatusLine statusLine = response.getStatusLine();
	// if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
	// HttpEntity entity = response.getEntity();
	// ByteArrayOutputStream out = new ByteArrayOutputStream();
	// entity.writeTo(out);
	// out.close();
	// String responseStr = out.toString();
	// return responseStr;
	// } else {
	// // handle bad response
	// }
	// } catch (ClientProtocolException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService ClientProtocolException",
	// e.getMessage());
	// } catch (IOException e) {
	// // handle exception
	// this.exception = e;
	// Log.e("OpenDoorService IOException", e.getMessage());
	// }
	// } catch (Exception e) {
	// this.exception = e;
	// Log.e("OpenDoorService Exception", e.getMessage());
	// return null;
	// }
	// return null;
	// }
	//
	// protected void onPostExecute(String response) {
	// if (this.exception == null) {
	// Toast.makeText(getApplicationContext(),
	// "Toggled looking mode successfully.",
	// Toast.LENGTH_SHORT).show();
	// } else {
	// Toast.makeText(getApplicationContext(),
	// "Failed to toggle looking mode :(", Toast.LENGTH_SHORT)
	// .show();
	// }
	// }
	// }
}
