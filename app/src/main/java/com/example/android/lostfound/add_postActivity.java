package com.example.android.lostfound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.lostfound.Prevalent.Prevalent;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class add_postActivity extends AppCompatActivity {
    private String categoryName,Desc,cat,saveCurrentDate,saveCurrentTime,temp;
    private Button addlost,addfound;
    private EditText item_category,desc;
    private Uri ImageUri;
    private static final int GalleryPick =1;
    private ImageView addimg;
    private ProgressDialog loadingBar;
    private StorageReference productImageRef;// to take reference in firebase storage
    private DatabaseReference lost_item_ref,found_item_ref,user_post,item_ref;//same as in login activity to get reference to the database
    private String productRandomKey,downloadImageUrl;
    private int option=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        productImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        lost_item_ref = FirebaseDatabase.getInstance().getReference().child("Lost");
        found_item_ref=FirebaseDatabase.getInstance().getReference().child("Found");
     user_post=FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getAdm()).child("Myposts");


        categoryName = getIntent().getExtras().get("category").toString();
        Toast.makeText(add_postActivity.this,"Lost/Found "+categoryName+" ?",Toast.LENGTH_LONG).show();
        addlost=(Button)findViewById(R.id.add_lost);
        addfound=(Button)findViewById(R.id.add_Found);
        item_category=(EditText)findViewById(R.id.product_name);
        desc=(EditText)findViewById(R.id.product_description);
        addimg=(ImageView)findViewById(R.id.select_product_image);

        loadingBar = new ProgressDialog(this);

        addimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option=1;
                temp=Prevalent.currentOnlineUser.getName()+"("+Prevalent.currentOnlineUser.getAdm().toUpperCase()+") Lost "+item_category.getText().toString();
                ValidateProductData();
            }
        });

        addfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option =2;
                temp=Prevalent.currentOnlineUser.getName()+"("+Prevalent.currentOnlineUser.getAdm().toUpperCase()+") Found "+item_category.getText().toString();
                ValidateProductData();
            }
        });


    }

    private void ValidateProductData() {
        Desc = desc.getText().toString();
        cat = item_category.getText().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Desc))
        {
            Toast.makeText(this, "Please write Item description...", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(cat))
        {
            Toast.makeText(this, "Please write Item category...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Post");
        loadingBar.setMessage("Please Wait,Post is being added");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate+saveCurrentTime;
        StorageReference filePath = productImageRef.child(ImageUri.getLastPathSegment()+productRandomKey+ ".jpg");
        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(add_postActivity.this,"Error :" +e.getMessage(), Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(add_postActivity.this, "Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                })
                        .addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task)
                            {
                                if (task.isSuccessful())
                                {
                                    downloadImageUrl = task.getResult().toString();

                                    Toast.makeText(add_postActivity.this, "got the image Url Successfully...", Toast.LENGTH_SHORT).show();

                                   SaveProductInfoToDatabase();
                                }
                            }
                        });
            }
        });

    }

    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("phone",Prevalent.currentOnlineUser.getPhone());
        productMap.put("bio",temp);
        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Desc);
        productMap.put("image", downloadImageUrl);
        productMap.put("category",cat);



         if(option==1){
             item_ref=lost_item_ref;
         }
         else item_ref=found_item_ref;


        item_ref.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(add_postActivity.this, Item_categoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(add_postActivity.this, "Post added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(add_postActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        user_post.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {

                        }
                        else
                        {

                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick&&resultCode== RESULT_OK){
            ImageUri = data.getData();
            addimg.setImageURI(ImageUri);
        }
    }
    private void openGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GalleryPick);
    }
}