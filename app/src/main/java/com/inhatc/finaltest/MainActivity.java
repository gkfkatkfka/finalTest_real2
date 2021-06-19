/*
 * 파일 이름 : MainActivity.java
 * 파일 작성자 : 201944058 강하람
 * 목적 : 로그인
 * 사용 DB : sqlite
 * */
package com.inhatc.finaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // 버튼 정의
    private Button btnLogin;
    private Button btnJoin;

    // txt 정의
    private TextInputEditText loginID;
    private TextInputEditText loginPW;

    // InputTextLayout 
    private TextInputLayout inputEmail;
    private TextInputLayout inputPW;

    private String email;
    private String pw;

    // DB 연동
    private String DBNAME="user";
    SQLiteDatabase myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 연결
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnJoin = (Button) findViewById(R.id.btnJoin);

        // 텍스트 연결
        loginID = (TextInputEditText) findViewById(R.id.loginID);
        loginPW = (TextInputEditText) findViewById(R.id.logonPW);

        // textinputlayout 연결
        inputEmail = (TextInputLayout) findViewById(R.id.textInputLayout3);
        inputPW = (TextInputLayout) findViewById(R.id.textInputLayout4);

        // 이벤트 핸들러 작성
        btnLogin.setOnClickListener(this);
        btnJoin.setOnClickListener(this);

        /*DB 관련*/
        // 디비 생성
        try {
            myDB=this.openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);
            Toast.makeText(this,"데이터베이스 생성", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onClick(View v) {
        // 글씨 뽑아내기
        email = loginID.getText().toString();
        pw = loginPW.getText().toString();

        if (v == btnJoin) { // join 화면
            Intent joinIntent = new Intent(MainActivity.this, JoinActivity.class);
            startActivity(joinIntent);
        } else if (v == btnLogin) { // login 화면

            check(email, pw);
            if(inputEmail.getError()==null&&inputPW.getError()==null){ // 공백이 없을 경우
                int result=login(email,pw);
                if (result==-1){
                    Toast.makeText(this,"존재하지 않는 아이디입니다", Toast.LENGTH_LONG).show();
                }else if(result==0){ // 아이디 있는데 비밀번호 잘못 입력
                    Toast.makeText(this,"비밀번호가 틀렸습니다", Toast.LENGTH_LONG).show();
                }else if(result==1){ // 로그인 잘 되는 경우
                    Toast.makeText(this,"로그인 성공하였습니다", Toast.LENGTH_LONG).show();
                    Intent tabIntent = new Intent(MainActivity.this, TabActivity.class);
                    tabIntent.putExtra("email", email);
                    startActivity(tabIntent);
                    myDB.close();
                }
            }


        }
    }

    private int login(String email, String pw) {
        // 1. select email from user where email = email(함수);
        // 2. 계속 불러들이면서 count하는데 만약 0 이면 아이디 없음

        // 이메일 중복 검사(1,2 단계)
        String countQuery = "SELECT email FROM " + DBNAME + " where email='" + email + "'";
        Cursor cursor = myDB.rawQuery(countQuery, null);

        if (cursor.getCount() == 0) { // 아이디가 존재하지 않음
            return -1; // 아이디가 존재하지 않음
        }

        // 3. 만약 0이 아니라면 여튼 아이디는 있다는 얘끼
        // 4. 가져온 데이터의 비밀번호랑 현재 변수 비밀번호 비교
        String pwQuery = "SELECT password FROM " + DBNAME + " where email='" + email + "'";
        cursor=myDB.rawQuery(pwQuery,null);

        cursor.moveToFirst();
        if (pw.equals(cursor.getString(0))) {// 비밀번호가 같으면
            cursor.close();
            return 1;
        } else { // 아이디는 있는데 비밀번호가 잘못 입력 된 경우
            cursor.close();
            return 0;
        }

        // 5-1. 만약 같다면 그건 맞는 이메일 맞는 비번 => 로그인 성공!
        // 5-2. 만약 다르다면 그건 맞는 이메일 틀린 비번 => 로그인 실패!
        // 6. 디비 꼭꼭 닫아주기

    }

    private void check(String id, String pw) {

        if(id.length()==0){
            inputEmail.setError("필수입력항목입니다");
        } else{
            inputEmail.setError(null);
        }

        if(pw.length()==0){
            inputPW.setError("필수입력항목입니다");
        } else{
            inputPW.setError(null);
        }

    }
}
