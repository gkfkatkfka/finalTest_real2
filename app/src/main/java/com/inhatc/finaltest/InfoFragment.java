package com.inhatc.finaltest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment implements View.OnClickListener{
    Button btnUpdate;
    Button btnDelete;
    Button btnLogout;

    TextView txtName;

    // 데이터 전달이 안 되어 우선 내가 정의 해둠
    String email;

    // DB 연동
    private String DBNAME="user";
    SQLiteDatabase myDB;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InfoFragment newInstance(String param1, String param2) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        btnDelete=(Button) v.findViewById(R.id.btnDelete);
        btnUpdate=(Button) v.findViewById(R.id.btnUpdateInfo);
        btnLogout=(Button) v.findViewById(R.id.btnLogout);

        txtName=(TextView) v.findViewById(R.id.txtName);

        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        // 액티비티에서 값 넘어오면 여기 수정
        email = "gkfka1@co.kr";

        /*DB 관련*/
        // 디비 생성
        try {
            myDB=getActivity().openOrCreateDatabase(DBNAME,MODE_PRIVATE,null);
            Toast.makeText(getActivity(),"프래그먼트 데이터베이스 생성", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

        return v;
    }

    public void onClick(View v){
        if(v==btnUpdate){ // 정보 수정할 때
            Intent loginIntent=new Intent(getActivity(),UpdateInfoActivity.class);
            startActivity(loginIntent);
            // 여기도 값넘겨주는 거 구현
        }else if(v==btnDelete){ // 회원 탈퇴 할 때
            String deleteQuery = "Delete FROM " + DBNAME + " where email='" + email + "'";
            myDB.execSQL(deleteQuery);
            myDB.close();
            Intent loginIntent=new Intent(getActivity(),MainActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getActivity(),email+"회원탈퇴 완료", Toast.LENGTH_LONG).show();
        }else if(v==btnLogout){ // 로그아웃 할 때
            Intent loginIntent=new Intent(getActivity(),MainActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getActivity(),"로그아웃 완료", Toast.LENGTH_LONG).show();
        }
    }
}