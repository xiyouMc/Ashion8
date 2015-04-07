package com.android.hui.ashion.util;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;

/**
 * Created by litonghui on 2015/4/7.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson;
    private final Class<T> mClass;
    private final Response.Listener<T> mListener;

    public GsonRequest(int method, String url, Class<T> c
            , Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       boolean needDecode) {
        super(method, url, errorListener);
        mClass = c;
        mListener = listener;
        mGson = needDecode ? new GsonBuilder()
                .registerTypeAdapter(String.class, new StringDeserializer())
                .create() : new GsonBuilder().create();
    }

    public GsonRequest(String url, Class<T> c, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        this(Method.GET, url, c, listener, errorListener, true);
    }

    public GsonRequest(String url, Class<T> c, Response.Listener<T> listener,
                       Response.ErrorListener errorListener,
                       boolean needDecode) {
        this(Method.GET, url, c, listener, errorListener, needDecode);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {
        final String json = new String(networkResponse.data);
        return Response.success(mGson.fromJson(json, mClass), HttpHeaderParser
                .parseCacheHeaders(networkResponse));

    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);

    }

    /**
     * decode the encoded String
     */
    private class StringDeserializer implements JsonDeserializer<String> {
        @Override
        public String deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return URLDecoder.decode(jsonElement.getAsJsonPrimitive().getAsString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }
}
