package com.example.asus.movinguplms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

public class DiscoverSchoolsOnListActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar mToolbar;
    private RecyclerView schoollist;
    private DatabaseReference SlistsRef, SavedSchoolListReference;
    private FirebaseAuth mAuth;

    private Button searchschool, searchtype, searchtrack, searchstrand;
    private Button firstcategory, secondcategory, thirdcategory, fourthcategory;

    String searchelement, searchparam;
    String currentUserID;
    Boolean SavedSchoolChecker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_schools_on_list);

        SlistsRef = FirebaseDatabase.getInstance().getReference().child("SchoolList");
        SavedSchoolListReference = FirebaseDatabase.getInstance().getReference().child("SavedSchools");
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        schoollist = (RecyclerView) findViewById(R.id.schoollist_view);
        schoollist.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        schoollist.setLayoutManager(linearLayoutManager);

        searchschool = findViewById(R.id.search_school);
        searchtype = findViewById(R.id.search_type);
        searchtrack = findViewById(R.id.search_track);
        firstcategory = findViewById(R.id.firstcategorysearch);
        secondcategory = findViewById(R.id.secondcategorysearch);
        thirdcategory = findViewById(R.id.thirdcategorysearch);
        fourthcategory = findViewById(R.id.fourthcategorysearch);

        searchschool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstcategory.setText("SCHOOL");
                secondcategory.setText("");
                thirdcategory.setText("");
                fourthcategory.setText("");
                firstcategory.setEnabled(true);
                secondcategory.setEnabled(false);
                thirdcategory.setEnabled(false);
                fourthcategory.setEnabled(false);
                firstcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                searchparam = "";
                searchelement = "searchkeyschoolname";
                DisplaySchoolList(searchparam, searchelement);
            }
        });

        searchtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstcategory.setText("PUBLIC");
                secondcategory.setText("PRIVATE");
                thirdcategory.setText("");
                fourthcategory.setText("");
                firstcategory.setEnabled(true);
                secondcategory.setEnabled(true);
                thirdcategory.setEnabled(false);
                fourthcategory.setEnabled(false);
                firstcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                firstcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                        secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        searchparam = "public";
                        searchelement = "searchschooltype1";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                });
                secondcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        secondcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                        thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        searchparam = "private";
                        searchelement = "searchschooltype2";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                });
                searchparam = "public";
                searchelement = "searchschooltype1";
                DisplaySchoolList(searchparam, searchelement);
            }
        });

        searchtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstcategory.setText("ACADEMIC");
                secondcategory.setText("TVL");
                thirdcategory.setText("SPORTS");
                fourthcategory.setText("ARTS");
                firstcategory.setEnabled(true);
                secondcategory.setEnabled(true);
                thirdcategory.setEnabled(true);
                fourthcategory.setEnabled(true);
                firstcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                firstcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                        secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        searchparam = "academic";
                        searchelement = "searchschooltrack1";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                });
                secondcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        secondcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                        thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        searchparam = "tvl";
                        searchelement = "searchschooltrack2";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                });

                thirdcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        thirdcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                        fourthcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        searchparam = "sports";
                        searchelement = "searchschooltrack3";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                });

                fourthcategory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firstcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        secondcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        thirdcategory.setBackgroundColor(Color.parseColor("#66ADD8E6"));
                        fourthcategory.setBackgroundColor(Color.parseColor("#66567f86"));
                        searchparam = "arts";
                        searchelement = "searchschooltrack4";
                        DisplaySchoolList(searchparam, searchelement);
                    }
                });
                searchparam = "academic";
                searchelement = "searchschooltrack1";
                DisplaySchoolList(searchparam, searchelement);
            }
        });

        searchparam = "";
        searchelement = "searchkeyschoolname";
        DisplaySchoolList(searchparam, searchelement);
    }

    private void DisplaySchoolList(String searchparam, String searchelement) {
        Query firebaseSearchQuery = SlistsRef.orderByChild(searchelement).startAt(searchparam).endAt(searchparam + "\uf8ff");

        FirebaseRecyclerAdapter<Schools, SchoollistViewHolder > firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Schools, SchoollistViewHolder>(
                Schools.class,
                R.layout.schoollists,
                SchoollistViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(SchoollistViewHolder viewHolder, Schools model, int position) {
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
        schoollist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SchoollistViewHolder extends RecyclerView.ViewHolder {
        View mView;

        ImageButton SavedSchoolToListButton;
        TextView DisplayNumberOfSaves;
        int countSaves;
        String currentUserId;
        DatabaseReference SavedSchoolListReference;

        public SchoollistViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

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

        public void setSchoolimage(Context ctx1,  String schoolimage)
        {
            ImageView simage = (ImageView) mView.findViewById(R.id.slist_schoolimage);
            Picasso.get().load(schoolimage).into(simage);
        }
    }
}
