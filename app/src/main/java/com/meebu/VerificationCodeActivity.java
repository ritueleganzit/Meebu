package com.meebu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.gfranks.minimal.notification.GFMinimalNotification;
import com.meebu.api.ApiInterface;
import com.meebu.databinding.ActivityVerificationCodeBinding;

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

public class VerificationCodeActivity extends AppCompatActivity {
    private String TAG="OTPSCreen";
    ProgressDialog progressDialog;
    String mobile="";
    ActivityVerificationCodeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding= DataBindingUtil.setContentView(VerificationCodeActivity.this,R.layout.activity_verification_code);
        progressDialog=new ProgressDialog(this);
        mobile=getIntent().getStringExtra("mobile");
        progressDialog.setMessage("Please Wait");
        binding.txtSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerificationCodeActivity.this,SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        });
        binding.verifySignupContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String pin=binding.vrCode.getText().toString();
                if(pin != null && !pin.isEmpty()) {

                    verifyCode(pin);
                }
                else
                {
                    GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, "Please Enter Verification Code", GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
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

    public void verifyCode(String pin)
    {
        progressDialog.show();
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.verifyCode(mobile, pin, "1", new Callback<Response>() {
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
                            startActivity(new Intent(VerificationCodeActivity.this,SignupScreen.class).putExtra("mobile",mobile));
                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();



                        }
                        else
                        {
                            Toast.makeText(VerificationCodeActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

                String line;
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
            }
        });
    }
}
