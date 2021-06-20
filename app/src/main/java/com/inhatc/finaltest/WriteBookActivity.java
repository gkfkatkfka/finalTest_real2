package com.inhatc.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class WriteBookActivity extends AppCompatActivity implements View.OnClickListener{
    // 박스
    private TextInputLayout layoutTitle;
    private TextInputLayout layoutContent;

    // 안에 텍스트
    private TextInputEditText txtTitle;
    private TextInputEditText txtContent;

    // 버튼
    private Button btnInsert;

    // String 변수
    private String title;
    private String content;
    private String email;

    // DB 연동
    private String DBNAME = "book";
    SQLiteDatabase myDB;
    ContentValues insertValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_book);

        layoutTitle=(TextInputLayout) findViewById(R.id.textInputLayoutTitle);
        layoutContent=(TextInputLayout)findViewById(R.id.textInputLayoutContent);

        txtTitle=(TextInputEditText)findViewById(R.id.insertTitle);
        txtContent=(TextInputEditText)findViewById(R.id.insertContent);

        btnInsert=(Button)findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(this);

        Intent callIntent= getIntent();
        email=callIntent.getStringExtra("email");

        txtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 100) { // 이메일 형식 검사
                    layoutContent.setError("100글자 이하 작성해주세요.");
                } else {
                    layoutContent.setError(null);
                }
            }
        });

    }

    public void onClick(View v){
        title=txtTitle.getText().toString();
        content=txtContent.getText().toString();

        if(v==btnInsert) {
            if (email.equals("") || txtTitle.equals("") || txtContent.equals("")) {
                Toast.makeText(this, "빈 값이 존재합니다.", Toast.LENGTH_LONG).show();
            } else {

                /*DB 관련*/
                // 디비 생성
                try {
                    myDB = this.openOrCreateDatabase(DBNAME, MODE_PRIVATE, null);
                    Toast.makeText(this, "데이터베이스 열림", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 테이블 생성
                try {
                    myDB.execSQL("Create table if not exists book(" +
                            "_id integer primary key autoincrement," +
                            "title text not null," +
                            "content text not null," +
                            "email text not null);");
                    Toast.makeText(this, "book 테이블 생성", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 데이터 넣어주고
                insertValue = new ContentValues();
                insertValue.put("email", email);
                insertValue.put("title", title);
                insertValue.put("content", content);

                // 데이터 insert 해줌
                myDB.insert(DBNAME, null, insertValue);

                // DB 꼭꼭 닫아주기
                try {
                    if (myDB != null) {
                        myDB.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 회원가입 완료 Toast 띄어주기
                Toast.makeText(this, "회원가입 완료되었습니다", Toast.LENGTH_LONG).show();

                Intent readIntent=new Intent(WriteBookActivity.this,ReadBookActivity.class);
                readIntent.putExtra("email",email);
                startActivity(readIntent);
                finish();
            }
        }
    }
}