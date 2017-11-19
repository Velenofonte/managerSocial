package com.example.davide.toiletteapp;

import android.app.Application;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.UiThread;
import android.util.Log;

import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import android.os.Handler;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.client.Url;
import io.socket.emitter.Emitter;
import io.socket.thread.EventThread;

/**
 * Created by ale on 19/11/17.
 */

//RETROFIT PER LE CHIAMATE
public class NotificationClass {

    private static URI uri = URI.create("http://5758d584.ngrok.io/");
    private static Socket socket = IO.socket(uri);

    public NotificationClass(final Handler socketHandler) {
        System.out.println("prova");
        new Thread(new Runnable() {

            public void run() {
                socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

                    @Override
                    public void call(Object... args) {
                        System.out.println("socket connesso "+" CONNESSO");
                    }
                }).on("isBusy", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject obj = (JSONObject)args[0];
                        Log.i("evento isBusy", obj.toString());
                        Message msg = socketHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("TYPE", "Busy");
                        bundle.putString("userName", "Pippo");
                        msg.setData(bundle);
                        socketHandler.sendMessage(msg);
                    }
                }).on("isFree", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        JSONObject obj = (JSONObject)args[0];
                        Log.i("evento isFree", obj.toString());
                        Message msg = socketHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("TYPE", "Free");
                        bundle.putString("userName", "Pippo");
                        msg.setData(bundle);
                        socketHandler.sendMessage(msg);
                    }
                }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        Log.i("socket disconnesso", "DISCONESSO");

                    }

                });
                socket.connect();
            }
        }).start();


    }

    public static Socket getSocket() {
        return socket;
    }

}
