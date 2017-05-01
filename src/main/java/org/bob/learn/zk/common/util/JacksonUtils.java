package org.bob.learn.zk.common.util;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Lazy
@Component
public final class JacksonUtils {

    private final static ObjectMapper objectMapper = SpringContextUtils.getBean("objectMapper", ObjectMapper.class);

    private final static XmlMapper xmlMapper = SpringContextUtils.getBean("xmlMapper", XmlMapper.class);

    public static <T> String toJson(T t)  {
        String s = null;
        try
        {
        	 if (t != null) {
                 s = objectMapper.writeValueAsString(t);
             }
        }
        catch(Exception e)
        {
        	
        }
       
        return s;
    }

    public static <T> T fromJson(String s, Class<T> clazz)
             {
    	T t = null;
    	
    	try
    	{
    		
            if (StringUtils.isNoneBlank(s)) {
                t = objectMapper.readValue(s, clazz);
            }
    	}
    	catch(Exception e)
    	{
    		
    		
    	}
        
        return t;
    }

    public static <T> String toXml(T t) throws JsonProcessingException {
        String s = null;
        if (t != null) {
            s = xmlMapper.writeValueAsString(t);
        }
        return s;
    }

    public static <T> T fromXml(String s, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
        T t = null;
        if (StringUtils.isNoneBlank(s)) {
            t = xmlMapper.readValue(s, clazz);
        }
        return t;
    }
}
