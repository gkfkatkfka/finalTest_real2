package com.inhatc.finaltest;
import android.animation.ArgbEvaluator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ReadBookActivity extends AppCompatActivity {
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);
        models = new ArrayList<>();




        // 여기에 디비 가지고 오는 법 배우기기



        models.add(new Model("목적지", "어디로 여행을 갈지 골라보자"));
        models.add(new Model( "숙소", "근처 숙소들을 찾아보고 숙소 가격을 알아보자"));
        models.add(new Model( "장보기", "무엇을 사고 대충 비용이 얼마나 드는지 알아보자"));
        models.add(new Model( "이동수단", "어떤 이동수단을 사용하고 비용이 얼마나 드는지 알아보자"));

        adapter = new Adapter(models,this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 360, 130, 0);
        viewPager.setBackground(getDrawable(R.drawable.background_gra));

    }
}
