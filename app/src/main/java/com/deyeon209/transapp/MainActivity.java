package com.deyeon209.transapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.deyeon209.transapp.config.Config;
import com.deyeon209.transapp.model.Trans;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    RadioGroup radioGroup;
    EditText editText;
    Button button;
    TextView textResult;

    ArrayList<Trans> transList = new ArrayList<>();
    String text;

    String result;



//    public ActivityResultLauncher<Intent> launcher =
//            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                    new ActivityResultCallback<ActivityResult>() {
//                        @Override
//                        public void onActivityResult(ActivityResult result) {
//                            // 액티비티를 실행한 후, 이 액티비티를
//                            // 돌아왔을때 할 일을 여기에 작성
//                            Log.i("POST_SAVE","done");
//                            // AddActivity가 넘겨준
//                            // Employee 객체를 받아서
//                            // 리스트를 받아서
//                            // 화면에 보여준다.
//                            if (result.getResultCode() == AddActivity.SAVE){
//                                Post post = (Post) result.getData().getSerializableExtra("post");
//                                postList.add(post);
//
//                                adapter.notifyDataSetChanged();
//
//
//                            }else if (result.getResultCode() == editActivity.EDIT) {
//                                Log.i("POST_SAVE","done");
//                                Post post = (Post) result.getData().getSerializableExtra("post");
//                                int index = result.getData().getIntExtra("index",-1);
//                                postList.set(index,post);
//
//                                adapter.notifyDataSetChanged();
//
//
//                            }
//                        }
//                    });

    final static String URL ="https://openapi.naver.com/v1/papago/n2mt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        textResult = findViewById(R.id.textResult);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = editText.getText().toString().trim();

                if(text.isEmpty()){
                    return;
                }

                int radioBtnId = radioGroup.getCheckedRadioButtonId();
                String target;
                if(radioBtnId == R.id.radiobtn1 ){
                    target = "en";

                }else if(radioBtnId == R.id.radiobtn2){
                    target = "zh-CN";
                }else if(radioBtnId == R.id.radiobtn3){
                    target = "zh-TW";
                }else if(radioBtnId == R.id.radiobtn4){
                    target = "th";
                }else{
                    Toast.makeText(MainActivity.this, "언어를 선택하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 파파고 API호출
                String source = "ko";

                JSONObject body = new JSONObject();
                try {
                    body.put("source",source);
                    body.put("target",target);
                    body.put("text",text);



                } catch (JSONException e) {
                    e.printStackTrace();

                    Toast.makeText(MainActivity.this, "파싱 에러", Toast.LENGTH_SHORT).show();

                    return;
                }


                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        URL,
                        body,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("PAPAGO_APP",response.toString());

                                try {

                                    result = response.getJSONObject("message")
                                            .getJSONObject("result")
                                            .getString("translatedText");

                                    textResult.setText(result);

                                    // 객체 생성한다.
                                    Trans trans = new Trans(text,result,target);

                                    // 어레이 리스트에 넣어준다.
                                    transList.add(0,trans);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    return;
                                }



                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("PAPAGO_APP",error.toString());

                            }
                        }

                ){
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();

                        headers.put("X-Naver-Client-Id", Config.NAVER_CLIENT_ID);
                        headers.put("X-Naver-Client-Secret",Config.NAVER_CLIENT_SECRET);
                        return headers;

                    }
                };
                queue.add(request);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId= item.getItemId();

        if(itemId == R.id.menuAdd){

            // AddActivity 실행하는 코드
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                intent.putExtra("transList",transList);
                startActivity(intent);
            Log.i("MENU",intent.toString());

        }

        return super.onOptionsItemSelected(item);
    }

//    imageView.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            String input = text;
//            String output = result;
//
//            Trans trans = new Trans(input,output);
//
//            Intent intent = new Intent(MainActivity.this,ListActivity.class);
//            launcher.launch(intent);
//            // 돌려줄때는 startActitvity 가 아니라, setResult 함수 이용
//
//
//
//
//
//        }
//    });
}