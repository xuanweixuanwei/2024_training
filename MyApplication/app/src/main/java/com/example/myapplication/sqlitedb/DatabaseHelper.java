package com.example.myapplication.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.week02.Post;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "MyDatabase.db";
    private static final int DATABASE_VERSION = 7;
    private static DatabaseHelper databaseHelper = null;
    private SQLiteDatabase database = null;

    public static final String TABLE_NAME = "posts";

    public static final String COLUMN_USER_ID = "userid";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_BODY = "body";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private DatabaseHelper(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
    }

    public static DatabaseHelper getInstance(Context context, int version) {
        if (version > 0 && databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context, version);
        } else if (databaseHelper == null) {
            databaseHelper = new DatabaseHelper(context);
        }
        return databaseHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getReadableDatabase();
        }
        return database;
    }


    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (database == null || !database.isOpen()) {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {


        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USER_ID + "INTEGER," +
                COLUMN_TITLE + " VARCHAR, " +
                COLUMN_BODY + " VARCHAR)";
        db.execSQL(createTableQuery);

    }

    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

        db.execSQL(drop_sql);
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USER_ID + " INTEGER," +
                COLUMN_TITLE + " VARCHAR, " +
                COLUMN_BODY + " VARCHAR)";
        Log.e(TAG, "onUpgrade: "+createTableQuery );
        db.execSQL(createTableQuery);
    }

    // ...其他必要的重写方法

    // 往该表添加一条记录
    public long insert(Post post) {

//        String queryCreateSQL = "SELECT sql FROM sqlite_master WHERE type='table' AND name='table_name';";
//        Cursor cursor = database.rawQuery(queryCreateSQL, null);
//        cursor.moveToNext();
//        String cursorString = cursor.getString(0);

        Log.e(TAG, "insert: "+database.getVersion() );

        List<Post> postList = new ArrayList<Post>();
        postList.add(post);
        return insert(postList);
    }

    public long insert(List<Post> posts) {
        long result = -1;
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            List<Post> tempList;
            // 如果存在同id记录，则更新记录
            // 注意条件语句的等号后面要用单引号括起来
            if (post.getId() != null && post.getId() > 0) {
                String condition = String.format("id='%s'", post.getId());
                tempList = query(condition);
                if (tempList.size() > 0) {
                    update(post, condition);
                    result = tempList.get(0).getId();
                    continue;
                }
            }

            // 不存在唯一性重复的记录，则插入新记录
            ContentValues cv = new ContentValues();

            cv.put("id", post.getId());
            cv.put("userid", post.getUserId());
            cv.put("title", post.getTitle());
            cv.put("body", post.getBody());

            // 执行插入记录动作，该语句返回插入记录的行号
            result = database.insert(TABLE_NAME, "", cv);
            if (result == -1) { // 添加成功则返回行号，添加失败则返回-1
                return result;
            }
        }
        return result;
    }

    public List<Post> queryAll() {
        String sql = String.format("select * " +
                "from %s ;", TABLE_NAME);
        Cursor cursor = database.rawQuery(sql, null);
        List<Post> posts = new ArrayList<>();
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Post post = new Post();

            post.setId(cursor.getInt(0));
            post.setUserId(cursor.getInt(1));
            post.setTitle(cursor.getString(2));
            post.setBody(cursor.getString(3));

            posts.add(post);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return posts;
    }

    // 根据指定条件查询记录，并返回结果数据列表
    public List<Post> query(String condition) {
        String sql = String.format("select * " +
                "from %s where %s COLLATE NOCASE;", TABLE_NAME, condition);
        Log.d(TAG, "query sql: " + sql);
        List<Post> posts = new ArrayList<Post>();
        // 执行记录查询动作，该语句返回结果集的游标
        Cursor cursor = database.rawQuery(sql, null);
        // 循环取出游标指向的每条记录
        while (cursor.moveToNext()) {
            Post post = new Post();

            post.setId(cursor.getInt(0));
            post.setUserId(cursor.getInt(1));
            post.setTitle(cursor.getString(2));
            post.setBody(cursor.getString(3));

            posts.add(post);
        }
        cursor.close(); // 查询完毕，关闭数据库游标
        return posts;
    }

    // 根据条件更新指定的表记录
    public int update(Post post, String condition) {
        ContentValues cv = new ContentValues();

        cv.put("id", post.getId());
        cv.put("userid", post.getUserId());
        cv.put("title", post.getTitle());
        cv.put("body", post.getBody());

        // 执行更新记录动作，该语句返回更新的记录数量
        return database.update(TABLE_NAME, cv, condition, null);
    }

    public int update(Post post) {
        // 执行更新记录动作，该语句返回更新的记录数量
        return update(post, "id=" + post.getId());
    }
}