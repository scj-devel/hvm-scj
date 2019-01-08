package dk.via.chfs.carbtremote;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final String TAG = "CarBTRemote_DEBUG";

    ConnectedDevice.ConnectThread connectThread;
    ConnectedDevice connectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupHandlers();

        connectedDevice = new ConnectedDevice();
        setInstrumentsEnabled(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                bluetoothStart();

                //TODO only enable ignition switch if bluetooth is enabled
                findViewById(R.id.ignitionSwitch);
                // etc...

            } else {
                Toast.makeText(getApplicationContext(), "Bluetooth is not enabled", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean success = connectedDevice.setup();
        if (!success) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            bluetoothStart();
        }

    }

    @Override
    protected void onDestroy() {
        connectedDevice.disconnect();
        super.onDestroy();
    }

    protected void bluetoothStart() {
        if (connectedDevice.isConnected()) {
            return;
        }
        // start connection and get a handle
        Handler handler = getMessageHandler();
        connectThread = connectedDevice.connect(handler);
    }

    private void setInstrumentsEnabled(boolean state) {
        final Button acceleratorButton = findViewById(R.id.acceleratorButton);
        acceleratorButton.setEnabled(state);

        final Button brakeButton = findViewById(R.id.brakeButton);
        brakeButton.setEnabled(state);

        final SeekBar gearSelectorSeekBar = findViewById(R.id.gearSelectorSeekBar);
        final boolean touchState = !state;
        gearSelectorSeekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return touchState;
            }
        });
    }

    /*
        EVENT HANDLERS
     */
    private void setupHandlers()
    {
        // IGNITION
        final Switch ignitionSwitch = findViewById(R.id.ignitionSwitch);
        ignitionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "Ignition On");
                    if (!connectedDevice.isConnected()) {
                        bluetoothStart();
                    }
                    connectThread.start();
                    setInstrumentsEnabled(true);
                    connectedDevice.write(CarMessage.START.getValueAsArray());
                } else {
                    Log.d(TAG, "Ignition Off");
                    connectedDevice.write(CarMessage.STOP.getValueAsArray());
                    setInstrumentsEnabled(false);
                    connectedDevice.disconnect();
                }
            }
        });

        // ACCELERATOR PEDAL
        final Button acceleratorButton = findViewById(R.id.acceleratorButton);
        acceleratorButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Accelerator pedal pressed");
                connectedDevice.write(CarMessage.ACCELERATE.getValueAsArray());
            }
        });

        // BRAKE PEDAL
        final Button brakeButton = findViewById(R.id.brakeButton);
        brakeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "Brake pedal pressed");
                connectedDevice.write(CarMessage.BRAKE.getValueAsArray());
            }
        });

        // GEAR SELECTOR
        final SeekBar gearSelectorSeekBar = findViewById(R.id.gearSelectorSeekBar);
        gearSelectorSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d(TAG, "Gear selector changed to: " + progress);
                switch (progress) {
                    case 0:
                        Log.d(TAG, "Gear selector: PARK");
                        connectedDevice.write(CarMessage.GEAR_PARK.getValueAsArray());
                        break;
                    case 1:
                        Log.d(TAG, "Gear selector: REVERSE");
                        connectedDevice.write(CarMessage.GEAR_REVERSE.getValueAsArray());
                        break;
                    case 2:
                        Log.d(TAG, "Gear selector: NEUTRAL");
                        connectedDevice.write(CarMessage.GEAR_NEUTRAL.getValueAsArray());
                        break;
                    case 3:
                        Log.d(TAG, "Gear selector: DRIVE");
                        connectedDevice.write(CarMessage.GEAR_DRIVE.getValueAsArray());
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not relevant
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not relevant
            }
        });
    }

    private Handler getMessageHandler() {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == ConnectedDevice.MessageConstants.MESSAGE_READ) {
                    // TODO: display or act on messages from Bluetooth
                    String message = new String((byte[]) msg.obj);
                    Log.d(TAG, message.trim());
                    Toast.makeText(getApplicationContext(), "BT: " + message, Toast.LENGTH_LONG).show();
                }
                super.handleMessage(msg);
            }
        };
    }
}
