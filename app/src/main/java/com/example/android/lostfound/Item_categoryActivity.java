package com.example.android.lostfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

public class Item_categoryActivity extends AppCompatActivity {
    private ImageView phone,watch, laptop, wallets;
    private ImageView umbrella, textbooks, cards;
    private ImageView headPhonesHandFree,bicycle,others,idcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_category);
        phone = findViewById(R.id.phone);
        watch= findViewById(R.id.watch);
        laptop = findViewById(R.id.laptop);
        wallets= findViewById(R.id.wallets);
        umbrella = findViewById(R.id.umbrella);
        textbooks = findViewById(R.id.text_or_notebooks);
        cards = findViewById(R.id.cards);
        headPhonesHandFree = findViewById(R.id.headphones_handfree);
        bicycle = findViewById(R.id.bicycle);
        others = findViewById(R.id.others);
        idcard=findViewById(R.id.Idcard);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","phone");
                startActivity(intent);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","watch");
                startActivity(intent);

            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Item_categoryActivity.this,"You have lost your laptop?",Toast.LENGTH_LONG);
                Intent intent = new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","laptop");
                startActivity(intent);
            }
        });
        idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Item_categoryActivity.this,"You have lost your idcard?",Toast.LENGTH_LONG);
                Intent intent = new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","idcard");
                startActivity(intent);
            }
        });
        wallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Item_categoryActivity.this,"You have lost your wallets?",Toast.LENGTH_LONG);
                Intent intent=new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","wallet");
                startActivity(intent);
            }
        });
        umbrella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(Item_categoryActivity.this,"You have lost your umbrella?",Toast.LENGTH_LONG);
                Intent intent = new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","umbrella");
                startActivity(intent);
            }
        });
        textbooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Item_categoryActivity.this,"You have lost your textbook or notebook?",Toast.LENGTH_LONG);
                Intent intent= new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","textbook");
                startActivity(intent);
            }
        });
        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(Item_categoryActivity.this,"You have lost your cards?",Toast.LENGTH_LONG);
                Intent intent =new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","payment card");
                startActivity(intent);
            }
        });
        headPhonesHandFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(Item_categoryActivity.this,"you have lost your headphone ?",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","headphone/earphone");
                startActivity(intent);
            }
        });
        bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             
                Intent intent = new Intent(Item_categoryActivity.this,add_postActivity.class);
                intent.putExtra("category","bicycle");
                startActivity(intent);
            }
        });
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Item_categoryActivity.this,add_postActivity.class);
                 intent.putExtra("category","please mention what you have lost/Found");
                startActivity(intent);
            }
        });

    }

}