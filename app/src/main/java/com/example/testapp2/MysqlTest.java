package com.example.testapp2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class MysqlTest extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] result = new String[30];
        NewWriteSQL task = new NewWriteSQL();//activityを渡す
        try {
            result = task.execute("SELECT * FROM user", "user_id", this);//実行するSQL文を渡す
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        TextView tv = this.findViewById(R.id.textview1);
        tv.setText(result[0]);
    }

}