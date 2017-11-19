package com.example.davide.toiletteapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Observer{

    private Button mButtonEnter;
    private Button mButtonExit;
    private EditText mName;
    private TextView mResult;
    private Timer timer;
    private NotificationClass socket;
    private static StatusClass statusClass = new StatusClass();
    private static Handler socketHandler;
    private NotificationClass notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.initUI();
        this.initSocket();

    }

    public void initUI() {
        Button mButtonLock = (Button)findViewById(R.id.lockbutton);
        mButtonEnter = (Button)findViewById(R.id.enter_button);
        mButtonExit = (Button) findViewById(R.id.exit_button);
        mName = (EditText) findViewById(R.id.name);
        mResult = (TextView) findViewById(R.id.result);
        mName.setHint("Inserisci il tuo nome");

        mButtonEnter.setEnabled(false);
        mButtonExit.setEnabled(false);
//
//        MyTimer tryGet = new MyTimer();
//        timer = new Timer();
//        timer.schedule(tryGet, 3000,1500);


        mButtonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TryThread.setStatusEntry(mName.getText().toString());

            }
        });

        mButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TryThread.setStatusExit(mName.getText().toString());

                mResult.setText("Nessuno");
            }
        });

        mButtonLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Inserisci il nome", Toast.LENGTH_SHORT).show();
                else {
                    mName.setEnabled(false);
                    //statusClass.addObserver(MainActivity.this);
                    //socket = new NotificationClass();
                }
            }
        });


    }

    private void setFree(String userName) {
        Toast.makeText(getApplicationContext(), "free", Toast.LENGTH_SHORT).show();
        setButtonFree();
    }

    private void setBusy(String userName) {
        Toast.makeText(getApplicationContext(), "Busy", Toast.LENGTH_SHORT).show();
        setButtonBusy(userName);
    }
    public void initSocket() {
         socketHandler = new Handler() {
             @Override
             public void handleMessage(Message msg) {
                 Bundle bundle = msg.getData();
                 try {
                     switch (bundle.getString("TYPE")) {
                         case "Free":
                             setFree(bundle.getString("userName"));
                             break;
                         case "Busy":
                             setBusy(bundle.getString("userName"));
                             break;
                     }
                 } catch (NullPointerException e) {
                     Log.e("nullPointer", e.getMessage());
                 }

             }


         };
        notification = new NotificationClass(socketHandler);
    }

    public static StatusClass getStatusClass() {
        return statusClass;
    }

    public void setButtonFree() {
        this.setmResult("Nessuno");
        this.mButtonEnter.setEnabled(true);
        this.mButtonExit.setEnabled(false);
    }

    public void setButtonBusy(String name) {
        this.setmResult(name);
        this.mButtonEnter.setEnabled(false);
        this.mButtonExit.setEnabled(true);
    }

    public void setmResult(String name) {
        mResult.setText(name);
    }

    @Override
    public void update(Observable o, Object arg) {
//        String result = arg.toString();
//        TextView mResultUpdate = (TextView) findViewById(R.id.result);
//        Button mButtonEnterUpdate = (Button)findViewById(R.id.enter_button);
//        Button mButtonExitUpdate = (Button) findViewById(R.id.exit_button);
//        EditText mNameUpdate = (EditText) findViewById(R.id.name);
//        mResultUpdate.setText(result);
//       if(mResultUpdate.getText().equals(mNameUpdate.getText().toString())) {
//           mButtonEnterUpdate.setEnabled(false);
//           mButtonExitUpdate.setEnabled(true);
//        }else{
//           mButtonEnterUpdate.setEnabled(true);
//           mButtonExitUpdate.setEnabled(false);
//        }
    }

//    @Override
//    protected void onStop(){
//        statusClass.deleteObserver(this);
//        timer.cancel();
//    }

}

