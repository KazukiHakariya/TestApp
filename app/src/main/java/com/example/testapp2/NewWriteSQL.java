package com.example.testapp2;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NewWriteSQL {
    String SQLCode;//実行するクエリ
    String SQLAttribute;//参照する属性
    String[] tempStr = new String[30];//実行結果の格納配列

    StringBuilder jsonInput = new StringBuilder();//JSON文字列を作る
    JSONArray Jarray;

    String JSONerror = "JSONerror";//正しくJSON形式になっていないとき
    String connectionerror = "connectionerror";//通信に失敗したとき

    Activity activity;

    public class AsyncRunnable implements Callable<String[]> {
        Handler handler = new Handler(Looper.getMainLooper());

        public String[] call() {

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
                        tempStr[i] = Jarray.getJSONObject(i).optString(SQLAttribute);
                        //元のデータベースのi行目のSQLAttribute属性の値を抜き出す
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                    tempStr[0] = JSONerror;//適切な形で受け取れなかった時の出力
                }
            } catch(IOException e){
                e.printStackTrace();
                tempStr[0] = connectionerror;//通信がうまくいかなかったときの出力
            }

            handler.post(new Runnable() {//UIスレッド上でRunnableの中身を実行
                @Override
                public void run(){
                    onPostExecute(tempStr);//後処理の実行
                }
            });

            return tempStr;
        }
    }

    public String[] execute(String query, String attribute,Activity UIactivity) throws ExecutionException, InterruptedException {
        SQLCode = query;
        SQLAttribute = attribute;
        this.activity = UIactivity;

        onPreExecute();//前処理の実行

        ExecutorService executorService  = Executors.newSingleThreadExecutor();//スレッドの生成
        Future<String[]> result = executorService.submit(new AsyncRunnable());//タスクの実行
        return result.get();
    }

    void onPreExecute() {
        // ここに前処理を記述します
        // 例） プログレスダイアログ表示
    }

    protected void onPostExecute(String... str) {
        //TextView tv = (TextView) activity.findViewById(R.id.textview1);
        //tv.setText(str[0]);//画面に出力
    }
}