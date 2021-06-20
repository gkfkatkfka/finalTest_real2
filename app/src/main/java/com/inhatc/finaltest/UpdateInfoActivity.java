package com.inhatc.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class UpdateInfoActivity extends AppCompatActivity implements View.OnClickListener{
    // 버튼
    private ImageButton btnUpdateBefore;
    private Button btnUpdateInfo;

    // 연결
    private TextInputEditText updatePW;
    private TextInputEditText updateCheckPW;
    private TextInputEditText updateName;

    // TextInputLayout
    private TextInputLayout inputPW;
    private TextInputLayout inputCheckPW;
    private TextInputLayout inputName;

    // 문자 변수
    private String PW;
    private String checkPW;
    private String name;

    // 외부에서 들어와야하는 이메일
    private String email;

    // DB 연동
    private String DBNAME="user";
    SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        /*DB 관련*/
        // 디비 생성
        try {
            myDB=this.openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);
            Toast.makeText(this,"데이터베이스 생성", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

        btnUpdateBefore= (ImageButton) findViewById(R.id.btnBeforeUpdate);
        btnUpdateInfo=(Button)findViewById(R.id.btnUpdateInfo);

        updatePW=(TextInputEditText)findViewById(R.id.updatePW);
        updateCheckPW=(TextInputEditText)findViewById(R.id.updateCheckPW);
        updateName=(TextInputEditText)findViewById(R.id.updateName);

        inputPW=(TextInputLayout)findViewById(R.id.textInputLayoutpw);
        inputCheckPW=(TextInputLayout)findViewById(R.id.textInputLayoutCheckPW);
        inputName=(TextInputLayout)findViewById(R.id.textInputLayoutName);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");


        // 이름 설정
        String nameQuery = "SELECT name FROM " + DBNAME + " where email='" + email + "'";
        Cursor cursor=myDB.rawQuery(nameQuery,null);

        cursor.moveToFirst();
        name=cursor.getString(0);
        cursor.close();
        updateName.setText(name);

        btnUpdateBefore.setOnClickListener(this);
         btnUpdateInfo.setOnClickListener(this);

        // 비밀번호와 비밀번호 확인
        updateCheckPW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                PW=updatePW.getText().toString();
                if (!s.toString().equals(PW)) { // 이메일 형식 검사
                    inputCheckPW.setError("비밀번호가 일치하지 않습니다");
                }

                if(s.toString().equals(PW)){
                    inputCheckPW.setError(null);
                    inputCheckPW.setBoxBackgroundColor(Color.argb(50,191,255,0));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        updatePW.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() <8) { // 이메일 형식 검사
                    inputPW.setError("8글자 이상 작성해주세요");
                } else {
                    inputPW.setError(null);
                }
            }
        });
    }

    public void onClick(View v) {
        // 변수 선언
        PW = updatePW.getText().toString().trim();
        checkPW = updateCheckPW.getText().toString().trim();
        name=updateName.getText().toString().trim();

        if(v==btnUpdateBefore){
            myDB.close();
            Intent tabIntent = new Intent(UpdateInfoActivity.this, TabActivity.class);
            startActivity(tabIntent);
            finish();
        }else if(v==btnUpdateInfo){ // 업데이트 눌렀을 때 변화
            // 체크
            boolean chk=check(name,PW,checkPW);
            if(chk){ // 제대로 테스트 마쳤을때
                String updateQuery = "Update "+DBNAME+" Set password = '"+PW+"', name = '"+name+"' " +
                        "where email = '"+email+"'";
                myDB.execSQL(updateQuery);
                myDB.close();
                finish();
                //Intent tabIntent = new Intent(UpdateInfoActivity.this, TabActivity.class);
                //startActivity(tabIntent);
            }
        }
    }

    private boolean check(String name, String pw, String checkPW) {
        // 빈칸검사
        if(pw.equals("")||name.equals("")){
            return false;
        }

        // 비번 일치 검사
        if(!checkPW.equals(pw)){
            return false;
        }

        return true;
    }
}