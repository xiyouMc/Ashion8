package com.android.hui.ashion.util;

import android.app.ActivityManager;
import android.content.Context;

import com.android.hui.ashion.tool.MobileTools;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by litonghui on 2015/4/7.
 */
public class NetworkRequest {
    private static RequestQueue mRequestQueue;
    private static ImageLoader mImageLoader;

    public static void init(Context context) {
        mRequestQueue = mRequestQueue == null ? Volley.newRequestQueue(context) : mRequestQueue;
        mImageLoader = mImageLoader == null ? newImageLoder(context) : mImageLoader;
    }

    public static ImageLoader newImageLoder(final Context context) {
        final int memoryClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

        if (memoryClass <= 16) { // 运用可用内存的1/32作为图像缓存
            mImageLoader = new ImageLoader(mRequestQueue,
                    new BitmapLruCache(1024 * 1024 * memoryClass / 32));
        } else {
            mImageLoader = new ImageLoader(mRequestQueue,
                    new BitmapLruCache(1024 * 1024 * memoryClass / 18));
        }
        return mImageLoader;
    }

    public static RequestQueue getRequestQueue() {
        return mRequestQueue = mRequestQueue == null ? Volley.newRequestQueue(MobileTools.getInstance()) : mRequestQueue;
    }

    public static ImageLoader getImageLoader() {
        return mImageLoader = mImageLoader == null ? newImageLoder(MobileTools.getInstance()) : mImageLoader;
    }

    public static Request<String> get(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue();
        return queue.add(new StringRequest(url, listener, errorListener));
    }

    /**
     * 构造请求模板 ，默认编码
     */
    public static <T> Request<T> get(String url, Class<T> c,
                                     Response.Listener<T> listener, Response.ErrorListener errorListener) {
        return get(url, c, listener, errorListener, true);
    }

    /**
     * 构造请求模板，是否编码
     */
    public static <T> Request<T> get(String url, Class<T> c,
                                     Response.Listener<T> listener,
                                     Response.ErrorListener errorListener,
                                     boolean needUrlDecode) {
        RequestQueue queue = getRequestQueue();
        GsonRequest<T> request = new GsonRequest<T>(url, c, listener, errorListener,
                needUrlDecode);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, 2.f));
        return queue.add(request);
    }

    /**
     * 构造请求模板，是否缓存
     */
    public static <T> Request<T> get(boolean shouldCache,
                                     String url,
                                     Class<T> c,
                                     Response.Listener<T> listener,
                                     Response.ErrorListener errorListener
    ) {
        RequestQueue queue = getRequestQueue();
        GsonRequest<T> request = new GsonRequest<T>(url, c, listener, errorListener, true);
        request.setShouldCache(shouldCache);
        request.setRetryPolicy(new DefaultRetryPolicy(5000, 5, 2.f));
        queue.add(request);
        return request;
    }

    /**
     * 用于发布同步请求，
     * Volley是一个非常优秀的网络框架，不仅可以发送异步请求，
     * 同步请求同样也非常好用。下面这段代码应该在子线程中调用，
     * 否则将会阻塞
     */
    public static String getSync(String url) {
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(url, future, future);
        RequestQueue queue = getRequestQueue();
        queue.add(request);

        String response;
        try {
            response = future.get();
        } catch (InterruptedException e) {
            response = "";
        } catch (ExecutionException e) {
            response = "";
        }

        return response;
    }

    public static void post(String url, final Map<String, String> params, Response.Listener<String> listener,
                            Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

        };
        queue.add(request);

    }

    /**
     * 参数字节流*
     */
    public static Request<String> post(String url, final byte[] buffer, Response.Listener<String> listener,
                                       Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue();
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return buffer;
            }

        };
        return queue.add(request);

    }

    /**
     * 模板类Post请求
     */
    public static <T> Request<T> post(String url,
                                      final Map<String, String> params, Class<T> clazz,
                                      Response.Listener<T> listener, Response.ErrorListener errorListener) {
        RequestQueue queue = getRequestQueue();
        GsonRequest<T> request = new GsonRequest<T>(url, clazz, listener,
                errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        return queue.add(request);
    }

    /**
     * 获取缓存
     */
    public static String getCache(final String url) {
        RequestQueue queue = getRequestQueue();
        Cache.Entry cache = queue.getCache().get(url);
        return cache != null ? new String(cache.data) : null;
    }

    /**
     * 获取缓存模板
     */
    public static <T> T getCache(final String url, Class<T> c) {
        RequestQueue queue = getRequestQueue();
        Cache.Entry cache = queue.getCache().get(url);
        return cache != null ? new GsonBuilder().create().fromJson(new String(cache.data), c) : null;
    }


}

