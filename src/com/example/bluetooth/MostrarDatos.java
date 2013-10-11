package com.example.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class MostrarDatos extends Activity {
	
	private static final String TAG = MostrarDatos.class.getSimpleName();
	private static final int REQUEST_DISCOVERY = 0x1;;
	private Handler _handler = new Handler();
	private BluetoothAdapter _bluetooth = BluetoothAdapter.getDefaultAdapter();
	
	private BluetoothSocket socket = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_datos);
		
	}
	
	/* after select, connect to device */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode != REQUEST_DISCOVERY) {
			return;
		}
		if (resultCode != RESULT_OK) {
			return;
		}
		final BluetoothDevice device = data.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
		new Thread() {
			public void run() {
				connect(device);
			};
		}.start();
	}
	
	
	protected void connect(BluetoothDevice device) {
		//BluetoothSocket socket = null;
		try {
			//Create a Socket connection: need the server's UUID number of registered
			socket = device.createRfcommSocketToServiceRecord(UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666"));
			
			socket.connect();
			Log.d("EF-BTBee", ">>Client connectted");
			
			InputStream inputStream = socket.getInputStream();														
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(new byte[] { (byte) 0xa0, 0, 7, 16, 0, 4, 0 });
			
			
		} catch (IOException e) {
			Log.e("EF-BTBee", "", e);
		} finally {
			if (socket != null) {
				try {
					Log.d("EF-BTBee", ">>Client Close");
					socket.close();	
					finish();
					return ;
				} catch (IOException e) {
					Log.e("EF-BTBee", "", e);
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_datos, menu);
		return true;
	}

}
