package com.btctaxi.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.List;


public class HttpUtil {
    protected static Logger LOGGER = Logger.getLogger(HttpUtil.class);
    public static CookieStore cookieStore = new BasicCookieStore();
    public static CloseableHttpClient httpCilent = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    public static String httpPost(String url, List<BasicNameValuePair> list) {

        // 配置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(1000).setRedirectsEnabled(true).build();

        HttpPost httpPost = new HttpPost(url);
        // 设置超时时间
        httpPost.setConfig(requestConfig);

        String strResult = "";
        int StatusCode = 404;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            // 设置post求情参数
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpCilent.execute(httpPost);

            if (httpResponse != null) {
                StatusCode = httpResponse.getStatusLine().getStatusCode();
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    strResult = EntityUtils.toString(httpResponse.getEntity());
                    LOGGER.info("post/" + StatusCode + ":" + strResult);
                    return strResult;
                } else {
                    strResult = "Error Response: " + httpResponse.getStatusLine().toString();
                    LOGGER.info("post/" + StatusCode + ":" + strResult);
                    strResult = null;
                }
            } else {

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {

        }

        return strResult;
    }

    public static String HttpGet(String url) {

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000) // 设置连接超时时间
                .setConnectionRequestTimeout(5000) // 设置请求超时时间
                .setSocketTimeout(5000).setRedirectsEnabled(true)// 默认允许自动重定向
                .build();
        HttpGet httpGet2 = new HttpGet(url);
        httpGet2.setConfig(requestConfig);
        String srtResult = null;
        int StatusCode = 404;
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet2);
            StatusCode = httpResponse.getStatusLine().getStatusCode();
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                LOGGER.info("get/" + StatusCode + ":" + srtResult);
                return srtResult;
            } else {
                srtResult = EntityUtils.toString(httpResponse.getEntity());// 获得返回的结果
                LOGGER.info("get/" + StatusCode + ":" + srtResult);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    public static void setCookieStore(List<BasicClientCookie> cookielist) {
        for (BasicClientCookie cookie : cookielist) {
            HttpUtil.cookieStore.addCookie(cookie);
        }
    }

    public static void createCookie(List<BasicClientCookie> cookielist) {
        for (BasicClientCookie cookie : cookielist) {
            HttpUtil.cookieStore.addCookie(cookie);
        }
    }

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl    请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr     提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }
            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

            jsonObject = new JSONObject(buffer.toString());
        } catch (ConnectException ce) {
            LOGGER.error("server connection timed out.");
        } catch (Exception e) {
            LOGGER.error("https request error:{}", e);
        }
        return jsonObject;
    }

    public static String getHttp(String url, String param) {

        String result = null;

        InputStream in = null;

        try {

            Proxy proxy = new Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress("47.74.223.16", Integer.valueOf("1024")));

            String urlNameString = url + "?" + param;

            URL realUrl = new URL(urlNameString);

            System.out.println(urlNameString);

            // 打开和URL之间的连接

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection(proxy);

            // 设置通用的请求属性

            connection.setRequestProperty("Connection", "keep-alive");

            // 建立实际的连接

            connection.connect();

            // 定义 BufferedReader输入流来读取URL的响应

            in = connection.getInputStream();

            byte[] inByte = new byte[in.available()];

            in.read(inByte, 0, in.available());

            result = new String(inByte, "utf-8");

            System.out.println(result);

        } catch (Exception e) {

            System.out.println("发送GET请求出现异常！" + e);

            e.printStackTrace();

        }

        // 使用finally块来关闭输入流

        finally {

            try {

                if (in != null) {

                    in.close();

                }

            } catch (Exception e2) {

                e2.printStackTrace();

            }

        }

        return result;

    }

    /*
      * 设置证书。
      */
    static{
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
                new javax.net.ssl.HostnameVerifier(){
                    public boolean verify(String hostname,
                                          javax.net.ssl.SSLSession sslSession) {
                        //域名或ip地址
                        if (hostname.equals("https://api.binance.com")) {
                            return true;
                        }
                        return false;
                    }
                });
        //第二个参数为证书的路径
        System.setProperty("javax.net.ssl.trustStore", "/Users/zhangsl/Documents/Digit.cer");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    }

    public static void main(String[] arg) {
        //String ret = HttpUtil.HttpGet("https://api.binance.com/api/v1/time");
        String ret = HttpUtil.HttpGet("https://api.huobi.pro/market/detail/merged?symbol=btcusdt");
        System.out.println("结果是：" + ret);
    }
}
