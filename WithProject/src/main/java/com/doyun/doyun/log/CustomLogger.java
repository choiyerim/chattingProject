package com.doyun.doyun.log;

import org.slf4j.Logger;

public  abstract class CustomLogger {
	 protected Logger logger;

	    

	    public CustomLogger(Logger logger) {

	        this.logger = logger;

	    }

	    

	    public void trace(String msg) { 

	        logger.trace(msg);

	        writeTrace(msg);

	    }

	    

	    public void debug(String msg) { 

	        logger.debug(msg);

	        writeDebug(msg);

	    }

	    

	    public void info(String msg) { 

	        logger.info(msg);

	        writeInfo(msg);

	    }

	    

	    public void warn(String msg) {

	        logger.warn(msg);

	        writeWarn(msg);

	    }

	    

	    public void error(String msg) { 

	        logger.error(msg);

	        writeError(msg);

	    }

	    

	    public abstract void writeTrace(String msg);

	    public abstract void writeDebug(String msg);

	    public abstract void writeInfo(String msg);

	    public abstract void writeWarn(String msg);

	    public abstract void writeError(String msg);

}
