package com.example.abodi.wshalghada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view=  inflater.inflate(R.layout.fragment_home, container, false);
        getFragmentManager().beginTransaction().replace(R.id.frame_popular,new PopularList()).commit();
       getFragmentManager().beginTransaction().replace(R.id.frame_recom,new RecommendedList()).commit();
        getFragmentManager().beginTransaction().replace(R.id.frame_fav,new FavoriteList()).commit();


        return view;
    }

}
