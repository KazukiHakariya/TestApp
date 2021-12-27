package com.example.testapp2;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

class TaskDbConnect extends AsyncTask<Void, Void, String>{
    // この中をこれから記述
    Activity activity = null;

    public TaskDbConnect(Activity act){
        activity = act;
    }

    @Override
    protected String doInBackground(Void... params) {
        String text1 = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://database-2.cjsruygswh3h.us-east-2.rds.amazonaws.com/allernin", "admin", "HAM2021softwere1210");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from user");

            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String phonenumber = rs.getString(3);
                String pass = rs.getString(4);
                text1 += id + " " + name + " " + phonenumber + " " + pass + " " + "\r\n";
            }

        } catch (Exception e) {
            text1 = e.getMessage();
        }

        return text1;
    }
    protected void onPostExecute(String result){
        TextView tv = (TextView)activity.findViewById(R.id.textview1);
        tv.setText(result);
    }


}