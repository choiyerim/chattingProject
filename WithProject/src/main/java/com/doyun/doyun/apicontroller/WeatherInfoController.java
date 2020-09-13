package com.doyun.doyun.apicontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api")
@RestController
public class WeatherInfoController {
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping("/regionWeather")
	public JSONObject getAPI(@RequestParam HashMap<String, String> paramMap) {
		JSONObject obj=new JSONObject();
		//동네 예보 조회 서비스 연동
		String url="https://api.openweathermap.org/data/2.5/onecall";
		String data="?lat="+paramMap.get("lat")+"&lon="+paramMap.get("lon")+"&" + 
				"exclude="+paramMap.get("part")+"&appid=7b3501692ba082055a8af78826323b48";
		String json = null;
		try {
			json = getUrlConnectionBuffer(url,data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONParser parser=new JSONParser();
		try {
			obj=(JSONObject) parser.parse(json);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	
	
	
	
	
	//urlconnection
	public String getUrlConnectionBuffer(String url,String data) throws IOException {
		String buffer="";
		BufferedReader buf=null;
		
		try {
			URL urls=new URL(url+data);
			HttpURLConnection con=(HttpURLConnection) urls.openConnection();
			con.setRequestMethod("GET");
			buf=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
			String line="";
			while((line=buf.readLine())!=null) {
				buffer+=line;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			buf.close();
		}
		return buffer;

	}
	
}
