package com.example.android.lostfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.lostfound.Model.Users;
import com.example.android.lostfound.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
private Button join_now_btn,login_btn;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        join_now_btn = findViewById(R.id.main_join_now_btn);
        login_btn=findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);
        join_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
Paper.init(this);
        String UserPhoneKey = Paper.book().read(Prevalent.UserAdmKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        if(UserPhoneKey!="" && UserPasswordKey!=""){
            if(!TextUtils.isEmpty(UserPhoneKey)&&!TextUtils.isEmpty(UserPasswordKey)){
                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

               AllowAccess(UserPhoneKey,UserPasswordKey);
            }
        }
    }
    private void AllowAccess(String adm, String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Users").child(adm).exists()){
                    Users usersData = snapshot.child("Users").child(adm).getValue(Users.class);
                    if(usersData.getAdm().equals(adm)){
                        if(usersData.getPassword().equals(password)){
                            Toast.makeText(MainActivity.this,"Please wait you are Already logged in!!",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Prevalent.currentOnlineUser = usersData;
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));

                        }
                        else {
                            loadingBar.dismiss();
                            Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + adm + " do not exists.", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}