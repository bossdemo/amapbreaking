package top.hdzi.appbreaking.utils;

import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtils {
    public static String post(String url, Map<String, Object> params) {
        return post(url, params, (String) null);
    }

    public static String post(String url, Map<String, Object> params, String charset) {
        return post(url, params, null, charset);
    }

    public static String post(String url, Map<String, Object> params, Map<String, Object> headers) {
        return post(url, params, headers, null);
    }

    public static String post(String url, Map<String, Object> params, Map<String, Object> headers, String charset) {
        charset = charset == null ? Consts.UTF_8.toString() : charset;

        HttpPost httpPost = new HttpPost(url);
        // header入参
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }
        // params入参
        if (params != null) {
            List<NameValuePair> nvp = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nvp.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            UrlEncodedFormEntity entity = null;
            try {
                entity = new UrlEncodedFormEntity(nvp, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
            entity.setContentEncoding(charset);
            httpPost.setEntity(entity);
        }

        return responseAsString(httpPost, charset);
    }

    public static String post(String url, String params) {
        return post(url, params, null);
    }

    public static String post(String url, String params, Map<String, Object> headers) {
        HttpPost httpPost = new HttpPost(url);
        // header入参
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpPost.setHeader(new BasicHeader(entry.getKey(), String.valueOf(entry.getValue())));
            }
        }
        // params入参
        if (params != null) {
            try {
                httpPost.setEntity(new StringEntity(params));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return responseAsString(httpPost, null);
    }


    public static String get(String url) {
        return get(url, null);
    }

    public static String get(String url, Map<String, Object> headers) {
        return get(url, headers, null);
    }

    public static String get(String url, Map<String, Object> headers, String charset) {
        charset = charset == null ? Consts.UTF_8.toString() : charset;
        HttpGet httpGet = new HttpGet(url);
        // header
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return responseAsString(httpGet, charset);
    }

    public static byte[] getAsBytes(String url, Map<String, Object> headers) {
        HttpGet httpGet = new HttpGet(url);
        // header
        if (headers != null) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.setHeader(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return responseAsBytes(httpGet);
    }

    private static String responseAsString(HttpRequestBase httpGet, String charset) {
        return sendRequest(httpGet, charset, (response, args) -> EntityUtils.toString(response.getEntity(), (String) args[0]));
    }

    private static byte[] responseAsBytes(HttpRequestBase httpGet) {
        return sendRequest(httpGet, null, (response, args) -> EntityUtils.toByteArray(response.getEntity()));
    }

    @SuppressWarnings("unchecked")
    private static <T> T sendRequest(HttpRequestBase httpGet, String charset, GetEntityValue<T> getEntityValue) {
        // 重试3次
        T resValue = null;
        CloseableHttpResponse response = null;
        CloseableHttpClient httpClient = ClientBuilder.createSimpleClient();
        for (int retry = 0; resValue == null && retry < 3; retry++) {
            try {
                // client
                response = httpClient.execute(httpGet, HttpClientContext.create());
                resValue = getEntityValue.getValue(response, charset);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            httpClient.close();
            if (response != null) response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resValue;
    }

    public static String spliceUrl(String url, Map<String, String> params) {
        /* 拼接url */
        return url + "?" + spliceParams(params);
    }

    public static String spliceParams(Map<String, String> params) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    //================================================================================================================
    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(8000)
            .setConnectTimeout(8000)
            .build();

    //================================================================================================================
    private static interface GetEntityValue<T> {
        T getValue(CloseableHttpResponse response, Object... args) throws Exception;
    }

    //================================================================================================================
    private static class ClientBuilder {

        /**
         * default
         *
         * @return CloseableHttpClient
         */
        private static CloseableHttpClient createSimpleClient() {

            return HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        }

        /**
         * 自定义Client
         * 1 请求重试机制
         * 2 连接池
         * 3 连接存活策略
         *
         * @return CloseableHttpClient
         */
        private static CloseableHttpClient createCustomClient() {

            // 请求重试HANDLER，自定义异常处理机制。
            HttpRequestRetryHandler myHandler = myRetryHandler();

            // 连接池管理器
            PoolingHttpClientConnectionManager cm = connectionManager();

            // keepAliveStrategy
            ConnectionKeepAliveStrategy keepAlive = keepAliveStrategy();

            return HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)// set config
                    .setRetryHandler(myHandler)
                    .setConnectionManager(cm)
                    .setKeepAliveStrategy(keepAlive)
                    .build();
        }

        /**
         * 请求重试HANDLER，自定义异常处理机制。
         */
        private static HttpRequestRetryHandler myRetryHandler() {
            return (exception, executionCount, context) -> {
                if (executionCount >= 5) {
                    // 如果已经重试了5次，就放弃
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    // 连接被拒绝
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    // 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    // 目标服务器不可达
                    return false;
                }
                if (exception instanceof SSLException) {
                    // ssl握手异常
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                return !(request instanceof HttpEntityEnclosingRequest);
            };
        }

        /**
         * 连接池管理器
         */
        private static PoolingHttpClientConnectionManager connectionManager() {
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
            // 将最大连接数增加到200
            cm.setMaxTotal(200);
            // 将每个路由基础的连接增加到20
            cm.setDefaultMaxPerRoute(20);

            return cm;
        }

        /**
         * 连接存活策略
         */
        private static ConnectionKeepAliveStrategy keepAliveStrategy() {
            return (response, context) -> {
                HeaderElementIterator iterator = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (iterator.hasNext()) {
                    HeaderElement he = iterator.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (null != value && "timeout".equalsIgnoreCase(param)) {
                        return Long.parseLong(value) * 1000;
                    }
                }

                HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
                    // Keep alive for 5 seconds only
                    return 5 * 1000;
                } else {
                    // otherwise keep alive for 30 seconds
                    return 30 * 1000;
                }
            };
        }
    }

}
