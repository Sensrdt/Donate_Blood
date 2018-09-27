package com.example.sridip.donate_blood;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourFragment extends Fragment {


    private RecyclerView recyclerView;
    private DatabaseReference donorReference, getUserData,databaseReference;
    private FirebaseAuth mAuth;

    public View view;

    String BloodGroup, BloodType;


    public YourFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_your, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.Recycle_view);



        mAuth = FirebaseAuth.getInstance();

        String cur_uid=mAuth.getUid();

        getUserData= FirebaseDatabase.getInstance().getReference().child("Users").child(cur_uid);

        getUserData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                BloodGroup=(dataSnapshot.child("userBlood")).getValue().toString();
                BloodType=(dataSnapshot.child("userType")).getValue().toString();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                try{
                    throw databaseError.toException();
                }
                catch (Exception e){
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }
        });


        donorReference= FirebaseDatabase.getInstance().getReference().child("Users");
        donorReference.keepSynced(true);
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



        // Inflate the layout for this fragment

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<all_donor,DonarFragment.all_donarViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<all_donor, DonarFragment.all_donarViewHolder>
                        (
                                all_donor.class,
                                R.layout.mytab_layout,
                                DonarFragment.all_donarViewHolder.class,
                                donorReference
                        ) {
                    @Override
                    protected void populateViewHolder(DonarFragment.all_donarViewHolder viewHolder, final all_donor model, int position) {


                        boolean a;
                        if  (model.getUserType().equals(BloodType) ||  (model.getUserType().equals("Not Now"))) {
                            a = false;
                        }

                        else{
                            a=true;
                        }





                        if(a &&( model.getUserBlood().equals(BloodGroup))){


                            view.setVisibility(View.VISIBLE);

                            viewHolder.setUserName(model.getUserName());
                            viewHolder.setUserBlood(model.getUserBlood());
                            viewHolder.setUserPh(model.getUserPh());
                            viewHolder.setUserType(model.getUserType());




                        }

                        else {

                            viewHolder.Layout_hide();


                        }
                        viewHolder.call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:"+model.getUserPh()));
                                startActivity(intent);

                            }
                        });



                    }


                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class all_donarViewHolder extends RecyclerView.ViewHolder{

        View mView;
        Button call;
        private LinearLayout linearLayout;
        LinearLayout.LayoutParams params;
        public all_donarViewHolder(View itemView) {
            super(itemView);
            mView=itemView;

            this.linearLayout = linearLayout;
            linearLayout=(LinearLayout)itemView.findViewById(R.id.linear);
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            call=(Button)mView.findViewById(R.id.call);

        }
        public void setUserType(String userType) {

        }

        public void setUserName(String userName) {

            TextView name=(TextView)mView.findViewById(R.id.donor_name);

            name.setText(userName);

        }

        public void setUserBlood(String userBlood){
            TextView blood=(TextView)mView.findViewById(R.id.donor_blood);
            blood.setText(userBlood);
        }

        public  void setUserPh(String userPh) {
            TextView ph=(TextView)mView.findViewById(R.id.donor_phone_no);
            ph.setText(userPh);
        }


        public void types(String s){
            params.height=0;
            params.width=0;
            TextView t=(TextView)mView.findViewById(R.id.donor_types);
            t.setText(s);
        }

        public void Layout_hide(){

            params.height=0;
            params.width=0;
            linearLayout.setLayoutParams(params);


        }
    }
}

