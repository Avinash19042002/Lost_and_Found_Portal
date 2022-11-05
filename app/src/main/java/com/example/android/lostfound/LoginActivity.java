package com.example.android.lostfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.lostfound.Model.Users;
import com.example.android.lostfound.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private EditText InputAdmNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private TextView ForgetPasswordLink,register;
    private  String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        InputAdmNumber = findViewById(R.id.login_adm_number_input);
        InputPassword = findViewById(R.id.login_password_input);
        register = findViewById(R.id.do_not_have_account);
        LoginButton= findViewById(R.id.login_btn);
        ForgetPasswordLink = findViewById(R.id.forget_password_link);
        loadingBar = new ProgressDialog(this);
        chkBoxRememberMe =(CheckBox)findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        ForgetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,SettingsActivity.class));
            }
        });
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }
    private void loginUser(){

        String adm = InputAdmNumber.getText().toString().toUpperCase();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(adm))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("login Account");
            loadingBar.setMessage("Please Wait while we are checking your credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

          AllowAccesstoAccount(adm,password);
         //startActivity(new Intent(LoginActivity.this,HomeActivity.class));
        }
    }
    private void AllowAccesstoAccount(String adm,String password){
        if(chkBoxRememberMe.isChecked()){
            Paper.book().write(Prevalent.UserAdmKey,adm);
            Paper.book().write(Prevalent.UserPasswordKey,password);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
              if(snapshot.child("Users").child(adm).exists()){
                  Users usersData = snapshot.child("Users").child(adm).getValue(Users.class);
                  if(usersData.getAdm().equals(adm)){
                      if(usersData.getPassword().equals(password)){
                              Toast.makeText(LoginActivity.this, "logged in Successfully...", Toast.LENGTH_SHORT).show();
                              loadingBar.dismiss();
                              Intent intent =new Intent(LoginActivity.this,HomeActivity.class);
                              Prevalent.currentOnlineUser=usersData;
                              startActivity(intent);
                      }
                      else {
                          loadingBar.dismiss();
                          Toast.makeText(LoginActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                      }
                  }
              }
              else{
                  Toast.makeText(LoginActivity.this,adm +"is not registered in portal kindly enroll first",Toast.LENGTH_LONG).show();
                  loadingBar.dismiss();
                  startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}