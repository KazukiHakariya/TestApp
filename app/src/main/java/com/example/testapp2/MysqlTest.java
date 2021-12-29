package com.example.testapp2;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MysqlTest extends AppCompatActivity {
    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WriteSQL task = new WriteSQL(activity);//activityを渡す
        task.execute("SELECT * FROM user");//実行するSQL文を渡す

    }

}