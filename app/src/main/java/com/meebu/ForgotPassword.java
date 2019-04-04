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
import com.meebu.databinding.ActivityForgotPasswordBinding;
import com.meebu.utils.RobotoBoldTextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.meebu.api.Constant.BASEURL;

public class ForgotPassword extends AppCompatActivity {

    private static final String TAG = "ForgotPassword";
    RobotoBoldTextView  backsign;
    ActivityForgotPasswordBinding binding;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         binding= DataBindingUtil.setContentView(ForgotPassword.this,R.layout.activity_forgot_password);

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");


        binding.backsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this,SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });
        binding.fgSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {
                    forgotPassword();
                }
            }
        });
    }

    private void forgotPassword() {

        progressDialog.show();
        final StringBuilder stringBuilder=new StringBuilder();
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(BASEURL).build();
        final ApiInterface apiInterface = restAdapter.create(ApiInterface.class);
        apiInterface.forgotPassword(binding.fgEmail.getText().toString(), "1", new Callback<Response>() {
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
                        JSONObject jsonObject = new JSONObject("" + stringBuilder);
                        if (jsonObject.getString("status").equalsIgnoreCase("1"))

                        {

                            Toast.makeText(ForgotPassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(ForgotPassword.this,ForgotPasswordOTPScreen.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                            finish();


                        }
                        else
                        {
                            Toast.makeText(ForgotPassword.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                catch (Exception e)
                {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
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
        matcher = pattern.matcher(binding.fgEmail.getText().toString());

        if (binding.fgEmail.getText().toString().equals("")) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, getResources().getString(R.string.Please_enter_email), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.fgEmail.requestFocus();
            return false;
        }
        else if (!matcher.matches()) {

            GFMinimalNotification mCurrentNotification = GFMinimalNotification.make(binding.main, ""+getResources().getString(R.string.Please_Enter_Valid_Email), GFMinimalNotification.LENGTH_LONG, GFMinimalNotification.TYPE_ERROR);
            mCurrentNotification.setDirection(GFMinimalNotification.DIRECTION_TOP);
            mCurrentNotification.setHelperImage(R.drawable.group_40);
            mCurrentNotification.show();
            binding.fgEmail.requestFocus();
            return false;
        }







        return true;
    }
}
