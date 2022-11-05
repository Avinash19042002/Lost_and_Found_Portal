package com.example.android.lostfound;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.lostfound.Model.Items;
import com.example.android.lostfound.Prevalent.Prevalent;
import com.example.android.lostfound.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Lost");
        setSupportActionBar(toolbar);



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Lost");
         layoutManager = new LinearLayoutManager(this);
         recyclerView = (RecyclerView)findViewById(R.id.recycler_menu_lost);
         recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               startActivity(new Intent(HomeActivity.this,Item_categoryActivity.class));
                }

            });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);
        userNameTextView.setText(Prevalent.currentOnlineUser.getName());

    }


    @Override
    public void onStart()
    {
        super.onStart();

        FirebaseRecyclerOptions<Items> options =
                new FirebaseRecyclerOptions.Builder<Items>()
                        .setQuery(ProductsRef, Items.class)
                        .build();

        FirebaseRecyclerAdapter<Items, ItemViewHolder> adapter= new FirebaseRecyclerAdapter<Items, ItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull  ItemViewHolder holder, int position, @NonNull  Items model) {
                holder.txtAuthorName.setText("Posted By : "+model.getBio());
                holder.txtDescription.setText("Desc :"+model.getDescription());
                holder.txtDateTime.setText("Date :" + model.getDate()+" , "+model.getTime());
                holder.txtPhone.setText("Phone : "+model.getPhone());

                Picasso.get().load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false);
                ItemViewHolder holder = new ItemViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
       if(id==R.id.nav_home){

       }
       else if(id==R.id.nav_found){
       startActivity(new Intent(HomeActivity.this,FoundActivity.class));
       }
        if (id == R.id.nav_add_post) {
            startActivity(new Intent(HomeActivity.this,Item_categoryActivity.class));

        } else if (id == R.id.nav_myposts) {
         startActivity(new Intent(HomeActivity.this,MyPostActivity.class));
        } else if (id == R.id.search_btn) {

        } else if (id == R.id.settings) {
            startActivity(new Intent(HomeActivity.this,SettingsActivity.class));

        } else if (id == R.id.logout_btn){
            Paper.book().destroy();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
