package com.meConcurrent.cancelTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.channels.UnsupportedAddressTypeException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;


public class WebCrawler {

	
	static void h2() throws HttpException, IOException{

		HttpClient client = new HttpClient(); 
      // 设置代理服务器地址和端口      
      //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port); 
      // 使用 GET 方法 ，如果服务器需要通过 HTTPS 连接，那只需要将下面 URL 中的 http 换成 https 
      //   HttpMethod method=new GetMethod("http://java.sun.com");
	    HttpMethod method=new GetMethod("http://210.14.151.134:10030/css/admin/style.css");
//	    method.setRequestHeader("User-Agent", " Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)"  
//	    		+ "Chrome/49.0.2623.22 Safari/537.36 SE 2.X MetaSr 1.0");
	  method.addRequestHeader("Accept", " text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"); // 不加次句的话默认是json连接



      //使用POST方法
      //HttpMethod method = new PostMethod("http://java.sun.com");
      client.executeMethod(method);

      //打印服务器返回的状态
      System.out.println(method.getStatusLine());
      //打印返回的信息
      
      System.out.println(toUTF8(method.getResponseBodyAsString()));
      Header [] k=method.getResponseHeaders();
      //释放连接
      method.releaseConnection();
	}
	
	/**  
	 * 字符串转换编码  
	 * @author Administrator  
	 *  
	 */  

	    public static String toUTF8(String param) {  
	        if (param == null) {  
	            return null;  
	        } else {  
	            try {  
	                param = new String(param.getBytes("ISO-8859-1"), "UTF-8");  
	            } catch (UnsupportedEncodingException e) {  
	                e.printStackTrace();  
	                return param;  
	            }  
	        }  
	        return param;  
	    }  
	  
	
	
	
		public static void main(String[] args) throws HttpException, IOException {
			WebCrawler.h2();
			
			//ArrayList<E>
	//		LinkedList<E>
			
		}
}
