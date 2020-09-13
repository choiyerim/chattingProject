package com.doyun.doyun.log;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MongoLogger extends CustomLogger{
	MongoTemplate mongoTemplate;

	public MongoLogger(Logger logger) {
		super(logger);
	}

	public MongoLogger(Logger logger, MongoTemplate mongoTemplate) {

        super(logger);

    }

	/* MongoContext안에 있는 mongoTemplateBean을 가지고 오기 */

    private void setMongoTemplate(String mongoTemplateBeanName) {

        

        // Session이 필요하다. 그러면 request가 필요하다. Spring에 깔려있는 request를 가져와서

        HttpServletRequest request = 

                ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        // 세션을 만든다.

        HttpSession session = request.getSession();

        // ServletContext 객체 가져오기

        ServletContext context = session.getServletContext();

        WebApplicationContext webContext = WebApplicationContextUtils.getWebApplicationContext(context);

        

        this.mongoTemplate = (MongoTemplate) webContext.getBean(mongoTemplateBeanName);

    }



    public MongoLogger(Logger logger, String mongoTemplateBeanName) {

        super(logger);

    }

 

    @Override

    public void writeTrace(String msg) {

        

    }

 

    @Override

    public void writeDebug(String msg) {

    	HashMap<String, Object> log = new HashMap<String, Object>();
        log.put("LEVEL", "DEBUG");
        log.put("DATETIME", new Date());
        log.put("REQUESTER", logger.getName());
        log.put("MESSAGE", msg);
        mongoTemplate.insert(log, "log");      


    }

 

    @Override

    public void writeInfo(String msg) {
    	HashMap<String, Object> log = new HashMap<String, Object>();
        log.put("LEVEL", "INFO");
        log.put("DATETIME", new Date());
        log.put("REQUESTER", logger.getName());
        log.put("MESSAGE", msg);
        mongoTemplate.insert(log, "log");    
        

    }

 

    @Override

    public void writeWarn(String msg) {

        

    }

 

    @Override

    public void writeError(String msg) {

        

    }



}
