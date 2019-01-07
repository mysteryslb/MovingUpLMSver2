package com.example.asus.movinguplms;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiscoverSchoolContentActivity extends AppCompatActivity {

    private String DisplayKey;
    private ImageView ContentSchoolImage;
    private CircleImageView ContentSchoolLogo;
    private TextView ContentSchoolName, ContentSchoolType, ContentSchoolLocation;
    private RecyclerView schoolContentSearchrview;
    private DatabaseReference ContentReference, SchoolContentSearchRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_school_content);

        DisplayKey = getIntent().getExtras().get("DisplayKey").toString();

        ContentReference = FirebaseDatabase.getInstance().getReference().child("SchoolList").child(DisplayKey);

        ContentSchoolImage = (ImageView) findViewById(R.id.content_schoolimage);
        ContentSchoolLogo = (CircleImageView) findViewById(R.id.content_schoollogo);
        ContentSchoolName = (TextView) findViewById(R.id.content_schoolname);
        ContentSchoolType = (TextView) findViewById(R.id.content_schooltype);
        ContentSchoolLocation = (TextView) findViewById(R.id.content_schoollocation);


        ContentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String schoolname = dataSnapshot.child("schoolname").getValue().toString();
                String schooltype = dataSnapshot.child("schooltype").getValue().toString();
                String schoollocation = dataSnapshot.child("schoollocation").getValue().toString();
                String schoollogo = dataSnapshot.child("schoollogo").getValue().toString();
                String schoolimage = dataSnapshot.child("schoolimage").getValue().toString();

                ContentSchoolName.setText(schoolname);
                ContentSchoolType.setText(schooltype);
                ContentSchoolLocation.setText(schoollocation);
                Picasso.get().load(schoollogo).into(ContentSchoolLogo);
                Picasso.get().load(schoolimage).into(ContentSchoolImage);

                DisplaySchoolContent(schoolname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        SchoolContentSearchRef = FirebaseDatabase.getInstance().getReference().child("SchoolList");

        schoolContentSearchrview = (RecyclerView) findViewById(R.id.schoolcontent_recyclerview);
        schoolContentSearchrview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(false);
        schoolContentSearchrview.setLayoutManager(linearLayoutManager);
    }

    public void DisplaySchoolContent(String schoolname){
        Query firebaseSearchQuery = SchoolContentSearchRef.orderByChild("schoolname").startAt(schoolname).endAt(schoolname + "\uf8ff");

        FirebaseRecyclerAdapter<Schools, SchoolContentViewHolder > firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Schools, SchoolContentViewHolder>(
                Schools.class,
                R.layout.content_overview,
                SchoolContentViewHolder.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(SchoolContentViewHolder viewHolder, Schools model, int position) {
                viewHolder.setMaincontentaveragetuitionfee(model.getMaincontentaveragetuitionfee().replace("_b","\n"));
                viewHolder.setMaincontentaboutschool(model.getMaincontentaboutschool().replace("_b","\n"));
                viewHolder.setMaincontentcontactnumber(model.getMaincontentcontactnumber().replace("_b","\n"));
                viewHolder.setMaincontentwebsite(model.getMaincontentwebsite().replace("_b","\n"));
                viewHolder.setMaincontentschoolemail(model.getMaincontentschoolemail().replace("_b","\n"));
                viewHolder.setMaincontentadmissionrequirements(model.getMaincontentadmissionrequirements().replace("_b","\n"));
                viewHolder.setMaincontentadmissionprocedures(model.getMaincontentadmissionprocedures().replace("_b","\n"));
                viewHolder.setMaincontentadmissionfee(model.getMaincontentadmissionfee().replace("_b","\n"));
                viewHolder.setMaincontentfeaturedfacilities(getApplicationContext(), model.getMaincontentfeaturedfacilities());
                viewHolder.setMaincontentdresscode(getApplicationContext(), model.getMaincontentdresscode());
                viewHolder.setMaincontentacadtracklist(model.getMaincontentacadtracklist().replace("_b","\n"));
                viewHolder.setMaincontenttvltracklist(model.getMaincontenttvltracklist().replace("_b","\n"));
                viewHolder.setMaincontentsportstracklist(model.getMaincontentsportstracklist().replace("_b","\n"));
                viewHolder.setMaincontentartsanddesigntracklist(model.getMaincontentartsanddesigntracklist().replace("_b","\n"));

            }

        };
        schoolContentSearchrview.setAdapter(firebaseRecyclerAdapter);
    }

    public static class SchoolContentViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public SchoolContentViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }

        public void setMaincontentaveragetuitionfee(String setMaincontentaveragetuitionfee) {
            TextView tuitionfield = (TextView) mView.findViewById(R.id.content_averagetutionfeevalue);
            tuitionfield.setText(setMaincontentaveragetuitionfee);
        }


        public void setMaincontentaboutschool(String setMaincontentaboutschool) {
            TextView aboutschoolfield = (TextView) mView.findViewById(R.id.content_aboutschooldescription);
            aboutschoolfield.setText(setMaincontentaboutschool);
        }

        public void setMaincontentfeaturedfacilities(Context ctx1, String setMaincontentfeaturedfacilities) {
            ImageView featuredfacilitiesimage = (ImageView) mView.findViewById(R.id.content_featuredfacilitiesimages);
            Picasso.get().load(setMaincontentfeaturedfacilities).into(featuredfacilitiesimage);
        }

        public void setMaincontentdresscode(Context ctx, String setMaincontentdresscode) {
            ImageView dresscodeimage = (ImageView) mView.findViewById(R.id.content_dresscodeimages);
            Picasso.get().load(setMaincontentdresscode).into(dresscodeimage);
        }

        public void setMaincontentcontactnumber(String setMaincontentcontactnumber) {
            TextView contactnumberfield = (TextView) mView.findViewById(R.id.content_telephonenumberdetail);
            contactnumberfield.setText(setMaincontentcontactnumber);
        }

        public void setMaincontentwebsite(String setMaincontentwebsite) {
            TextView websitefield = (TextView) mView.findViewById(R.id.content_websitedetail);
            websitefield.setText(setMaincontentwebsite);
        }

        public void setMaincontentschoolemail(String setMaincontentschoolemail) {
            TextView schoolemailfield = (TextView) mView.findViewById(R.id.content_emaildetail);
            schoolemailfield.setText(setMaincontentschoolemail);
        }

        public void setMaincontentadmissionrequirements(String setMaincontentadmissionrequirements) {
            TextView admissionfield = (TextView) mView.findViewById(R.id.content_requirementslist);
            admissionfield.setText(setMaincontentadmissionrequirements);
        }

        public void setMaincontentadmissionprocedures(String setMaincontentadmissionprocedures) {
            TextView admissionprocedurefield = (TextView) mView.findViewById(R.id.content_procedurelist);
            admissionprocedurefield.setText(setMaincontentadmissionprocedures);
        }

        public void setMaincontentadmissionfee(String setMaincontentadmissionfee) {
            TextView admissionfeefield = (TextView) mView.findViewById(R.id.content_testfeevalue);
            admissionfeefield.setText(setMaincontentadmissionfee);
        }

        public void setMaincontentacadtracklist(String setMaincontentacadtracklist) {
            TextView acadtrackfield = (TextView) mView.findViewById(R.id.content_acadtrackcontent);
            acadtrackfield.setText(setMaincontentacadtracklist);
        }

        public void setMaincontenttvltracklist(String setMaincontenttvltracklist) {
            TextView tvltrackfield = (TextView) mView.findViewById(R.id.content_tvltrackcontent);
            tvltrackfield.setText(setMaincontenttvltracklist);
        }

        public void setMaincontentsportstracklist(String setMaincontentsportstracklist) {
            TextView sportstrackfield = (TextView) mView.findViewById(R.id.content_sporttrackcontent);
            sportstrackfield.setText(setMaincontentsportstracklist);
        }

        public void setMaincontentartsanddesigntracklist(String setMaincontentartsanddesigntracklist) {
            TextView artsanddesigntrackfield = (TextView) mView.findViewById(R.id.content_artsanddesigntrackcontent);
            artsanddesigntrackfield.setText(setMaincontentartsanddesigntracklist);
        }
    }
}
