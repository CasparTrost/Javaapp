package de.codeyourapp.zitate;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity
        implements SensorEventListener {

    private View view;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // System sensor manager instance.
    private SensorManager mSensorManager;

    // Proximity and light sensors, as retrieved from the sensor manager.
    private Sensor mSensorAccelerate;
    private Sensor mSensorLight;
    private Sensor mSensorMagnet;

    // TextViews to display current sensor values.
    private TextView mTextSensorLight;
    private TextView mTextSensorAccelerate;
    private TextView mTextSensorMagnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialize all view variables.
        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextSensorAccelerate = (TextView) findViewById(R.id.label_accelerate);
        mTextSensorMagnet = (TextView) findViewById(R.id.label_magnet);

        // Get an instance of the sensor manager.
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        // Get light and proximity sensors from the sensor manager.
        // The getDefaultSensor() method returns null if the sensor
        // is not available on the device.
        Log.i(LOG_TAG, "Überprüfung ob die Sensoren abrufbar sind ...");
        mSensorAccelerate = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorMagnet = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Get the error message from string resources.
        String sensor_error = getResources().getString(R.string.error_no_sensor);

        // If either mSensorLight or mSensorProximity are null, those sensors
        // are not available in the device.  Set the text to the error message
        if (mSensorLight == null) {
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorAccelerate == null) {
            mTextSensorAccelerate.setText(sensor_error);
        }
        if (mSensorMagnet == null) {
            mTextSensorMagnet.setText(sensor_error);
        }
    }


    public void startAcc(View view) {
        // Do something in response to button click
        new AlertDialog.Builder(this)
                .setTitle("Accelerometerbutton")
                .setMessage("Accelerometer wurde abgelesen")
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();


        Log.i(LOG_TAG, "Button zum Accelerometerstart wurde gedrückt ...");
        mSensorManager.registerListener(this, mSensorAccelerate,
                SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(LOG_TAG, "Text wird für 2000ms geflashed ...");
        view = findViewById(R.id.label_accelerate);
        view.setBackgroundColor(Color.GREEN);
        final Handler handler = new Handler();
        View finalView = view;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finalView.setBackgroundColor(Color.WHITE); //set the color to black
            }
        }, 2000);


    }

    public void startMag(View view) {
        // Do something in response to button click
        new AlertDialog.Builder(this)
                .setTitle("Magnetometerbutton")
                .setMessage("Mamgnetometer wurde abgelesen")
                .setPositiveButton("OK", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        Log.i(LOG_TAG, "Button zum Magnetometerstart wurde gedrückt ...");
        mSensorManager.registerListener(this, mSensorMagnet,
                SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(LOG_TAG, "Text wird für 2000ms geflashed ...");
        view = findViewById(R.id.label_magnet);
        view.setBackgroundColor(Color.GREEN);
        final Handler handler = new Handler();
        View finalView = view;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finalView.setBackgroundColor(Color.WHITE); //set the color to black
            }
        }, 2000);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "Initiale Sensordaten werden angelegt ...");
        mTextSensorLight.setText(getResources().getString(
                R.string.label_light, String.format("%1.2f", 0.00)));

        mTextSensorAccelerate.setText(getResources().getString(
                R.string.label_accelerate,  "----"));

        mTextSensorMagnet.setText(getResources().getString(
                R.string.label_magnet,  "----"));



        // Listeners for the sensors are registered in this callback and
        // can be unregistered in onPause().
        //
        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL)

        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is paused.
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // The sensor type (as defined in the Sensor class).
        int sensorType = sensorEvent.sensor.getType();
        // The new data value of the sensor.  Both the light and proximity
        // sensors report one value at a time, which is always the first
        // element in the values array.
        float currentValue = sensorEvent.values[0];


        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT:
                Log.i(LOG_TAG, "Text wird für 2000ms geflashed ...");
                Log.i(LOG_TAG, "Der Lichtsensorwert hat sich geändert! Der Wert ist jetzt bei: " + String.format("%1.2f", currentValue));
                view = findViewById(R.id.label_light);
                view.setBackgroundColor(Color.GREEN);
                final Handler handler = new Handler();
                View finalView = view;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finalView.setBackgroundColor(Color.WHITE); //set the color to black
                    }
                }, 2000);

                // Set the light sensor text view to the light sensor string
                // from the resources, with the placeholder filled in.
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, String.format("%1.2f", currentValue)));
                break;

            case Sensor.TYPE_MAGNETIC_FIELD:

                mTextSensorMagnet.setText(getResources().getString(
                        R.string.label_magnet, "   Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2])));
                Log.i(LOG_TAG, "Der Magnetometerwert hat sich geändert!" +  " " + "Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2]));
                mSensorManager.unregisterListener(this, mSensorMagnet);
                break;
            case Sensor.TYPE_ACCELEROMETER:

                // Set the proximity sensor text view to the light sensor
                // string from the resources, with the placeholder filled in.
                mTextSensorAccelerate.setText(getResources().getString(
                        R.string.label_accelerate, "   Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2])));
                Log.i(LOG_TAG, "Die Lage des Device hat sich geändert!" +  " " + "Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2]));

                mSensorManager.unregisterListener(this, mSensorAccelerate);
                break;

            default:
                // do nothing
        }

    }

    /**
     * Abstract method in SensorEventListener.  It must be implemented, but is
     * unused in this app.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}