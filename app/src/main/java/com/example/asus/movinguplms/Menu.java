package com.example.asus.movinguplms;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Menu extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private android.support.v7.widget.Toolbar mToolbar;
    private CircleImageView NavProfileImage;
    private TextView NavProfileUserName;
    private FloatingActionButton mFab;

    private ImageButton viewLearn;

    private FirebaseAuth mAuth;
    private DatabaseReference UsersRef, PostsRef;

    String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            SendUserToLoginActivity();
        }
        else{
            currentUserID = mAuth.getCurrentUser().getUid();
        }

        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(Menu.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        View navView = navigationView.inflateHeaderView(R.layout.navigation_header);
        NavProfileImage = (CircleImageView) navView.findViewById(R.id.nav_profile_image);
        NavProfileUserName = (TextView) navView.findViewById(R.id.nav_user_full_name);

        mFab = (FloatingActionButton) findViewById(R.id.SearchFAB);

        UsersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("username"))                    {
                        String fullname = dataSnapshot.child("username").getValue().toString();
                        NavProfileUserName.setText(fullname);
                    }
                    if(dataSnapshot.hasChild("profileimage"))
                    {
                        String image = dataSnapshot.child("profileimage").getValue().toString();
                        Picasso.get().load(image).placeholder(R.drawable.profile).into(NavProfileImage);
                    }
                    else
                    {
                        Toast.makeText(Menu.this, "Profile name do not exists...", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        viewLearn = (ImageButton) findViewById(R.id.btnshsprograms);

        viewLearn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendToLearnActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            SendUserToLoginActivity();
        }
        else
        {
            CheckUserExistence();
        }
    }

    private void CheckUserExistence()
    {
        final String current_user_id = mAuth.getCurrentUser().getUid();

        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(!dataSnapshot.hasChild(current_user_id))
                {
                    SendUserToSetupActivity();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_shsprograms:
                Toast.makeText(this, "SHS Programs", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_schools:
                Toast.makeText(this, "All Schools", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_find_schools:
                Toast.makeText(this, "Find Schools", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_aboutus:
                Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_Logout:
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                SendUserToLoginActivity();
                break;
        }
    }

    private void SendUserToLoginActivity()
    {
        Intent loginIntent = new Intent(Menu.this, LoginSignupMenuActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    private void SendUserToSetupActivity()
    {
        Intent setupIntent = new Intent(Menu.this, CreateUserProfileActivity.class);
        setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setupIntent);
        finish();
    }

    private void SendToLearnActivity() {
        Intent learnIntent = new Intent(Menu.this, LearnActivity.class);
        startActivity(learnIntent);
    }
}
