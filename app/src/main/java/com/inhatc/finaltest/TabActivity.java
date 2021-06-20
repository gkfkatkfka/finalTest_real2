package com.inhatc.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity{
    String email;

    private FloatingActionMenu fam;
    private FloatingActionButton fabEdit,  fabAdd;

    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);


        /*기존의 것*/
        ViewPager vp = findViewById(R.id.viewpager);
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        TabLayout tab = findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images = new ArrayList<>();
        images.add(R.drawable.search);
        images.add(R.drawable.location);
        images.add(R.drawable.info);

        for(int i=0; i<3; i++) {
            tab.getTabAt(i).setIcon(images.get(i));
        }

        /*floating button*/

        fabAdd = (FloatingActionButton) findViewById(R.id.fab2);
        fabEdit = (FloatingActionButton) findViewById(R.id.fab1);
        fam = (FloatingActionMenu) findViewById(R.id.fab_menu);

        //handling each floating action button clicked
        fabEdit.setOnClickListener(onButtonClick());
        fabAdd.setOnClickListener(onButtonClick());

        fam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fam.isOpened()) {
                    fam.close(true);
                }
            }
        });

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
    }

    private View.OnClickListener onButtonClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == fabAdd) {
                    showToast("Button Add clicked");
                } else {
                    showToast("Button Edit clicked");
                }
                fam.close(true);
            }
        };
    }

    public String getData(){
        return email;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}