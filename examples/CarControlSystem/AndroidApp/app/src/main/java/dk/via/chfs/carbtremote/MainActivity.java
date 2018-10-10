package dk.via.chfs.carbtremote;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectionDevice connectionDevice = new ConnectionDevice();
        boolean success = connectionDevice.setupBluetooth();
        if (!success) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                //TODO enable buttons
                findViewById(R.id.gearSelectorSeekBar);
                // etc...

            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled", Toast.LENGTH_LONG).show();

            }
        }
    }
}
