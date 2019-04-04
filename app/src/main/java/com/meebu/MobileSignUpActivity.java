package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gfranks.minimal.notification.GFMinimalNotification;
import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivityMobileSignUpBinding;
import com.meebu.utils.RubikBoldButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.meebu.api.Constant.BASEURL;

public class MobileSignUpActivity extends AppCompatActivity {
    private static final String TAG ="MobileSignUpActivity" ;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ActivityMobileSignUpBinding binding= DataBindingUtil.setContentView(MobileSignUpActivity.this,R.layout.activity_mobile_sign_up);

        progressDialog=new ProgressDialog(this);
progressDialog.setMessage("Please Wait");

        binding.txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileSignUpActivity.this,SignInActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });


        binding.mobileSignupContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str=binding.edMobileNumber.getText().toString();
                if(str != null && !str.isEmpty()) {

                    createUser(str);
                }
                else {
                    GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, "Please Enter Mobile Number", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
                    mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
                    mCurrentNotification.setHelperImage(R.drawable.group_40);
                    mCurrentNotification.show();

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    private void createUser(final String str) {
        progressDialog.show();
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.createUser(str, new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {

                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody().in()));

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
                            startActivity(new Intent(MobileSignUpActivity.this,VerificationCodeActivity.class).putExtra("mobile",str));
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);


                            finish();

                        }
                        else
                        {
                            Toast.makeText(MobileSignUpActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
}
