package com.example.android.lostfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
  private Button createAccountButton;
  private EditText inputName,inputAdm,inputPassword,inputPhone;
  private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createAccountButton = findViewById(R.id.register_btn);
        inputName = findViewById(R.id.register_username_input);
        inputPassword=findViewById(R.id.register_password_input);
        inputAdm=findViewById(R.id.register_adm_number_input);
        inputPhone=findViewById(R.id.register_phone_input);

        loadingBar = new ProgressDialog(this);
       createAccountButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CreateAccount();
           }
       });
    }

    private void CreateAccount() {

        String name = inputName.getText().toString();
        String adm = inputAdm.getText().toString().toUpperCase();
        String phone_user=inputPhone.getText().toString().toUpperCase();
        String password = inputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(adm))
        {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        }
        else{
    loadingBar.setTitle("Create Account");
    loadingBar.setMessage("Please Wait while we are checking your credentials");
    loadingBar.setCanceledOnTouchOutside(false);
    loadingBar.show();
    ValidateAdmNumber(name,adm,password,phone_user);
    }

    }
    private void ValidateAdmNumber(String name,String adm,String password,String phone_user){
      final DatabaseReference RootRef;
      RootRef = FirebaseDatabase.getInstance().getReference();
      RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(!(snapshot.child("Users").child(adm).exists())){
                HashMap<String,Object>userdataMap = new HashMap<>();
                userdataMap.put("adm", adm);
                userdataMap.put("password", password);
                userdataMap.put("name", name);
                userdataMap.put("phone",phone_user);
                RootRef.child("Users").child(adm).updateChildren(userdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this,"Congratulations! Your Account has been SuccessFully created!",Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }
                                else{
                                    loadingBar.dismiss();
                                    Toast.makeText(RegisterActivity.this,"Oops failed to register!! check Internet",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else{
                Toast.makeText(RegisterActivity.this, "This "+ adm + " Already exists", Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Toast.makeText(RegisterActivity.this,"Please Try using another Admission number",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {

          }
      });
    }
}