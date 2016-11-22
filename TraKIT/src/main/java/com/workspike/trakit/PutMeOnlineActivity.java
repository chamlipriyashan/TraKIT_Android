package com.workspike.trakit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PutMeOnlineActivity extends AppCompatActivity {
    private Button btn_putmeonline;
    private TextView online_text;
    private TextView status;
    boolean x,y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_put_me_online);
        btn_putmeonline = (Button) findViewById(R.id.btn_putmeonline);
        online_text=(TextView) findViewById(R.id.online_text);
         status=(TextView) findViewById(R.id.status);
        online_text.setText("By using this Option,All your location details will be shown to Public,So that all your friends can see you realtime on their map.");


        x=loadSavedPreferences("put_me_online");
        if(x==false) {
            status.setText("OFFLINE");
            btn_putmeonline.setText("Put Me Online");
        }else if(x=true) {
            btn_putmeonline.setText("Put Me Offline");
            status.setText("ONLINE");
        }


            btn_putmeonline.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                   y = loadSavedPreferences("put_me_online");
                    if(y==false) {
                        startService(new Intent(PutMeOnlineActivity.this, LocationService.class));
                        savePreferences("put_me_online", true);
                        System.out.println("Im ONLINE");
                        status.setText("ONLINE");
                        Toast toast= Toast.makeText(getApplicationContext(),
                                "Serrvice Started", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        btn_putmeonline.setText("Put Me Offline");
                        //status.setTextColor(125);

                    }else if(y==true){
                        savePreferences("put_me_online", false);
                        stopService(new Intent(PutMeOnlineActivity.this, LocationService.class));
                        System.out.println("Im offline");
                        Toast toast= Toast.makeText(getApplicationContext(),
                                "Serrvice Closed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast.show();
                        btn_putmeonline.setText("Put Me Online");
                        status.setText("OFFLINE");
                        //status.setTextColor(200);
                    }
                }
            });






    }




    private void savePreferences(String key, boolean value) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }


    private boolean loadSavedPreferences(String x) {

        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        boolean checkBoxValue = preferences.getBoolean(x, false);//false is the default value...Dont worry
        System.out.println("login checkbox value=" + checkBoxValue);
        return checkBoxValue;

    }











}
