package com.inhatc.finaltest;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class ReadBookActivity extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;

    ImageButton btnBefore;

    // DB 연동
    private String DBNAME = "book";
    SQLiteDatabase myDB;
    ContentValues insertValue;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_book);

        btnBefore=(ImageButton)findViewById(R.id.btnBefore);
        models = new ArrayList<>();

        btnBefore.setOnClickListener(this);
        // 이메일 가져오기
        Intent callIntent= getIntent();
        email=callIntent.getStringExtra("email");


        // 디비 가져오기
        /*DB 관련*/
        // 디비 생성
        try {
            myDB=this.openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);
            //Toast.makeText(this,"데이터베이스 생성", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

        // 여기에 디비 가지고 오는 법 배우기기
        String bookQuery = "select title,content from "+DBNAME+ " where email = '"+email+"'";
        Cursor cursor = myDB.rawQuery(bookQuery,null);
        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
            models.add(new Model(cursor.getString(0), cursor.getString(1)));
        }

        if(cursor.getCount()!=0){
            while(cursor.moveToNext()){
                //num 행은 가장 첫번째에 있으니 0번이 되고, name은 1번
                models.add(new Model(cursor.getString(0), cursor.getString(1)));
            }
        }

        cursor.close();
        myDB.close();
        models.add(new Model("독서의 행복", "읽었던 책에서 다시 한 번 기쁨을 찾아보는 거 어떠신가요?"));

        adapter = new Adapter(models,this);

        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130, 300, 130, 0);
        viewPager.setBackground(getDrawable(R.drawable.background_gra));
    }

    public void onClick(View v){
        if(v==btnBefore){
            finish();
        }
    }
}
