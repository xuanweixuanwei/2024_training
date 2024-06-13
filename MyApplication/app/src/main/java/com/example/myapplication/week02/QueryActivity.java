package com.example.myapplication.week02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityQueryBinding;
import com.example.myapplication.sqlitedb.DatabaseHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryActivity extends AppCompatActivity {

    public static final String TAG = "QueryActivity";
    private ActivityQueryBinding viewBinding;
    private Button btnQuery;
    private Button btnShowDB;
    private View.OnClickListener listener;

    private final OkHttpClient mClient = new OkHttpClient();
    private Gson gson = new Gson();

    private DatabaseHelper databaseHelper;
//    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityQueryBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        initLayout();
        initListener();
        openDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseHelper.closeLink();
    }

    private void initLayout() {
        btnQuery = viewBinding.btnGetRequest;
        btnShowDB = viewBinding.btnShowDBData;
    }

    private void initListener() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btnGetRequest:
                        query();
                        break;
                    case R.id.btnShowDBData:
                        showData();
                        break;
                    default:
                        break;
                }
            }
        };

        btnQuery.setOnClickListener(listener);
        btnShowDB.setOnClickListener(listener);
    }

    private void showData() {
//        将数据填充到listView
        readSQLiteDB();
//        Log.e(TAG, DatabaseHelper.COLUMN_USER_ID+post.getUserId() );
//        Log.e(TAG, DatabaseHelper.COLUMN_ID+post.getId() );
//        Log.e(TAG, DatabaseHelper.COLUMN_TITLE+post.getTitle() );
//        Log.e(TAG, DatabaseHelper.COLUMN_BODY+post.getBody() );
    }

    private void query() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();
                builder.url("https://jsonplaceholder.typicode.com/posts/3");
                Request request = builder.build();
                Log.d(TAG, "run: " + request);
                Call call = mClient.newCall(request);
                try {
                    Response response = call.execute();
                    if (response.isSuccessful()) {
//                        改逻辑为顺序的那种
                        final String responseStr = response.body() != null ?
                                response.body().string()
                                : "null";
                        if (responseStr.equals("null")) {
                            Toast.makeText(QueryActivity.this, "响应体为空，未解析到数据",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            databaseHelper.openWriteLink();
                            if (isJsonObject(responseStr)) {
                                Post post = gson.fromJson(responseStr, Post.class); // 假设Post
                                // 是一个对应JSON
                                databaseHelper.insert(post);
                            } else {
                                TypeToken<List<Post>> token = new TypeToken<List<Post>>() {
                                };
                                List<Post> posts = gson.fromJson(responseStr, token.getType());
                                databaseHelper.insert(posts);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();
    }

    public boolean isJsonArray(String jsonString) {
        return jsonString.trim().startsWith("[") && jsonString.trim().endsWith("]");
    }

    public boolean isJsonObject(String jsonString) {
        return jsonString.trim().startsWith("{") && jsonString.trim().endsWith("}");
    }

    private void openDB() {
//        不要忘记这里的version
        databaseHelper = DatabaseHelper.getInstance(this, 7);
        databaseHelper.openWriteLink();
    }

    private void testWriteDB(){
        Post post = new Post();
        post.setId(100);
        post.setUserId(100);
        post.setTitle("title");
        post.setBody("body");
        databaseHelper.openWriteLink();
        databaseHelper.insert(post);
    }

    private void readSQLiteDB() {
        if (databaseHelper == null) {
            Toast.makeText(QueryActivity.this, "数据库连接为空", Toast.LENGTH_SHORT).show();
            return;
        }
        // 执行数据库帮助器的查询操作
        List<Post> posts = databaseHelper.queryAll();
        String desc = String.format("数据库查询到%d条记录，详情如下：", posts.size());
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            desc = String.format("%s\n第%d条记录信息如下：", desc, i + 1);
            desc = String.format("%s\n　POST USER ID 为%d", desc, post.getUserId());
            desc = String.format("%s\n　POST ID为%d", desc, post.getId());
            desc = String.format("%s\n　TITLE 为%s", desc, post.getTitle());
            desc = String.format("%s\n　内容为%s", desc, post.getBody());

        }
        if (posts.size() <= 0) {
            desc = "数据库查询到的记录为空";
        }
        Toast.makeText(QueryActivity.this, desc, Toast.LENGTH_SHORT).show();
    }
}