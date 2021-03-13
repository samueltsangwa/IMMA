package com.example.imma;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {
    //https://newsapi.org/v2/top-headlines?apiKey="dece2fd9324d4a54908b1af5d2e1c6cf&country=ie&pageSize=5
    private static final String BASE_URL="https://newsapi.org/v2/";
    private static Retrofit retrofit;
    private static Client client;

    private Client() {
        retrofit= new Retrofit.Builder().baseUrl(BASE_URL).client(getUnsafeOKHttpClient().build())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    public static synchronized Client getInstance(){
        if (client==null)
        {
            client=new Client();
        }
        return client;
    }
    public static OkHttpClient.Builder getUnsafeOKHttpClient() {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };
            final SSLContext sslContext= SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory=sslContext.getSocketFactory();

            final OkHttpClient.Builder builder=new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public Interface getApi()
    {
        return retrofit.create(Interface.class);
    }
}
