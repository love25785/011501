//volley也能抓圖檔
//一樣先在mainifest開網路權限，及在grandel加入    compile 'com.android.volley:volley:1.1.0'
//一樣四個步驟:一.建立一個quese隊列，二:建立一個Request，三.把Request 加入Queue，四.執行queue，只是第二步驟會有7個引數
//用progrssbar呈現載圖時要轉圈圈等待，載好後出現圖，所以轉圈圈和圖檔在同一個位置要用framelayout
package com.wl.a011501;

import android.app.VoiceInteractor;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity
{
    ProgressBar pb;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        img = (ImageView) findViewById(R.id.imageView);
    }

    public void click1(View v)
    {
        pb.setVisibility(View.VISIBLE);//按下BUTTON先看轉圈圈
        img.setVisibility(View.INVISIBLE);//先看不到圖

        RequestQueue queue= Volley.newRequestQueue(MainActivity.this);
        //↓第二步驟因為是要抓圖檔，所以不是StringRequest，而是ImageRequest，且有7個引數: 1.網址 2.成功做甚麼 3.寬度(0為不設限) 4.高度(0為不設限) 5.擺置位置 6.甚麼色彩 7.失敗怎辦
        ImageRequest request=new ImageRequest
                ("https://5.imimg.com/data5/UH/ND/MY-4431270/red-rose-flower-500x500.jpg",
                        new Response.Listener<Bitmap>()//由於是ImageRequest，第二個引數他的類型會是Bitmap
                        {
                            @Override
                            public void onResponse(Bitmap response)
                            {
                                img.setImageBitmap(response);//setImageBitmap將圖檔放入
                                pb.setVisibility(View.INVISIBLE);//圖載好了，轉圈圈消失
                                img.setVisibility(View.VISIBLE);//秀出圖
                            }
                        },
                        0,
                        0,
                        ImageView.ScaleType.FIT_XY,
                        Bitmap.Config.RGB_565,
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {

                            }
                        }
                );

    }



    ///////////////////////////////////////↓而使用volley在抓String時會出現亂碼，是因為volly是使用ACEII(BIG5) ，而抓的文字編碼如是UTF8會亂碼//////////////////////////////////////////////////

    /////////////////解決辦法: 不用volley的StringRequest，而是自設一個StringRequest:   UTF8StringRequest繼承而是自設一個StringRequest(所以會有其建構式)，在override其內容，其內容備註在底下
    public void click2(View v)
    {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new UTF8StringRequest
                ("https://www.mobile01.com/rss/news.xml",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("NET", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                    }
                });
        queue.add(request);
        queue.start();
    }
}

/*
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import java.io.UnsupportedEncodingException;

public class UTF8StringRequest extends StringRequest
{
    public UTF8StringRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {
        super(url, listener, errorListener);
    }
    ////↑建構式不動
    //////↓override編碼方式
    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        // TODO Auto-generated method stub
        String str = null;
        try
        {
            str = new String(response.data,"utf-8");  ////////////將資料編碼為 utf-8
        } catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Response.success(str, HttpHeaderParser.parseCacheHeaders(response));
    }
}

 */
