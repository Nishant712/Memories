package com.example.newapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.io.*;

public class MainActivity extends FragmentActivity {

    private TextView mTextView;
    public static Context mContext;
    private String appEntryTime;
    public static String EXTRA_MESSAGE = "com.example.newapp.MESSAGE";
    //public int num = 0;
    private static final int REQUEST_PERMISSIONS = 1;
    private static String[] PERMISSIONS_LIST = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.VIBRATE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });

        verifyStoragePermissions(this);
        setContentView(R.layout.rect_activity_main);
        int randomNum = 0;
        int last;
        String firstLine, lastLine, temp = "";
        String[] arr;
        int[] numOccurrences = new int[5];

        try {
            firstLine = getFirstLine("pastSequence.txt");
            lastLine = getLastLine("pastSequence.txt");
            if(lastLine != null && !lastLine.isEmpty()) {
                arr = lastLine.split(",");
                for(int i = 0; i < 5; i++){
                    numOccurrences[i] = Integer.parseInt(arr[i]);
                }

            }

            if(firstLine != null && !firstLine.isEmpty()) {
                last = getPrevious();
                randomNum = getRandom(last);
                Log.d("If. Prompt number is ", Integer.toString(randomNum));
                firstLine = firstLine + "," + randomNum;
                numOccurrences[randomNum-1] = numOccurrences[randomNum] + 1;
                for(int j=0; j<5; j++) {
                    temp = temp + numOccurrences[j] + ",";
                }
                temp = temp.substring(0, temp.length() - 1);
                writeToOne(firstLine, temp);
            }
            else {
                randomNum = getRandomNumberInRange(1,5);
                Log.d("Else. Prompt number is ", Integer.toString(randomNum));
                firstLine = Integer.toString(randomNum);
                numOccurrences[randomNum-1] = numOccurrences[randomNum] + 1;
                for(int j=0; j<5; j++) {
                    temp = temp + numOccurrences[j] + ",";
                }
                temp = temp.substring(0, temp.length() - 1);
                writeToOne(firstLine, temp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView tv1 = (TextView)findViewById(R.id.second_screen);
        tv1.setText("Prompt "+randomNum);

        /*num = numRecordings();
        Log.d("Number of Recordings",String.format("number of recordings = %d", num));*/
        appEntryTime = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.US).format(new Date());
        /*long notificationDelay = (1000 * 60 * 60 * 5)/100; //5 hours, starts in milliseconds
        for(int i = 0; i <= 0;i++) {


                final int notificationNumber = i;
                setAlarm(notificationNumber, notificationDelay);


        }*/

    }

    public void setAlarm(final int notificationNumber, long notificationDelay) {
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override public void onReceive(Context context, Intent _ ) {
                MessagePromptNotification mpn = new MessagePromptNotification(mContext);
                mpn.createNotification(notificationNumber);
                context.unregisterReceiver( this ); // this == BroadcastReceiver, not Activity
            }
        };

        this.registerReceiver( receiver, new IntentFilter("com.blah.blah.somemessage") );

        PendingIntent pintent = PendingIntent.getBroadcast( this, 0, new Intent("com.blah.blah.somemessage"), 0 );
        AlarmManager manager = (AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));

        // set alarm to fire 5 sec (1000*5) from now (SystemClock.elapsedRealtime())
        manager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + notificationDelay, pintent );
    }

    public void startRecording(View view) {
        Intent intent;
        String message = appEntryTime;
        intent = new Intent(this, MessageRecord.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        this.finish();
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionRecord = ActivityCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED || permissionRecord != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LIST,
                    REQUEST_PERMISSIONS
            );
            SharedPreferences preferences = getSharedPreferences("prefName", MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("systemPermissionsGiven", false);
            edit.apply();
        } else {

            SharedPreferences settings = getSharedPreferences("prefName", MODE_PRIVATE);
            //Boolean needToDeleteAudio = settings.getBoolean("audioFilesDeleted", false);

            SharedPreferences preferences = getSharedPreferences("prefName", MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("systemPermissionsGiven", true);
            edit.apply();

            /*if(!needToDeleteAudio){
                deleteOldAudioFiles();
                edit.putBoolean("audioFilesDeleted", true);
                edit.apply();
            }*/
        }
    }

    public static int numRecordings() {
        int numberOfRecordings = 0;
        File Memories = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Memories");
        if(Memories.isDirectory()) {
            File[] foundFiles = Memories.listFiles(new FilenameFilter() {
                public boolean accept(File Memories, String name) {
                    return name.contains("_recording_");
                }
            });
            numberOfRecordings = foundFiles.length;
            return numberOfRecordings;
        }
        return 0;
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static String getFirstLine(String FileName) throws IOException {

        String strLine = null, tmp;
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Memories" + File.separator + FileName);
        if(f.exists() && !f.isDirectory()) {
            FileInputStream in = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Memories" + File.separator + FileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((tmp = br.readLine()) != null) {
                strLine = tmp;
                break;
            }
        }
        return strLine;
    }

    public static String getLastLine(String FileName) throws IOException {

        String strLine = null, tmp;

        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Memories" + File.separator + FileName);
        if(f.exists() && !f.isDirectory()) {
            FileInputStream in = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Memories" + File.separator + FileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while ((tmp = br.readLine()) != null) {
                strLine = tmp;
            }
        }
        return strLine;
    }

    public static void writeToOne(String firstLine, String secondLine) throws IOException {
        String FILENAME = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Memories" + File.separator + "pastSequence.txt";
        BufferedWriter bw = null;
        FileWriter fw = null;
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator + "Memories" + File.separator + "pastSequence.txt");
        if(f.exists() && !f.isDirectory()) {
            try {
                //open file for writing
                File file = new File("/sdcard/Memories/pastSequence.txt");
                FileOutputStream fileinput = new FileOutputStream(file, false);
                PrintStream printstream = new PrintStream(fileinput);
                printstream.print(firstLine+"\n");
                printstream.print(secondLine+"\n");
                fileinput.close();


            } catch (java.io.IOException e) {
                //if caught

            }
        }
        else {
            File dir = new File("/sdcard/Memories");
            try{
                if(dir.mkdir()) {
                    Log.d("Directory created: ", "Success");
                } else {
                    Log.d("Directory not created: ", "Failure");
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            try {
                //open file for writing
                File file = new File("/sdcard/Memories/pastSequence.txt");
                FileOutputStream fileinput = new FileOutputStream(file, false);
                PrintStream printstream = new PrintStream(fileinput);
                printstream.print(firstLine+"\n");
                printstream.print(secondLine+"\n");
                fileinput.close();


            } catch (java.io.IOException e) {
                //if caught

            }
        }

    }

    public static int getPrevious() throws IOException {
        String numbers = getFirstLine("pastSequence.txt");
        if(numbers == null)
            return 0;
        String[] arr = numbers.split(",");
        int last = Integer.parseInt(arr[arr.length-1]);
        return last;
    }

    public static int getRandom(int n) {
        ArrayList<Integer> numbers=new ArrayList<Integer>();
        for(int i=0; i <= 5; i++) {
            if(i == n)
                continue;
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        return numbers.get(0);
    }
}
