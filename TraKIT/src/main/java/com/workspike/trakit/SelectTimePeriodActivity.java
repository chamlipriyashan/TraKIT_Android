package com.workspike.trakit;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class SelectTimePeriodActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnstartDatePicker, btnstartTimePicker,btnendDatePicker, btnendTimePicker;
    TextView txtstartDate, txtstartTime,txtendDate, txtendTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button button_statrt_time;
    private Button btntracklocation;
    private EditText editTextId;
    private TextView positdetails;
    DashboardActivity main = new DashboardActivity();
    private ProgressDialog loading;
    String Mystring = "";
    private String startdate;
    private String starttime;
    private String enddate;
    private String endtime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_time_period);
        positdetails = (TextView) findViewById(R.id.positdetails);
        btntracklocation = (Button) findViewById(R.id.btntracklocation);



        btnstartDatePicker=(Button)findViewById(R.id.btn_start_date);
        btnstartTimePicker=(Button)findViewById(R.id.btn_start_time);
        txtstartDate=(TextView)findViewById(R.id.in_date_start);
        txtstartTime=(TextView)findViewById(R.id.in_time_start);


        btnendDatePicker=(Button)findViewById(R.id.btn_end_date);
        btnendTimePicker=(Button)findViewById(R.id.btn_end_time);
        txtendDate=(TextView)findViewById(R.id.in_end_date);
        txtendTime=(TextView)findViewById(R.id.in_end_time);

        btnstartDatePicker.setOnClickListener(this);
        btnstartTimePicker.setOnClickListener(this);
        btnendDatePicker.setOnClickListener(this);
        btnendTimePicker.setOnClickListener(this);

        txtstartDate.setText("2016-11-22");
        txtendDate.setText("2016-11-23");
        txtstartTime.setText("6:15 am");
        txtendTime.setText("6:15 am");
        DashboardActivity.start_time=("2016-11-21"+"-6:00");
        DashboardActivity.end_time=("2016-11-23"+"-6:00");
        btntracklocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                getData();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    //*************************************************************CHAMLI STARTING.......
    private void getData() {

        loading = ProgressDialog.show(this, "Please wait...", "Fetching...", false, false);
        String startingtime="";
        String endingtime="";

        startingtime= DashboardActivity.start_time;
        endingtime= DashboardActivity.end_time;


        String tb_name ="d865904028323530";
        String url = (Config.DATA_URL2+ startingtime+"&ending="+endingtime+"&tb_name="+tb_name);
      //  String url = (Config.DATA_URL2+ startingtime+"&ending="+endingtime);
        System.out.println(url);
        System.out.println(url);
        System.out.println(url);
        System.out.println(url);
        System.out.println(url);
        System.out.println(url);
        System.out.println(url);

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SelectTimePeriodActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showJSON(String response){
        String vorp="";
        String temp="";
        String date="";
        String fuel = "";
        String lati="";
        String longi="";
        String alti = "";
        String satellite="";
        String speed="";
        String other = "";
        int ix=0;
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            if(result.length()>=2) {
                DashboardActivity.lati_array = new String[(result.length() - 2)];
                DashboardActivity.longi_array = new String[(result.length() - 2)];

                for(int i=0;i<(result.length()-2);i++) {
                    JSONObject collegeData = result.getJSONObject(i);
                    vorp = collegeData.getString(Config.KEY_VORP) + " ";
                    temp = collegeData.getString(Config.KEY_TEMP) + " ";
                    date = collegeData.getString(Config.KEY_DATE) + " ";
                    fuel = collegeData.getString(Config.KEY_FUEL) + " ";

                    lati = collegeData.getString(Config.KEY_LATI) + " ";
                    longi = collegeData.getString(Config.KEY_LONGI) + " ";
                    alti = collegeData.getString(Config.KEY_ALTI) + " ";
                    satellite = collegeData.getString(Config.KEY_SATELLITE) + " ";
                    speed = collegeData.getString(Config.KEY_SPEED) + " ";
                    other = collegeData.getString(Config.KEY_OTHER) + " ";
                    System.out.println(result.length() - 2);
                    DashboardActivity.lati_array[i] = lati;
                    DashboardActivity.longi_array[i] = longi;
                    ix = i;
                }

                System.out.println(ix + "lines are fetched ");
                positdetails.setText(ix + "lines are fetched ");
                System.out.println("VORP:\t" + vorp + "Temperature:\t" + temp + "\n  Date:\t" + date + "\n Fuel:\t" + fuel +
                        "\n  Latitude:\t" + lati + "\n  Longitude:\t" + longi +
                        "\n  Altitude:\t" + alti + "\n  Satellite Count:\t" + satellite + "\n  Speed:\t" + speed + "\n  Other:\t" + other);
                jsonObject = null;
                //jsonObject.remove(response);
                result = null;
            }else {
                System.out.println(ix + "lines are fetched ");

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btnstartDatePicker) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtstartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            startdate =(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                            DashboardActivity.start_time=(startdate+"-6:00");
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();

        }
        if (v == btnstartTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtstartTime.setText(hourOfDay + ":" + minute);
                            starttime = (hourOfDay + ":" + minute + ":00");
                            DashboardActivity.start_time=(startdate+"-"+starttime);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnendDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtendDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            enddate=(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                            DashboardActivity.end_time=(enddate+"-6:00");
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnendTimePicker) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            txtendTime.setText(hourOfDay + ":" + minute);
                            endtime =(hourOfDay + ":" + minute+":00");
                            DashboardActivity.end_time=(enddate+"-"+endtime );
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

    }
}
