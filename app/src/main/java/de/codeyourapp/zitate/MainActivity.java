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


    private SensorManager mSensorManager;

    // Festlegung der Sensoren
    private Sensor mSensorAccelerate;
    private Sensor mSensorLight;
    private Sensor mSensorMagnet;

    // TextViews zur Anzeige der Sensorenwerte.
    private TextView mTextSensorLight;
    private TextView mTextSensorAccelerate;
    private TextView mTextSensorMagnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Initialisierung der TextViews.
        mTextSensorLight = (TextView) findViewById(R.id.label_light);
        mTextSensorAccelerate = (TextView) findViewById(R.id.label_accelerate);
        mTextSensorMagnet = (TextView) findViewById(R.id.label_magnet);

        // Instanz des Sensormanagers
        mSensorManager = (SensorManager) getSystemService(
                Context.SENSOR_SERVICE);

        // Abrufen der Sensoren
        Log.i(LOG_TAG, "Überprüfung ob die Sensoren abrufbar sind ...");
        mSensorAccelerate = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorMagnet = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        // Fehlermeldung
        String sensor_error = getResources().getString(R.string.error_no_sensor);

        // Überprüfung ob Sensoren vorhanden sind
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
        // Funktion für Buttonklick Accelerometer
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
                finalView.setBackgroundColor(Color.WHITE);
            }
        }, 2000);


    }

    public void startMag(View view) {
        // Funktion für Buttonklick Magnetometer
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
                finalView.setBackgroundColor(Color.WHITE);
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


        // Wenn Sensor vorhanden, Listener registrieren
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        // Listener beenden wenn App pausiert
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Sensortyp bestimmen
        int sensorType = sensorEvent.sensor.getType();

        // currentValue zum Abrufen des aktuellen Sensorwertes
        float currentValue = sensorEvent.values[0];


        switch (sensorType) {
            // Case für Lichtensor
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
                        finalView.setBackgroundColor(Color.WHITE);
                    }
                }, 2000);

                // TextView für Sensor verändern
                mTextSensorLight.setText(getResources().getString(
                        R.string.label_light, String.format("%1.2f", currentValue)));
                break;
            // Case für Magnetometer
            case Sensor.TYPE_MAGNETIC_FIELD:
                // TextView für Sensor verändern
                mTextSensorMagnet.setText(getResources().getString(
                        R.string.label_magnet, "   Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2])));
                Log.i(LOG_TAG, "Der Magnetometerwert hat sich geändert!" +  " " + "Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2]));
                mSensorManager.unregisterListener(this, mSensorMagnet);
                break;
            case Sensor.TYPE_ACCELEROMETER:

                // Case für Accelerometer
                mTextSensorAccelerate.setText(getResources().getString(
                        R.string.label_accelerate, "   Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2])));
                Log.i(LOG_TAG, "Die Lage des Device hat sich geändert!" +  " " + "Z:   " + String.format("%1.2f", sensorEvent.values[0]) + "   X:   " + String.format("%1.2f",sensorEvent.values[1]) + "   Y:   " + String.format("%1.2f",sensorEvent.values[2]));

                mSensorManager.unregisterListener(this, mSensorAccelerate);
                break;

            default:
                // do nothing
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}