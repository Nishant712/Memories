package com.example.newapp;

import android.content.Intent;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

public class MessagePromptEndTwo extends FragmentActivity {

    private TextView mTextView;
    private String recording_details = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_prompt_end_two);
        /*final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });*/

        Intent intent = getIntent();
        recording_details = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d("Recording Details",recording_details);

        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);
        SecondQuestionAdapter padapter = new SecondQuestionAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
    }

    public void endAppResponse1(View view) {
        Intent intent;
        String message = recording_details + ",1";
        try {
            //open file for writing
            File file = new File("/sdcard/Memories/response_recordings.txt");
            FileOutputStream fileinput = new FileOutputStream(file, true);
            PrintStream printstream = new PrintStream(fileinput);
            printstream.print(message+"\n");
            fileinput.close();


        } catch (java.io.IOException e) {
            //if caught

        }
        Log.d("Recording Details",message);
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void endAppResponse2(View view) {
        Intent intent;
        String message = recording_details + ",2";
        try {
            File file = new File("/sdcard/Memories/response_recordings.txt");
            FileOutputStream fileinput = new FileOutputStream(file, true);
            PrintStream printstream = new PrintStream(fileinput);
            printstream.print(message+"\n");
            fileinput.close();


        } catch (java.io.IOException e) {
            //if caught

        }
        Log.d("Recording Details",message);
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void endAppResponse3(View view) {
        Intent intent;
        String message = recording_details + ",3";
        try {
            File file = new File("/sdcard/Memories/response_recordings.txt");
            FileOutputStream fileinput = new FileOutputStream(file, true);
            PrintStream printstream = new PrintStream(fileinput);
            printstream.print(message+"\n");
            fileinput.close();


        } catch (java.io.IOException e) {
            //if caught

        }
        Log.d("Recording Details",message);
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void endAppResponse4(View view) {
        Intent intent;
        String message = recording_details + ",4";
        try {
            File file = new File("/sdcard/Memories/response_recordings.txt");
            FileOutputStream fileinput = new FileOutputStream(file, true);
            PrintStream printstream = new PrintStream(fileinput);
            printstream.print(message+"\n");
            fileinput.close();


        } catch (java.io.IOException e) {
            //if caught

        }
        Log.d("Recording Details",message);
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
