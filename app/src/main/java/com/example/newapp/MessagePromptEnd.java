package com.example.newapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MessagePromptEnd extends FragmentActivity {

    private TextView mTextView;
    public FirstQuestionAdapter padapter = new FirstQuestionAdapter(getSupportFragmentManager());
    private String recording_details = "";
    public static String EXTRA_MESSAGE = "com.example.newapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_prompt_end);
        /*final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });*/

        ViewPager viewpager = (ViewPager) findViewById(R.id.pager);

        Intent intent = getIntent();
        recording_details = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        Log.d("Recording Details",recording_details);

        viewpager.setAdapter(padapter);
    }

    public void secondQuestionResponse1(View view) {
        Intent intent;
        String message = recording_details + ",1";
        intent = new Intent(this, MessagePromptEndTwo.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        this.finish();
    }

    public void secondQuestionResponse2(View view) {
        Intent intent;
        String message = recording_details + ",2";
        intent = new Intent(this, MessagePromptEndTwo.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        this.finish();
    }

    public void secondQuestionResponse3(View view) {
        Intent intent;
        String message = recording_details + ",3";
        intent = new Intent(this, MessagePromptEndTwo.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        this.finish();
    }

    public void secondQuestionResponse4(View view) {
        Intent intent;
        String message = recording_details + ",4";
        intent = new Intent(this, MessagePromptEndTwo.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        this.finish();
    }
}
