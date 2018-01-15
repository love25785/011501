package com.wl.a011501;

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