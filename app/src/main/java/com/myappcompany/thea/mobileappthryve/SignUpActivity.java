package com.myappcompany.thea.mobileappthryve;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    Retrofit retrofit;
    JsonPlaceHolderApi jsonPlaceHolderApi;

    private TextView signupAlert;
    private EditText registerFname;
    private EditText registerLname;
    private EditText registerSheridanId;
    private EditText registerEmail;
    private EditText registerPassword;
    private EditText registerConfirmPass;
    private Button registerButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://web-app-thrv.us-east-2.elasticbeanstalk.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        signupAlert = (TextView) findViewById(R.id.signup_alert);
        registerFname = (EditText) findViewById(R.id.reg_fname);
        registerLname = (EditText) findViewById(R.id.reg_lname);
        registerSheridanId = (EditText) findViewById(R.id.reg_sheridanid);
        registerEmail = (EditText) findViewById(R.id.reg_email);
        registerPassword = (EditText) findViewById(R.id.et_password);
        registerConfirmPass = (EditText) findViewById(R.id.et_repassword);

        registerButton = (Button) findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewStudentAuth();
            }
        });
        //createNewStudentAuth();
    }

    private void createNewStudentAuth() {
        String textFname = registerFname.getText().toString().replaceAll("\\s", "");
        String textLname = registerLname.getText().toString().replaceAll("\\s", "");
        String textId = registerSheridanId.getText().toString().replaceAll("\\s", "");
        String textEmail = registerEmail.getText().toString().replaceAll("\\s", "");
        String textPassword = registerPassword.getText().toString().replaceAll("\\s", "");
        String textConfirmPassword = registerConfirmPass.getText().toString().replaceAll("\\s", "");

        if (textFname.equals("")
                || textLname.equals("")
                || textId.equals("")
                || textEmail.equals("")
                || textPassword.equals("")
                || textConfirmPassword.equals("")) {
            signupAlert.setText("Some fields missing");
        }
        else {
            Call<StudentContainer> call = jsonPlaceHolderApi.getStudentAccounts("student/get-studentaccount");

            call.enqueue(new Callback<StudentContainer>() {
                @Override
                public void onResponse(Call<StudentContainer> call, Response<StudentContainer> response) {
                    if (!response.isSuccessful()) {
                        signupAlert.setText(response.isSuccessful() + ": Code: " + response.code());
                        return;
                    }

                    StudentContainer container = response.body();

                    List<StudentAccount> studentAccounts = container.getMyStudentAccounts();

                    for (StudentAccount studentAccount: studentAccounts) {
                        if (textId.equals(studentAccount.getStudentNumber())) {
                            signupAlert
                        }
                        if (textEmail.equals(studentAccount.getEmailAddress())) {
                            b = true;
                        }
                    }


                }

                @Override
                public void onFailure(Call<StudentContainer> call, Throwable t) {
                    signupAlert.setText("Failure: " + t.getMessage());
                }
            });
        }

        /*Call<StudentAuthContainer> call = jsonPlaceHolderApi.createStudentAuth(textId,textPassword);

            call.enqueue(new Callback<StudentAuthContainer>() {
                @Override
                public void onResponse(Call<StudentAuthContainer> call, Response<StudentAuthContainer> response) {
                    if (!response.isSuccessful()) {
                        signupAlert.setText(response.isSuccessful() + ": Code: " + response.code());
                        return;
                    }

                    StudentAuthContainer container = response.body();

                    List<StudentAuth> studentAuths = container.getMyStudentAuths();

                    String content = "";
                    for (StudentAuth studentAuth: studentAuths) {
                        content = "";
                        content += "success: " + studentAuth.getSheridanId() + ": " + studentAuth.getPassword() + "\n";

                        signupAlert.append(content);
                    }

                    signupAlert.setText(content);
                }

                @Override
                public void onFailure(Call<StudentAuthContainer> call, Throwable t) {
                    signupAlert.setText("Failure: " + t.getMessage());
                }
            });*/

        /*call.enqueue(new Callback<StudentAuth>() {
            @Override
            public void onResponse(Call<StudentAuth> call, Response<StudentAuth> response) {
                if (!response.isSuccessful()) {
                    signupAlert.setText(response.isSuccessful() + ": Code: " + response.code());
                    return;
                }

                StudentAuthContainer container = response.body();

                String content = "";
                content += response.code() + ": " + authResponse.getSheridanId() + ": " + authResponse.getPassword();
                signupAlert.setText(content);
            }

            @Override
            public void onFailure(Call<StudentAuth> call, Throwable t) {
                signupAlert.setText("Failure: " + t.getMessage());
            }
        });*/
    }
}
