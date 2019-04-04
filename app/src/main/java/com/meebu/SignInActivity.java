package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.gfranks.minimal.notification.GFMinimalNotification;
import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivitySignInBinding;
import com.meebu.utils.RobotoBoldTextView;
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

import static com.github.gfranks.minimal.notification.GFMinimalNotification.LENGTH_LONG;
import static com.meebu.api.Constant.BASEURL;

public class SignInActivity extends BaseActivity {

    RobotoRegularTextView signin;
    RobotoBoldTextView forgot;
    RubikBoldButton signin_btn;
    ActivitySignInBinding binding;
    private String TAG="SignInActivity";
    SessionManager sessionManager;
    public static boolean dataConnection=false;

    ProgressDialog progressDialog;

    @Override
    protected void onNetworkOn() {
        Toast.makeText(this, "on", Toast.LENGTH_SHORT).show();
        dataConnection=true;
    }

    @Override
    protected void onNetworkOff() {
        dataConnection=false;
        Toast.makeText(this, "off", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(SignInActivity.this,R.layout.activity_sign_in);
        progressDialog=new ProgressDialog(this);
        sessionManager=new SessionManager(SignInActivity.this);
        progressDialog.setMessage("Please Wait");
        binding.signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,MobileSignUpActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });
        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dataConnection) {

                    if (isValid()) {
                        loginUser();

                    }
                }
                else
                {
                    Toast.makeText(SignInActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                }



            }
        });
        if (sessionManager.isLoggedIn())
        {
            startActivity(new Intent(SignInActivity.this,HomeActivity.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            finish();
        }
        binding.forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(SignInActivity.this,ForgotPassword.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
    }

    private void loginUser() {
        progressDialog.show();
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);

        apiInterface.login(binding.edEmailMob.getText().toString(), binding.edPassword.getText().toString(), "1", new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                try {
                    BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));
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


                            sessionManager.createLoginSession(jsonObject1.getString("email_id"),jsonObject1.getString("full_name"),jsonObject1.getString("id"),binding.edPassword.getText().toString(),jsonObject1.getString("mobile_no"));
                            startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(SignInActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public boolean isValid() {


         if (binding.edEmailMob.getText().toString().equals("")) {

             GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_email), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
             mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
             mCurrentNotification.setHelperImage(R.drawable.group_40);
             mCurrentNotification.show();
            binding.edEmailMob.requestFocus();
            return false;
        }

        else  if (binding.edPassword.getText().toString().equals("")) {

             GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_Enter_Password), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
             mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
             mCurrentNotification.setHelperImage(R.drawable.group_40);
             mCurrentNotification.show();
            binding.edPassword.requestFocus();
            return false;
        }





        return true;
    }
}
