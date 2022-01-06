//受け取ったJSON形式のファイルからデータを取り出す
package com.example.testapp2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class AcquireData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //以下取り出し部分
        String jsonResult = new String();//JSON文字列を作る
        JSONArray Jarray;//json文字列をJSONオブジェクトに直すときに使う
        WriteSQL SQLNewestFunction = new WriteSQL();//SQL文を実行するためのAPIへのアクセス
        //アクセスを行うごとにWriteSQL型のコンストラクタを作成する必要がある

        String data = new String();//

        String query_newest = "SELECT * FROM user";//実行するSQL文
        try {
            jsonResult = SQLNewestFunction.execute(query_newest);//SQL文を実行,結果をJSON形式の文字列として格納
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //JSON形式から必要なデータを取り出す
        //データを取り出さない場合は不要
        try {
            Jarray = new JSONArray(jsonResult);//jsonResultをJson配列に
            data = Jarray.getJSONObject(0).getString("user_id");
            //テーブル中の0行目のデータのuser_id属性の値を抜き出す
            //数字と属性を書き換えることで取り出すデータを選べる
            //複数の行のデータを取得したい場合は数字を変数にしてforループに入れる
            //for (int i = 0; i < Jarray.length(); i++) {
            //実行結果の全行の全属性の値を格納する場合↓
            //    resultStr[i][0] = Jarray.getJSONObject(i).getString("user_id");
            //    resultStr[i][1] = Jarray.getJSONObject(i).getString("account_name");
            //    resultStr[i][2] = Jarray.getJSONObject(i).getString("phone_number");
            //    resultStr[i][3] = Jarray.getJSONObject(i).getString("mail_address");
            //    resultStr[i][4] = Jarray.getJSONObject(i).getString("password");
            //    resultStr[i][5] = Jarray.getJSONObject(i).getString("account_type");
            //}
        } catch(JSONException e){
            //JSONArray型から正しくデータが抜き出せなかった場合の処理
            e.printStackTrace();
        }
    }
}