
/*
 * 파일 이름 : JoinActivity.java
 * 파일 작성자 : 201944058 강하람
 * 목적 : 회원가입
 * 사용 DB : sqlite
 * */

package com.inhatc.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class JoinActivity extends AppCompatActivity implements View.OnClickListener {


// 버튼 정의
private Button btnJoinBefore;
private Button btnResister;

// TextInputEditText 정의
private TextInputEditText joinPW;
private TextInputEditText joinCheckPW;
private TextInputEditText joinName;
private TextInputEditText joinEmail;

// TextInputLayout
private TextInputLayout inputEmail;
private TextInputLayout inputCheckPW;
private TextInputLayout inputPW;

// 변수 선언
private String PW;
private String checkPW;
private String name;
private String email;

// DB 연동
private String DBNAME="user";
SQLiteDatabase myDB;
ContentValues insertValue;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_join);


/* 버튼 연결*/
btnJoinBefore=(Button)findViewById(R.id.btnJoinBefore);
btnResister=(Button)findViewById(R.id.btnResister);


/* txt 각각 연결*/
joinPW=(TextInputEditText)findViewById(R.id.joinPW);
joinCheckPW=(TextInputEditText)findViewById(R.id.joinCheckPW);
joinName=(TextInputEditText)findViewById(R.id.joinName);
joinEmail=(TextInputEditText)findViewById(R.id.joinEmail);

/* TextInputLayout 연결*/
inputEmail=(TextInputLayout)findViewById(R.id.textInputLayout42);
inputCheckPW=(TextInputLayout)findViewById(R.id.textInputLayout40);
inputPW=(TextInputLayout)findViewById(R.id.textInputLayout39);

/* 핸들러 작동*/
btnResister.setOnClickListener(this);
btnJoinBefore.setOnClickListener(this);

/*DB 관련*/
// 디비 생성
try {
    myDB=this.openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);
    Toast.makeText(this,"데이터베이스 생성", Toast.LENGTH_LONG).show();
}catch (Exception e){
    e.printStackTrace();
}

// 테이블 생성
try {
    myDB.execSQL("Create table if not exists user(" +
            "_id integer primary key autoincrement," +
            "name text not null," +
            "email text not null," +
            "password text not null);");
    Toast.makeText(this,"테이블 생성", Toast.LENGTH_LONG).show();
}catch (Exception e){
    e.printStackTrace();
}

/* 글씨가 입력 될 때마다 실행*/
// 이메일 형식 검사(@ 확인)
joinEmail.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().contains("@")) { // 이메일 형식 검사
            inputEmail.setError("올바르지 않은 형식");
        } else {
            inputEmail.setError(null);
        }
    }
});

    // 비밀번호와 비밀번호 확인
    joinCheckPW.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        PW=joinPW.getText().toString();
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

joinPW.addTextChangedListener(new TextWatcher() {
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

public void onClick(View v){
// 변수 선언
PW=joinPW.getText().toString().trim();
checkPW=joinCheckPW.getText().toString().trim();
name=joinName.getText().toString().trim();
email=joinEmail.getText().toString().trim();


if(v==btnJoinBefore){ // 이전 버튼을 눌렀을 때
    try {
        if(myDB!=null){
            myDB.close();
        }
    }catch (Exception e){
        e.printStackTrace();
    }
    finish();
    // 원래 MainActivity 즉, 로그인 화면으로 가기
}else if(v==btnResister){ // 회원가입 버튼을 눌렀을 때
    boolean chk = check(email,PW,checkPW,name); // 테스트 실행

    if(chk){ // test 결과 참이면
        // 데이터 넣어주고
        insertValue=new ContentValues();
        insertValue.put("email",email);
        insertValue.put("password",PW);
        insertValue.put("name",name);

        // 데이터 insert 해줌
        myDB.insert(DBNAME,null,insertValue);

        // DB 꼭꼭 닫아주기
        try {
            if(myDB!=null){
                myDB.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        // 회원가입 완료 Toast 띄어주기
        Toast.makeText(this,"회원가입 완료되었습니다", Toast.LENGTH_LONG).show();

        // 원래 페이지로 돌아가기
        finish();
    }
    else {
        Toast.makeText(this, "회원가입에 실패하였습니다!", Toast.LENGTH_LONG).show();
    }
}
}

// 회원가입
public boolean check(String email, String pw, String checkPW, String name) {
// 빈칸검사
if(email.equals("")||pw.equals("")||name.equals("")){
    return false;
}

// 이메일 형식 검사
if(!email.contains("@")){
    Toast.makeText(this,"올바른 이메일 형식 입력해주세요", Toast.LENGTH_LONG).show();
    return false;
}

// 비번 일치 검사
if(!checkPW.equals(pw)){
    return false;
}

// 이메일 중복 검사
String countQuery = "SELECT email FROM " + DBNAME + " where email='"+email+"'";
Cursor cursor = myDB.rawQuery(countQuery, null);
cursor.moveToFirst();
if(cursor.getCount()!=0){
    inputEmail.setError("중복된 이메일 존재");
    return false;
}
cursor.close();

// 모든게 다 괜찮으면 true 반환
return true;
}
}
