package com.example.bluetooth;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MostrarDatos extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mostrar_datos);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mostrar_datos, menu);
		return true;
	}

}
