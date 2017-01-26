package com.nomensvyat.offlinechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nomensvyat.offlinechat.model.entities.Room;
import com.nomensvyat.offlinechat.ui.chat_messaging.ChatMessagingActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(
                ChatMessagingActivity.createStartIntent(this, Room.createRoom(1)));

        finish();
    }
}
