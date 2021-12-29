package com.example.testapp2;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;


public class WriteSQL extends AsyncTask<String, String, String> {
    String tempStr = "";//変化がないとき
    StringBuilder jsonInput = new StringBuilder();//JSON文字列を作る
    String JSONerror = "JSONerror";//正しくJSON形式になっていないとき
    String connectionerror = "connectionerror";//通信に失敗したとき
    JSONArray Jarray;
    Activity activity;

    public WriteSQL(Activity activity) {
        this.activity = activity;//メインのアクティビティを代入
    }

    @Override
    protected String doInBackground (String... params) {//ピリオド三つ：可変長引数 0個以上の引数をとれる
        //SQL文をphpで実行
        String SQLCode = params[0];//引数からSQL文をSQLCodeに格納

        //APIへ接続
        try {
            URL url = new URL("http://10.0.2.2/mysqltest/read.php");//接続先のURL指定
            URLConnection uc = url.openConnection();//接続オブジェクトを生成
            uc.setDoOutput(true); //POST可能に
            // 以下、ヘッダを設定
            uc.setRequestProperty("User-Agent", "Sample-Agent");
            uc.setRequestProperty("Accept-Language", "ja");
            // 以下、パラメータを送信
            OutputStream os = uc.getOutputStream();//接続への書き込みを行う出力ストリームの準備
            String data = "SQLCode=" + SQLCode; // 送信するSQL文 =に続けて一つの文字列として
            PrintStream ps = new PrintStream(os);//出力ストリームを生成
            ps.print(data);//データを送信先に出力
            ps.close();//出力ストリームを閉じる
            //POSTした結果を取得
            InputStream is = uc.getInputStream();//入力ストリームの生成
            BufferedReader br = new BufferedReader(new InputStreamReader(is));//入力ストリームからバッファリング
            String line;//一度文字列に移す
            while ((line = br.readLine()) != null) {
                jsonInput.append(line);//出力からJSON文字列を作成
            }
            br.close();//入力ストリームを閉じる

            //JSON形式から必要なデータを取り出す
            try {
                Jarray = new JSONArray(jsonInput.toString());
                //jsonInputがStringBuilderクラスなので文字列に変換、変換後JSONに
                for (int i = 0; i < Jarray.length(); i++) {
                    tempStr += Jarray.getJSONObject(i).getString("testchar");
                    //元のデータベースのi行目のtestchar属性の値を抜き出す
                    tempStr += "\n";
                }
            } catch(JSONException e){
                e.printStackTrace();
                return JSONerror;//適切な形で受け取れなかった時の出力
            }
            return tempStr;
        } catch(IOException e){
            e.printStackTrace();
            return connectionerror;//通信がうまくいかなかったときの出力
        }

    }

    @Override
    protected void onPostExecute(String str) {
        TextView tv = (TextView)activity.findViewById(R.id.textview1);
        tv.setText(str);//画面に出力
    }


}