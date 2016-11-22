package com.workspike.trakit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddDeviceActivity extends AppCompatActivity {
    //    trackit.host22.com/add_device.php?email=xpanuwa@gmail.com&imei=123456789123456&serialnumber=1524555&purchasedate=121212121212&simnumber=0776000697&devicename=benzzz
    private static String KEY_SUCCESS = "success";
    public static final String KEY_FB_ID = "fbid";
    private static String KEY_IMEI = "imei";
    private static String KEY_SERIALNUMBER= "serialnumber";
    private static String KEY_SIM_NO = "simnumber";
    private static String KEY_ERROR = "error";
    private static String KEY_NAME = "devicename";
    private static String KEY_PASSWORD = "devicepassword";
    private static String KEY_COLOUR = "devicecolor";
    EditText inputdeviceIMEI_No;
    EditText inputdeviceSIM_No;
    EditText inputdeviceName;


    EditText inputDeviceSN;
    EditText inputDevicecolor;
    EditText inputdevicepassword;
    Button btnSaveDevicetoDB;
    Button btnresetfields;
    TextView devicesaveErrorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        inputdeviceIMEI_No = (EditText) findViewById(R.id.et_device_imei);
        inputdeviceSIM_No = (EditText) findViewById(R.id.et_sim_number);
        inputdevicepassword = (EditText) findViewById(R.id.et_passowrd);
        inputDevicecolor= (EditText) findViewById(R.id.et_device_colour);
        inputdeviceName = (EditText) findViewById(R.id.et_device_name);
        inputDeviceSN = (EditText) findViewById(R.id.et_device_sn);
        devicesaveErrorMsg=(TextView) findViewById(R.id.tv_devicesaveErrorMsg);
        btnSaveDevicetoDB = (Button) findViewById(R.id.btn_add_device);
        btnresetfields = (Button) findViewById(R.id.btn_reset);



        btnresetfields.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                inputdeviceIMEI_No.setText("");
                inputdeviceSIM_No.setText("");
                inputdeviceName.setText("");
                inputDeviceSN.setText("");
                devicesaveErrorMsg.setText("Reset OK");
            }
        });

        btnSaveDevicetoDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ((!inputdeviceIMEI_No.getText().toString().equals("")) && (!inputdevicepassword.getText().toString().equals(""))  && (!inputdeviceSIM_No.getText().toString().equals("")) && (!inputdeviceName.getText().toString().equals("")) && (!inputDeviceSN.getText().toString().equals("")) ) {
                    if (inputdeviceIMEI_No.getText().toString().length() == 15) {
                        System.out.println("adding the deviceeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
                        AddDevice();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                "IMEI number should be 15 characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "One or more fields are empty", Toast.LENGTH_SHORT).show();
                }

                ///*******************************
            }
        });
    }
    private void AddDevice(){
        final String username = "chamlipriyashan";
        final String password = "12345678";
        // final String email = "xpanuwaa@gmail.com";
        final String devicepassword = inputdevicepassword.getText().toString();
        final String deviceimei = inputdeviceIMEI_No.getText().toString();
        final String devicesimno = inputdeviceSIM_No.getText().toString();
        final String devicename = inputdeviceName.getText().toString();
        final String serialnumber = inputDeviceSN.getText().toString();
        final String devicecolor =inputDevicecolor.getText().toString();
        final String ADDDEVICE_URL = "http://www.workspike.com/trakit/APIs/add_device_api/add_device.php";
       final String fb_id=loadSavedstringPreferences("ID", "DEFAULT");
        System.out.println(fb_id);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADDDEVICE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        String subresponse=response.substring(0,30);
                        System.out.println(subresponse);
                        Toast.makeText(AddDeviceActivity.this,subresponse,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddDeviceActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();


                //params.put(KEY_USERNAME,username);
                //  params.put(KEY_PASSWORD,password);
                params.put(KEY_FB_ID,fb_id);
                params.put(KEY_IMEI, deviceimei);
                params.put(KEY_SERIALNUMBER, serialnumber);
                params.put(KEY_SIM_NO, devicesimno);
                params.put(KEY_PASSWORD,devicepassword);
                params.put(KEY_COLOUR, devicecolor);
                params.put(KEY_NAME, devicename);
                System.out.println(params);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }





    private String loadSavedstringPreferences(String key, String dummy) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        String string = preferences.getString(key, dummy);
        System.out.println("returened string=" + key + "value=" + string);
        return string;
    }


}
