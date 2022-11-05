package com.example.android.lostfound;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.lostfound.Model.Items;
import com.example.android.lostfound.ViewHolder.ItemViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link lost_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lost_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    public lost_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lost_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static lost_fragment newInstance(String param1, String param2) {
        lost_fragment fragment = new lost_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Lost");
//
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(layoutManager);
    }

//
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//
//        FirebaseRecyclerOptions<Items> options =
//                new FirebaseRecyclerOptions.Builder<Items>()
//                        .setQuery(ProductsRef, Items.class)
//                        .build();
//
//        FirebaseRecyclerAdapter<Items,ItemViewHolder>adapter= new FirebaseRecyclerAdapter<Items, ItemViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull @NotNull ItemViewHolder holder, int position, @NonNull @NotNull Items model) {
//                holder.txtAuthorName.setText("Posted By : "+model.getBio());
//                holder.txtDescription.setText(model.getDescription());
//                holder.txtDateTime.setText("Date :" + model.getDate()+" , "+model.getTime());
//
//                Picasso.get().load(model.getImage()).into(holder.imageView);
//            }
//
//            @NonNull
//            @NotNull
//            @Override
//            public ItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_layout, parent, false);
//                ItemViewHolder holder = new ItemViewHolder(view);
//                return holder;
//            }
//        };
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_lost_fragment, container, false);

//        recyclerView = view.findViewById(R.id.recycler_menu_lost);
        return view;
    }
}