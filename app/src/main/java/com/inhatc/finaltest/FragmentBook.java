package com.inhatc.finaltest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentBook#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBook extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String apiURL="https://openapi.naver.com/v1/search/book.json?";
    String query="";
    // String query="안드로이드";
    int start=1;

    private RecyclerView recyclerView;
    private BookAdapter adapter;
    private ArrayList<com.inhatc.finaltest.BookVO> list = new ArrayList<>();

    public FragmentBook() {
        // Required empty public constructor
    }

    public static FragmentBook newInstance(String param1, String param2) {
        FragmentBook fragment = new FragmentBook();
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
        //return inflater.inflate(R.layout.fragment_book, container, false);

        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_book, container, false);
        // xml 가져와서 연결

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list);
        // recyclerView 가져오기

        recyclerView.setHasFixedSize(true);
        adapter = new BookAdapter(getActivity(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // 추가
        //생성
        //array=new ArrayList<BookVO>();

        //생성
        new BookThread().execute();

        TextInputEditText edtsearch= rootView.findViewById(R.id.testBook);



        edtsearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                start = 1;
                list.clear();
                query = edtsearch.getText().toString();
                new BookThread().execute();
                return false;
            }
        });

        return rootView;
    }

    class BookThread extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            return NaverAPI.main(apiURL,query,start);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("결과.........."+s);
            try {
                JSONArray jArray=new JSONObject(s).getJSONArray("items");
                for(int i=0; i<jArray.length(); i++){
                    JSONObject obj=jArray.getJSONObject(i);
                    BookVO vo=new BookVO();
                    //vo.setAuthor(obj.getString("author"));
                    vo.setImage(obj.getString("image"));
                    vo.setPrice(obj.getInt("price"));
                    //vo.setDescription(obj.getString("description"));
                    //vo.setLink(obj.getString("link"));
                    vo.setTitle(obj.getString("title"));
                    list.add(vo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //adapter=new BookAdapter(array,BookActivity.this);
            //list.setAdapter(adapter);
            //list.scrollToPosition(start);
        }
    }
}