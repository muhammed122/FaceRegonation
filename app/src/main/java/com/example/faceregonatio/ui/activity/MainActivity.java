package com.example.faceregonatio.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.example.faceregonatio.R;
import com.example.faceregonatio.ui.adapter.MyFragmentAdapter;
import com.example.faceregonatio.databinding.ActivityMainBinding;
import com.example.faceregonatio.ui.activity.login.Login;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    MyFragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
       binding.setLifecycleOwner(this);

       intiTabLayout();


    }

    protected void intiTabLayout(){
        adapter=new MyFragmentAdapter(getSupportFragmentManager(),1,binding.tabLayout.getTabCount());
        binding.container.setAdapter(adapter);
        binding.container.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.container.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.logout)
        {
            userLogout();
        }

        return super.onOptionsItemSelected(item);
    }

    private void userLogout(){
        SharedPreferences preferences =getApplication().getSharedPreferences("local_file", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        boolean flag= preferences.getBoolean("login",false);
        if (flag) {
            editor.putString("email", "");
            editor.putString("password", "");
            editor.putBoolean("login", false);
            editor.apply();
        }

        startActivity( new Intent(MainActivity.this, Login.class));
        finish();
    }

}
