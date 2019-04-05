package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.gfranks.minimal.notification.GFMinimalNotification;
import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivitySignupScreenBinding;
import com.meebu.model.HomeData;
import com.meebu.utils.RobotoRegularTextView;
import com.meebu.utils.RubikBoldButton;
import com.meebu.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.meebu.api.Constant.BASEURL;

public class SignupScreen extends AppCompatActivity {

    RubikBoldButton sp_continue;
    String mobile="";
    SessionManager sessionManager;

    RobotoRegularTextView  txt_signin;
    ActivitySignupScreenBinding binding;
    private String TAG="SignUPScreen";
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding= DataBindingUtil.setContentView(SignupScreen.this,R.layout.activity_signup_screen);
        mobile=getIntent().getStringExtra("mobile");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
sessionManager=new SessionManager(SignupScreen.this);
        binding.txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupScreen.this,SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                finish();
            }
        });

        binding.spContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  startActivity(new Intent(SignupScreen.this,HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();*/


                if (isValid())
                {
                    registerUser();

                }
            }
        });
    }

    private void registerUser() {
        progressDialog.show();
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.updateUserData(binding.regFullname.getText().toString(), binding.regEdEmail.getText().toString(), binding.regPass.getText().toString(), mobile, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                BufferedReader bufferedReader = null;
                try {
                    bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    Log.d(TAG, "Success " + stringBuilder);
                    if (stringBuilder != null || !(stringBuilder.toString().equalsIgnoreCase(""))) {


progressDialog.dismiss();
JSONObject jsonObject=new JSONObject(""+stringBuilder);
                        if (jsonObject.getString("status").equalsIgnoreCase("1"))

                        {


                            JSONObject jsonObject1=jsonObject.getJSONObject("data");


                            sessionManager.createLoginSession(jsonObject1.getString("email_id"),jsonObject1.getString("full_name"),jsonObject1.getString("id"),binding.regPass.getText().toString(),mobile);



                            startActivity(new Intent(SignupScreen.this,HomeActivity.class));

                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignupScreen.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                    else
                    {
                        progressDialog.dismiss();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(binding.regEdEmail.getText().toString());


        if (binding.regFullname.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, ""+getResources().getString(R.string.Please_enter_fullname), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.regFullname.requestFocus();
            return false;
        }
       else if (binding.regEdEmail.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, ""+getResources().getString(R.string.Please_enter_email), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.regEdEmail.requestFocus();
            return false;
        }
        else if (!matcher.matches()) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, ""+getResources().getString(R.string.Please_Enter_Valid_Email), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.regEdEmail.requestFocus();
            return false;
        }
        else  if (binding.regPass.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, ""+getResources().getString(R.string.Please_Enter_Password), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.regPass.requestFocus();
            return false;
        }
        else if (!(binding.regPass.getText().toString().equals(binding.regCpass.getText().toString()))) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, ""+getResources().getString(R.string.Password_doesnt_match), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.regCpass.requestFocus();
            return false;
        }




        return true;
    }
}
