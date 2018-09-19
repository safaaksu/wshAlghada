package com.example.abodi.wshalghada;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

private boolean loadFragment(Fragment fr){

    if(fr!=null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fr).commit();

        return true;

    }
return false;

}
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    Fragment fragment=null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   fragment= new HomeFragment();

                    break;
                    case R.id.navigation_favorite:
                    fragment= new FavoriteFragment();

                        break;
                case R.id.navigation_search:
                    fragment=new SearchFragment();
                    break;
                case R.id.navigation_profile:
                    fragment=new ProfileFragment();
                    break;

            }
            return loadFragment(fragment);
        }


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
    }



}
