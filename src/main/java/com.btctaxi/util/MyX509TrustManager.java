package com.btctaxi.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
 
/**
 * 证书信任管理器（用于https请求）
 * 
 * @author Rain
 * @date 2013-11-29
 * @version 1.0
 */
public class MyX509TrustManager implements X509TrustManager {
 
	@Override
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub
	}
 
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}
 
}