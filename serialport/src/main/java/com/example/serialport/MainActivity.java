package com.example.serialport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import serialportlibrary.Device;
import serialportlibrary.SerialPortFinder;
import serialportlibrary.SerialPortManager;
import serialportlibrary.listener.OnOpenSerialPortListener;
import serialportlibrary.listener.OnSerialPortDataListener;

public class MainActivity extends AppCompatActivity {
    private  final  String TAG ="serialport";
    private  final  String DRIVERS_PATH = "/proc/tty/drivers";
    private static final String DEFAULT_PATH_SP = "ttyMT1";


    TextView textView;
    EditText editText;
    private SerialPortManager mSerialPortManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       textView= findViewById(R.id.tv_serial_txt);
        editText =findViewById(R.id.et_input);
        mSerialPortManager = new SerialPortManager();
//        openSerial();
    }

    private void openSerial() {
        ArrayList<Device> devices = new SerialPortFinder().getDevices();
        Device mydevice=null ;
        for (Device device :
                devices) {
            if (device.getName().equals(DEFAULT_PATH_SP)) {
                mydevice = device;
                break;
            }
        }
        if(mydevice==null){
            return;
        }
        // 打开串口
        boolean openSerialPort = mSerialPortManager.setOnOpenSerialPortListener(new OnOpenSerialPortListener() {
                    @Override
                    public void onSuccess(File device) {
                        Log.d(TAG, "connect Success");
                    }

                    @Override
                    public void onFail(File device, Status status) {
                        Log.d(TAG, "connect failed");
                    }
                })
                .setOnSerialPortDataListener(new OnSerialPortDataListener() {
                    @Override
                    public void onDataReceived(byte[] bytes) { // 接收到串口数据的监听函数
                        Log.d(TAG, "SerialPortActivity onDataReceived [ byte[] ]: " + Arrays.toString(bytes));
                        Log.d(TAG, "SerialPortActivity onDataReceived [ String ]: " + new String(bytes));
                        final byte[] finalBytes = bytes;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,String.format("接收\n%s", new String(finalBytes,StandardCharsets.UTF_8)),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    /**
                     * 开启发生消息线程startSendThread - 调用 - 数据发送
                     * @param bytes 发送的数据
                     */
                    @Override
                    public void onDataSent(byte[] bytes) { // 发送串口数据的监听函数
                        Log.d(TAG, "SerialPortActivity onDataSent [ byte[] ]: " + Arrays.toString(bytes)); // onDataSent [ byte[] ]: [97] 【发送2】
                        Log.d(TAG, "SerialPortActivity onDataSent [ String ]: " + new String(bytes)); // onDataSent [ String ]: a
                        final byte[] finalBytes = bytes;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,String.format("发送\n%s", new String(finalBytes,StandardCharsets.UTF_8)),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .openSerialPort(mydevice.getFile(), 19200); // 串口设备文件，波特率

        Log.d(TAG, "SerialPortActivity onCreate: openSerialPort = " + openSerialPort);
    }

    //可以读取到串口列表
    public void getSerialList(View v) throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(DRIVERS_PATH));
        String readLine;
        while ((readLine = lineNumberReader.readLine()) != null) {
            String driverName = readLine.substring(0, 0x15).trim();
            String[] fields = readLine.split(" +");

            //Log.d(TAG, "SerialPortFinder getDrivers() driverName:" + driverName /*+ " readLine:" + readLine*/);
            if ((fields.length >= 5) && (fields[fields.length - 1].equals("serial"))) { // 判断第四个等不等于serial
                textView.setText(textView.getText().toString()+"\n"+driverName+"###"+fields[fields.length - 4]);
            }
        }
    }

    //可以读取到串口列表
    public void sendData(View v)  {
        mSerialPortManager.sendBytes(editText.getText().toString().getBytes(StandardCharsets.UTF_8));
    }

    public void openSerial(View v){
        openSerial();
    }

}

