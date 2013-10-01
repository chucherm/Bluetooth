package com.example.bluetooth;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Dispositivos extends Activity {

	BluetoothSocket socket;
	BluetoothDevice dispositivo;

	InputStream ins;
	OutputStream ons;
	String error;

	private ListView listadispositivos;
	ArrayList<String> recibido = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dispositivos);

		Bundle bundle = getIntent().getExtras();

		if (bundle != null) {
			recibido = bundle.getStringArrayList("dispositivos");

			// listadispositivos.setAdapter(recibido);
		}

		listadispositivos = (ListView) findViewById(R.id.listadispositivos);

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, recibido);
		listadispositivos.setAdapter(arrayAdapter);

	}

	void conectaDispositivo() {
		try {
			socket = dispositivo.createRfcommSocketToServiceRecord(UUID
					.fromString("00001101-0000-1000-8000-00805F9B34FB"));
			/*
			 * Method m = dispositivo.getClass().getMethod("createRfcommSocket",
			 * new Class[] { int.class }); socket = (BluetoothSocket)
			 * m.invoke(dispositivo, 1);
			 */
			socket.connect();
			ins = socket.getInputStream();
			ons = socket.getOutputStream();
			// estado = 3;
		} catch (Exception ex) {
			// estado = 4;
			error = ex.toString();
			System.out.println(error);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dispositivos, menu);
		return true;
	}

}
