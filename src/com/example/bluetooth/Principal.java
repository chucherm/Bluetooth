package com.example.bluetooth;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.Toast;

public class Principal extends Activity implements OnClickListener {

	private Button bucardispositivo, obtenerinformacion, salir;
	private BluetoothAdapter mBluetoothAdapter;
	private Switch mySwitch;

	private ArrayList<String> mArrayAdapter = new ArrayList<String>();

	private static final int ENCENDER = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Distpositivo no soportado",
					Toast.LENGTH_SHORT).show();
		}

		bucardispositivo = (Button) findViewById(R.id.buscardisb);
		obtenerinformacion = (Button) findViewById(R.id.obtenerdirnb);
		salir = (Button) findViewById(R.id.salirb);

		bucardispositivo.setOnClickListener(this);
		obtenerinformacion.setOnClickListener(this);
		salir.setOnClickListener(this);

		encenderBluetooth();

		registerReceiver(ActionFoundReceiver, new IntentFilter(
				BluetoothDevice.ACTION_FOUND));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}

	public void encenderBluetooth() {
		mySwitch = (Switch) findViewById(R.id.switch1);

		mySwitch.setChecked(false);

		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {

				if (isChecked) {

					if (!mBluetoothAdapter.isEnabled()) {
						Intent enableBtIntent = new Intent(
								BluetoothAdapter.ACTION_REQUEST_ENABLE);
						startActivityForResult(enableBtIntent, ENCENDER);
						Toast.makeText(Principal.this,
								"El Bluetooth esta Activado",
								Toast.LENGTH_SHORT).show();
					}

				} else if (!isChecked) {

					if (mBluetoothAdapter.isEnabled()) {
						mBluetoothAdapter.disable();
						Toast.makeText(Principal.this,
								"El Bluetooth esta Desactivado",
								Toast.LENGTH_SHORT).show();
					}

				}

			}
		});
	}

	@Override
	public void onClick(View v) {

		if (bucardispositivo.isPressed()) {

			mBluetoothAdapter.startDiscovery();

			Intent inte = new Intent(this, Dispositivos.class);
			inte.putExtra("dispositivos", mArrayAdapter);
			startActivity(inte);

		} else if (obtenerinformacion.isPressed()) {

		} else if (salir.isPressed()) {

			mBluetoothAdapter.disable();
			finish();
		}
	}

	private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				mArrayAdapter
						.add(device.getName() + "\n" + device.getAddress());
			}
		}
	};

}
