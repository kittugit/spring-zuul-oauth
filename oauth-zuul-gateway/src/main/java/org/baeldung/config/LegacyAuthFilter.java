package org.baeldung.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.netflix.ribbon.proxy.annotation.Http.HttpMethod;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * LegacyAuthFilter
 */
@Component
public class LegacyAuthFilter extends ZuulFilter {

    
    
    LegacyAuthFilter(){
        System.out.println("LegacyAuthFilter - 0");
        
    }

	@Override
	public String filterType() {
        System.out.println("filterType - 0");
		return FilterConstants.POST_TYPE;
	}

	@Override
	public int filterOrder() {
        System.out.println("filterOrder - 0");
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER  - 1;
	}

	@Override
	public boolean shouldFilter() {
        System.out.println("should Filter - 0");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        boolean is2xxSuccessful = HttpStatus.valueOf(requestContext.getResponseStatusCode()).is2xxSuccessful();
        System.out.println("should Filter" + is2xxSuccessful);
		return  is2xxSuccessful;
    }
    
    @Override
    public Object run() {
		OkHttpClient httpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder()
                .header("SESSION_ID", "someid")
                .url("http://localhost:8082/spring-security-oauth-resource/users/test").build();
        try {
            Response response = httpClient.newCall(request).execute();
            System.out.println(response);  
        } catch (IOException e) {
            System.out.println(e.getMessage());
			e.printStackTrace();
        }  
             

                return null;
                
    }
}