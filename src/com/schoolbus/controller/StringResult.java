package com.schoolbus.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;



public class StringResult extends StrutsResultSupport {  
    private static final long serialVersionUID = 1L;  
    private String contentTypeName;  
    private String stringName = "";  
    public StringResult() {  
        super();  
    }  
    public StringResult(String location) {  
        super(location);  
    }  
    protected void doExecute(String finalLocation, ActionInvocation invocation)  
            throws Exception {  
        HttpServletResponse response = (HttpServletResponse) invocation  
                .getInvocationContext().get(HTTP_RESPONSE);  
        String contentType = conditionalParse(contentTypeName, invocation);  
        if (contentType == null) {  
            contentType = "text/json; charset=UTF-8";  
        }  
        response.setContentType(contentType);  
        PrintWriter out = response.getWriter();  
        String result = (String) invocation.getStack().findValue(stringName);  
        out.println(result);  
        out.flush();  
        out.close();  
    }  
    public String getContentTypeName() {  
        return contentTypeName;  
    }  
    public void setContentTypeName(String contentTypeName) {  
        this.contentTypeName = contentTypeName;  
    }  
    public String getStringName() {  
        return stringName;  
    }  
    public void setStringName(String stringName) {  
        this.stringName = stringName;  
    }  
}  