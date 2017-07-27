package com.workspike.trakit;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.workspike.trakit.other_activities.HelpActivity;
import com.workspike.trakit.other_activities.TermsofserviceActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chamli Priyashan on 7/5/2016.
 */


public class LoginActivity extends Activity {
    private TextView info;
    private LoginButton loginButton;
    ImageView profilepicture;
    private ProfileTracker mProfileTracker;
    private static String KEY_SUCCESS = "success";
    public static String name;
    public static String id;
    public static String firstname;
    public static String lastname;
    public static String email;
    public static String gender;
    public static String birthday="01/31/1980";
    public static String phone_no="0776000697";
    public static String phone_imei="147852369745236";
    public static String profileImgUrl;

    public static final String KEY_FB_ID = "fb_id";
    private static String KEY_NAME = "f_name";
    private static String KEY_EMAIL= "email";
    private static String KEY_PROF_URL = "prof_url";
    private static String KEY_PHONE = "phone";
    private static String KEY_MYDEVICES = "my_devices";
    private static String KEY_FEVDEVICES = "fav_devices";
    private static String KEY_PHONE_IMEI = "phone_imei";

    private static String KEY_ERROR = "error";
    private CallbackManager callbackManager;

    boolean loggedIn = false;
    private static final String HOME_ACTIVITIES = "com.trakit.trakit.DashboardActivity";
    // Request Code
    private static final int HOME_ACTIVITIES_REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        System.out.println("goda");
        boolean loggedin_status = loadloginPreferences("loginBox");
        System.out.println(loggedin_status);
        if (loggedin_status == true) {
            System.out.println("start main activity");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AddmetoDB();
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        printhash();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loggedIn = isFacebookLoggedIn();



        setContentView(R.layout.login_activity);
        //info = (TextView) findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        LoginManager.getInstance().logOut();
        ImageButton closebutton= (ImageButton) findViewById(R.id.closeButton);
        closebutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
               finish();
            }
        });

        ImageButton helpbutton= (ImageButton) findViewById(R.id.helpButton);
        helpbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),HelpActivity.class);
                startActivity(i);
            }
        });

        ImageButton signupbutton= (ImageButton) findViewById(R.id.signupButton);
        signupbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);
            }
        });

        TextView termsofservice= (TextView) findViewById(R.id.termsofservice);
        termsofservice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),TermsofserviceActivity.class);
                startActivity(i);
            }
        });

        TextView workspikelink= (TextView) findViewById(R.id.workspikelink);
        workspikelink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String url = "http://www.workspike.com/trakit";
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setPackage("com.android.chrome");
                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    // Chrome is probably not installed
                    // Try with the default browser
                    i.setPackage(null);
                    startActivity(i);
                }
            }
        });


        Typeface font_style = Typeface.createFromAsset(getAssets(), "rio-glamour-personal-use.regular.otf");
        loginButton.setTypeface(font_style);
      // Imagebutton loginButtonimage = (LoginButton) findViewById(R.id.imageButton);
        //profilepicture = (ImageView) findViewById(R.id.profile_image);
        loginButton.setReadPermissions(Arrays.asList(
                "user_status", "public_profile", "email", "user_birthday", "user_friends","user_location"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accesstoken = loginResult.getAccessToken();
                //Profile profile = Profile.getCurrentProfile();
                String userID = loginResult.getAccessToken().getUserId();

                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile profile, Profile profile2) {
                            // profile2 is the new profile
                            Log.v("facebook - profile", profile2.getFirstName());
                            mProfileTracker.stopTracking();

                            String name = profile2.getName();
                            String id=profile2.getId();
                            String lastname=profile2.getLastName();
                            String firstname=profile2.getFirstName();

                            savePreferences("NAME",name);
                            savePreferences("ID",id);
                            savePreferences("FIRSTNAME",lastname);
                            savePreferences("LASTNAME",firstname);
                            System.out.println(name);
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.v("facebook - profile", profile.getFirstName());
                    name = profile.getName();
                    id=profile.getId();
                    lastname=profile.getLastName();
                    firstname=profile.getFirstName();

                    savePreferences("NAME",name);
                    savePreferences("ID",id);
                    savePreferences("FIRSTNAME",lastname);
                    savePreferences("LASTNAME",firstname);
                    System.out.println(name);
                }


                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                     email = object.getString("email");
                                    savePreferences("EMAIL",email);
                                    System.out.println(email);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                     gender = object.getString("gender");
                                    savePreferences("GENDER",gender);
                                    System.out.println(gender);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                     birthday = object.getString("birthday"); // 01/31/1980 format
                                    savePreferences("BIRTHDAY",birthday);
                                    System.out.println(birthday);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields","id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
                profileImgUrl = "https://graph.facebook.com/" + userID + "/picture?type=large";
                System.out.println("this ids from login activity"+profileImgUrl);
                savePreferences("PROFILEIMAGE", profileImgUrl);
                savePreferences("loginBox", true);


                AddmetoDB();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
              //  info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
              //  info.setText("Login attempt failed");
            }
        });

    }

    public boolean isFacebookLoggedIn() {
        System.out.println(AccessToken.getCurrentAccessToken());
        return AccessToken.getCurrentAccessToken() != null;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private boolean loadSavedPreferences(String x) {

        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        boolean checkBoxValue = preferences.getBoolean(x, false);//false is the default value...Dont worry
        System.out.println("login checkbox value=" + checkBoxValue);
        return checkBoxValue;

    }

    private boolean loadloginPreferences(String x) {
        SharedPreferences myPreference= PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkBoxValue = myPreference.getBoolean(x, false);
        System.out.println("login checkbox value=" + checkBoxValue);
        return checkBoxValue;

    }

    private void savePreferences(String key, boolean value) {
        SharedPreferences myPreference= PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = myPreference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void savePreferences(String key, String value) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();

    }

    private void printhash(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.workspike.trakit", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }



    private void AddmetoDB(){

        final String ADDmetoDB_URL = "http://www.lucidchamli.com/trakit/APIs/login_api/add_user_to_db.php";
        final String f_name = loadSavedstringPreferences("NAME", "DEFAULT");
        final String email2 = loadSavedstringPreferences("EMAIL", "DEFAULT");
        final String fb_id = loadSavedstringPreferences("ID", "DEFAULT");
        profileImgUrl= loadSavedimagePreferences();
        System.out.println(loadSavedstringPreferences("FIRSTNAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("LASTNAME", "DEFAULT"));
        System.out.println(loadSavedstringPreferences("NAME", "DEFAULT"));


        System.out.println("adding user TO MYSQL DATABASE");
        System.out.println("sTARTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT    DEBUGGGGGG....................");
        System.out.println(profileImgUrl);
        System.out.println(fb_id);
        System.out.println(email2);
        System.out.println(f_name);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADDmetoDB_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("xxxxx");
                        System.out.println(response.length());

                        String subresponse=response.substring(0,response.length());
                        System.out.println(subresponse.toString()+"xxxxx");
                        System.out.println(subresponse.toString()+"xxxxx");
                        System.out.println("sUCSSSSSSSSSS    DEBUGGGGGG....................");
                        Toast.makeText(LoginActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                       //uid
                params.put(KEY_FB_ID,fb_id);
                params.put(KEY_NAME, f_name);
                params.put(KEY_EMAIL, email2);
                params.put(KEY_PHONE,phone_no);
                params.put(KEY_PHONE_IMEI,phone_imei);
                params.put(KEY_PROF_URL, profileImgUrl);
                params.put(KEY_MYDEVICES, "default");
                params.put(KEY_FEVDEVICES, "default");
                System.out.println(params.toString());




                System.out.println("  DEBUGGGGGG....................");
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        System.out.println("finish adding user TO MYSQL DATABASE");
    }




    private String loadSavedimagePreferences() {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        // boolean checkBoxValue = preferences.getBoolean("CheckBox_Value", false);
        String string = preferences.getString("PROFILEIMAGE", "http://goo.gl/gEgYUd");
        System.out.println("returened image=" + "PROFILEIMAGE" + "value=" + string);
        return string;
    }

    private String loadSavedstringPreferences(String key, String dummy) {
        SharedPreferences preferences = getSharedPreferences("your_file_name", MODE_PRIVATE);
        String string = preferences.getString(key, dummy);
        System.out.println("returened string=" + key + "value=" + string);
        return string;
    }


}
