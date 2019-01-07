package com.example.asus.movinguplms;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscoverSchoolsSearchActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar csToolbar;
    private ImageButton cssearchbtn;
    private RecyclerView cssearchrview;
    private DatabaseReference ClassifiedSearchRef;
    private DatabaseReference SavedSchoolListReference;
    private EditText edtx;
    private TabLayout searchtabs;
    private FirebaseAuth mAuth;

    String searchelement;
    String searchparam;
    String currentUserID;
    Boolean SavedSchoolChecker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_schools_search);

        ClassifiedSearchRef = FirebaseDatabase.getInstance().getReference().child("SchoolList");
        SavedSchoolListReference = FirebaseDatabase.getInstance().getReference().child("SavedSchools");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        csToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.searchselection_toolbar);
        setSupportActionBar(csToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cssearchrview = (RecyclerView) findViewById(R.id.classifiedsearchlist_view);
        cssearchrview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        cssearchrview.setLayoutManager(linearLayoutManager);

        edtx = (EditText) findViewById(R.id.searchselection_text);
        cssearchbtn = (ImageButton) findViewById(R.id.btn_schoolsearch);

        edtx.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String searchparam = edtx.getText().toString().toLowerCase();
                    if (searchparam.equals("public") || searchparam.equals("pub") || searchparam.equals("publictype")
                            || searchparam.equals("public type"))
                    {
                        searchelement = "searchschooltype1";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                    else if (searchparam.equals("private") || searchparam.equals("priv") || searchparam.equals("privatetype")
                            || searchparam.equals("private type"))
                    {
                        searchelement = "searchschooltype2";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                    else if (searchparam.equals("academic") || searchparam.equals("acad") || searchparam.equals("academictrack")
                            || searchparam.equals("academic track"))
                    {
                        searchelement = "searchschooltrack1";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                    else if (searchparam.equals("tvl") || searchparam.equals("technical") || searchparam.equals("vocational")
                            || searchparam.equals("livelihood") || searchparam.equals("technical vocational livelihood")
                            || searchparam.equals("technicalvocationallivelihood"))
                    {
                        searchelement = "searchschooltrack2";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                    else if (searchparam.equals("sport") || searchparam.equals("sports") || searchparam.equals("sporttrack")
                            || searchparam.equals("sportstrack") || searchparam.equals("sport track") || searchparam.equals("sports track"))
                    {
                        searchelement = "searchschooltrack3";
                        DisplaySchoolList(searchparam, searchelement);

                    }
                    else if (searchparam.equals("art") || searchparam.equals("arts") || searchparam.equals("artsanddesign")
                            || searchparam.equals("artsand") || searchparam.equals("design") || searchparam.equals("arts")
                            || searchparam.equals("arts and design") || searchparam.equals("art and design")
                            || searchparam.equals("artsdesign") || searchparam.equals("artdesign"))
                    {
                        searchelement = "searchschooltrack4";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                    else {
                        searchelement = "searchkeyschoolname";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                    return true;
                }
                return false;
            }
        });

        cssearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchparam = edtx.getText().toString().toLowerCase();
                if (searchparam.equals("public") || searchparam.equals("pub") || searchparam.equals("publictype")
                        || searchparam.equals("public type"))
                {
                    searchelement = "searchschooltype1";
                    DisplaySchoolList(searchparam, searchelement);
                }
                else if (searchparam.equals("private") || searchparam.equals("priv") || searchparam.equals("privatetype")
                        || searchparam.equals("private type"))
                {
                    searchelement = "searchschooltype2";
                    DisplaySchoolList(searchparam, searchelement);
                }
                else if (searchparam.equals("academic") || searchparam.equals("acad") || searchparam.equals("academictrack")
                        || searchparam.equals("academic track"))
                {
                    searchelement = "searchschooltrack1";
                    DisplaySchoolList(searchparam, searchelement);
                }
                else if (searchparam.equals("tvl") || searchparam.equals("technical") || searchparam.equals("vocational")
                        || searchparam.equals("livelihood") || searchparam.equals("technical vocational livelihood")
                        || searchparam.equals("technicalvocationallivelihood"))
                {
                    searchelement = "searchschooltrack2";
                    DisplaySchoolList(searchparam, searchelement);
                }
                else if (searchparam.equals("sport") || searchparam.equals("sports") || searchparam.equals("sporttrack")
                        || searchparam.equals("sportstrack") || searchparam.equals("sport track") || searchparam.equals("sports track"))
                {
                    searchelement = "searchschooltrack3";
                    DisplaySchoolList(searchparam, searchelement);

                }
                else if (searchparam.equals("art") || searchparam.equals("arts") || searchparam.equals("artsanddesign")
                        || searchparam.equals("artsand") || searchparam.equals("design") || searchparam.equals("arts")
                        || searchparam.equals("arts and design") || searchparam.equals("art and design")
                        || searchparam.equals("artsdesign") || searchparam.equals("artdesign"))
                {
                    searchelement = "searchschooltrack4";
                    DisplaySchoolList(searchparam, searchelement);
                }
                else {
                    searchelement = "searchkeyschoolname";
                    DisplaySchoolList(searchparam, searchelement);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DisplaySchoolList(String searchparam, String searchelement) {
        Toast.makeText(DiscoverSchoolsSearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = ClassifiedSearchRef.orderByChild(searchelement).startAt(searchparam).endAt(searchparam + "\uf8ff");

        FirebaseRecyclerAdapter<Schools, SearchSchoolViewHolder > firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Schools, SearchSchoolViewHolder>(
                Schools.class,
                R.layout.schoollists,
                SearchSchoolViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(SearchSchoolViewHolder viewHolder, Schools model, int position) {
                final String DisplayKey = getRef(position).getKey();
                viewHolder.setSchoolname(model.getSchoolname());
                viewHolder.setStrandoffers(model.getStrandoffers());
                viewHolder.setTrackoffers(model.getTrackoffers());
                viewHolder.setSchooltype(model.getSchooltype());
                viewHolder.setSchoollocation(model.getSchoollocation());
                viewHolder.setSchoollogo(getApplicationContext(), model.getSchoollogo());
                viewHolder.setSchoolimage(getApplicationContext(), model.getSchoolimage());
                viewHolder.setSaveButtonStatus(DisplayKey);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent clickSchoolInfoDisplayIntent = new Intent(DiscoverSchoolsSearchActivity.this, DiscoverSchoolContentActivity.class);
                        clickSchoolInfoDisplayIntent.putExtra("DisplayKey", DisplayKey);
                        startActivity(clickSchoolInfoDisplayIntent);
                    }
                });

                viewHolder.SavedSchoolToListButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SavedSchoolChecker = true;
                        SavedSchoolListReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (SavedSchoolChecker.equals(true)) {
                                    if (dataSnapshot.child(DisplayKey).hasChild(currentUserID)) {
                                        SavedSchoolListReference.child(DisplayKey).child(currentUserID).removeValue();
                                        SavedSchoolChecker = false;
                                    } else {
                                        SavedSchoolListReference.child(DisplayKey).child(currentUserID).setValue(true);
                                        SavedSchoolChecker = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }

        };
        cssearchrview.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SearchSchoolViewHolder extends RecyclerView.ViewHolder{
        View mView;

        ImageButton SavedSchoolToListButton;
        TextView DisplayNumberOfSaves;
        int countSaves;
        String currentUserId;
        DatabaseReference SavedSchoolListReference;

        public SearchSchoolViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            SavedSchoolToListButton = (ImageButton) mView.findViewById(R.id.save_school_Button);
            DisplayNumberOfSaves = (TextView) mView.findViewById(R.id.save_counter);

            SavedSchoolListReference = FirebaseDatabase.getInstance().getReference().child("SavedSchools");
            currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

        public void setSaveButtonStatus(final String DisplayKey)
        {
            SavedSchoolListReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(DisplayKey).hasChild(currentUserId))
                    {
                        countSaves = (int) dataSnapshot.child(DisplayKey).getChildrenCount();
                        SavedSchoolToListButton.setImageResource(R.drawable.like);
                        DisplayNumberOfSaves.setText((Integer.toString(countSaves)+(" Saves")));
                    }
                    else
                    {
                        countSaves = (int) dataSnapshot.child(DisplayKey).getChildrenCount();
                        SavedSchoolToListButton.setImageResource(R.drawable.dislike);
                        DisplayNumberOfSaves.setText(Integer.toString(countSaves));
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        public void setSchoolname(String schoolname)
        {
            TextView sname = (TextView) mView.findViewById(R.id.slist_schoolname);
            sname.setText(schoolname);
        }

        public void setSchoollogo(Context ctx, String schoollogo)
        {
            CircleImageView slogo = (CircleImageView) mView.findViewById(R.id.slist_logo);
            Picasso.get().load(schoollogo).into(slogo);
        }

        public void setSchooltype(String schooltype)
        {
            TextView stype = (TextView) mView.findViewById(R.id.slist_schooltype);
            stype.setText(schooltype);
        }

        public void setStrandoffers(String strandoffers)
        {
            TextView sstrand = (TextView) mView.findViewById(R.id.slist_strandoffers);
            sstrand.setText("    Strand Offers: " + strandoffers);
        }

        public void setSchoollocation(String schoollocation)
        {
            TextView sloc = (TextView) mView.findViewById(R.id.slist_location);
            sloc.setText(schoollocation);
        }

        public void setTrackoffers(String trackoffers)
        {
            TextView stracks = (TextView) mView.findViewById(R.id.slist_trackoffers);
            stracks.setText("    Track Offers: " + trackoffers);
        }

        public void setSchoolimage(Context ctx1, String schoolimage)
        {
            ImageView simage = (ImageView) mView.findViewById(R.id.slist_schoolimage);
            Picasso.get().load(schoolimage).into(simage);
        }
    }
}
