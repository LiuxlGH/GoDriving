package com.liuxl.godriving.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.liuxl.godriving.R;
//import com.liuxl.godriving.model.Server;
//
//import junit.framework.Assert;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.KeyManagementException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.CertificateException;
//
//import javax.net.ssl.KeyManagerFactory;
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.SSLServerSocketFactory;


/**
 * Created by Liuxl on 2017/8/23.
 */

public class ServerService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

//    private static final String TAG = ServerService.class.getSimpleName();
//    private static final int  SERVER_PORT = 4567;
//    Server soudboxServer;
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        soudboxServer = new Server(SERVER_PORT,this);
//        Log.i(TAG,"create server");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Log.i(TAG,"server thread start ");
//                    createMySSLFactory();
//                    soudboxServer.start();
//                    try {
//                        long start = System.currentTimeMillis();
//                        Thread.sleep(100L);
//                        while (!soudboxServer.wasStarted()) {
//                            Thread.sleep(100L);
//                            if (System.currentTimeMillis() - start > 2000) {
//                                Assert.fail("could not start server");
//                            }
//                        }
//                    } catch (InterruptedException e) {
//                    }
//                    Log.i(TAG,"server start");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (CertificateException e) {
//                    e.printStackTrace();
//                } catch (NoSuchAlgorithmException e) {
//                    e.printStackTrace();
//                } catch (UnrecoverableKeyException e) {
//                    e.printStackTrace();
//                } catch (KeyStoreException e) {
//                    e.printStackTrace();
//                } catch (KeyManagementException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if(null != soudboxServer){
//            soudboxServer.stop();
//        }
//    }
//    private static final String KEYSTORE_PWD = "ssltest";
//    private void createMySSLFactory() throws NoSuchAlgorithmException, KeyStoreException, IOException, UnrecoverableKeyException, KeyManagementException, CertificateException {
//        InputStream inputStream = null;
//        //选择安全协议的版本
//        SSLContext ctx = SSLContext.getInstance("TLS");
//        KeyManagerFactory keyManagers = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//        inputStream = getResources().openRawResource(R.raw.test);
//        //选择keystore的储存类型，andorid只支持BKS
//        KeyStore ks = KeyStore.getInstance("BKS");
//
//        ks.load(inputStream, KEYSTORE_PWD.toCharArray());
//
//        keyManagers.init(ks, KEYSTORE_PWD.toCharArray());
//
//        ctx.init(keyManagers.getKeyManagers(), null, null);
//
//        SSLServerSocketFactory serverSocketFactory = ctx.getServerSocketFactory();
//        soudboxServer.makeSecure(serverSocketFactory,null);
//    }

}
