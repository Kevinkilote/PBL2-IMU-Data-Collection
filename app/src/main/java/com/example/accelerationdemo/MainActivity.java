package com.example.accelerationdemo;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ToggleButton;
import android.widget.CompoundButton;



public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    TextView XValue, YValue, ZValue;
    ToggleButton simpleToggleButton1;
    private AccDataChunk mChunk;		// Accelerometer data pool Class reference

    private AccDataWriter mWriter;		// Accelerometer data writer Class reference

    private boolean isRecording;

    // Constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XValue = (TextView)findViewById(R.id.XValue);
        YValue = (TextView)findViewById(R.id.YValue);
        ZValue = (TextView)findViewById(R.id.ZValue);

        simpleToggleButton1 = (ToggleButton) findViewById(R.id.toggleButton);

        // Initialize data storage
        mChunk = new AccDataChunk(100000);
        mChunk.numberOfRecords = 0;
        mWriter = new AccDataWriter(this);

        // Initialize recording status
        isRecording = false;

        //simpleToggleButton1 = (ToggleButton) findViewById(R.id.toggleButton2);
        simpleToggleButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    if (!isRecording) {
                        mChunk.numberOfRecords = 0;
                        isRecording = true;
                        Toast.makeText(MainActivity.this, "Recording Started", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // The toggle is disabled
                    if (isRecording)
                    {
                        isRecording = false;
                        Toast.makeText(MainActivity.this, "Recording Stopped", Toast.LENGTH_SHORT).show();
                        // Raw acceleration data
                        //mWriter.setAttributes(mAtt);
                        if (mChunk.numberOfRecords > 0) {
                            mWriter.setAccelerationData(mChunk);
                            mWriter.Write();
                        }
                        Toast.makeText(MainActivity.this, "Recording Stopped2 " + mChunk.numberOfRecords, Toast.LENGTH_SHORT).show();
                        mChunk.numberOfRecords =0;
                    }
                }
            }
        });



        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, sensor, 5000);
        int min_delay = sensor.getMinDelay();
        Toast.makeText(MainActivity.this, "Min Delay" + min_delay, Toast.LENGTH_SHORT).show();
    }

    // Destructor
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    // Initialize Event listener
    private SensorEventListener listener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            // Get data only if recording
            if (!isRecording)
                return;

            // Process only accelerometer data
            if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
                return;

            // Get raw acceleration and time stamp
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long mSensorTimeStamp = event.timestamp;

            int count = mChunk.numberOfRecords;
            mChunk.acc_x[count] = x;
            mChunk.acc_y[count] = y;
            mChunk.acc_z[count] = z;

            XValue.setText("xValue: " + event.values[0]);
            YValue.setText("yValue: " + event.values[1]);
            ZValue.setText("zValue: " + event.values[2]);


            mChunk.time_stamp[count] = mSensorTimeStamp;
            mChunk.numberOfRecords = mChunk.numberOfRecords+1;

        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };



}