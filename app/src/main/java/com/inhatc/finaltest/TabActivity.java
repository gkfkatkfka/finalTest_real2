package com.inhatc.finaltest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class TabActivity extends AppCompatActivity{
    String email;

    private FloatingActionMenu fam;
    private FloatingActionButton fabRead,  fabWrite;

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

        fabRead = (FloatingActionButton) findViewById(R.id.readTab);
        fabWrite = (FloatingActionButton) findViewById(R.id.writeTab);
        fam = (FloatingActionMenu) findViewById(R.id.fabMenu);

        //handling each floating action button clicked
        fabRead.setOnClickListener(onButtonClick());
        fabWrite.setOnClickListener(onButtonClick());

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
                if (view == fabRead) {
                    Intent readIntent=new Intent(TabActivity.this,ReadBookActivity.class);
                    readIntent.putExtra("email",email);
                    startActivity(readIntent);
                } else {
                    Intent writeIntent=new Intent(TabActivity.this,WriteBookActivity.class);
                    writeIntent.putExtra("email",email);
                    startActivity(writeIntent);
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