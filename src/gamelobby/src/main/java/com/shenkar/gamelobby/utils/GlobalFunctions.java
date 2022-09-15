package com.shenkar.gamelobby.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class GlobalFunctions
{
	 public static Map<String, Object> DeserializeJson(String _Json)
	 {
		 Gson _gson = new GsonBuilder().create();
		 java.lang.reflect.Type typeofHashMap = new TypeToken<LinkedHashMap<String, Object>>(){}.getType();
		 return _gson.fromJson(_Json, typeofHashMap);
	 }
	 
	 public static String GetUTCDate()
	 {
	     TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	     TimeZone timeZone = TimeZone.getTimeZone("UTC");
	     Calendar calendar = Calendar.getInstance(timeZone);
	     SimpleDateFormat simpleDateFormat = 
	 new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
	     simpleDateFormat.setTimeZone(timeZone);
	     String _returnValue = calendar.get(Calendar.YEAR) + "-" + 
	     (calendar.get(Calendar.MONTH) + 1) + "-" + 
	      calendar.get(Calendar.DAY_OF_MONTH) + " ";
	      _returnValue += calendar.get(Calendar.HOUR_OF_DAY) + ":" +
	      calendar.get(Calendar.MINUTE) + ":" + 
	      calendar.get(Calendar.SECOND);
	      return _returnValue;
	  }
	 
	 public static String SerializeToJson(Object _Value)
	 {
		 Gson _gson = new GsonBuilder().create();
		 return _gson.toJson(_Value);
	 }

	 public static String CreateUserUUID()
	 {
		 return UUID.randomUUID().toString().substring(0,17).toUpperCase();
	 }
	 
	 public static void GlobalResponse(HttpServletResponse _Response,Map<String,Object> _Data)
	 {
		String _retString = GlobalFunctions.SerializeToJson(_Data);
		try 
		{
			PrintWriter out = _Response.getWriter();
		    out.println(_retString);
		} 
		catch (IOException e) {System.out.println("GlobalResponse " + e.getMessage());}
	 }

}
