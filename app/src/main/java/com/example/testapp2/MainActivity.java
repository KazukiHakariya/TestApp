package com.example.testapp2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

/* activity screen transition
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.send_button);

        sendButton.setOnClickListener( v -> {
            Intent intent = new Intent(getApplication(), SubActivity.class);
            startActivity(intent);
        });
    }
}
 */
/*
// fragment screen transition
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setScreenMain();
    }
    private void setScreenMain() {
        setContentView(R.layout.activity_main);

        Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(v -> setScreenSub());
    }
    private void setScreenSub() {
        setContentView(R.layout.activity_sub);

        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(v -> setScreenMain());
    }
}
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.databasetest);

        // Asyncタスククラスのインスタンスを作成し、実行する
        TaskDbConnect task = new TaskDbConnect(MainActivity.this);
        task.execute();
    }
}