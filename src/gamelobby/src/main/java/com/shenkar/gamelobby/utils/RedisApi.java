package com.shenkar.gamelobby.utils;

import java.util.Map;

public class RedisApi 
{
	public static void SetUserData(String _Key,Map<String,String> _UserData)
	{RedisLogic.RedisSetMap(_Key + "/Users", _UserData);}
	
	public static Map<String,String> GetUserData(String _Key)
	{return RedisLogic.RedisGetMap(_Key + "/Users");}
	

	public static void SetSearchData(String _UserId,Map<String,String> _SearchData)
	{RedisLogic.RedisSetMap(_UserId + "/Search", _SearchData);}
	
	public static Map<String,String> GetSearchData(String _UserId)
	{return RedisLogic.RedisGetMap(_UserId + "/Search");}
	
	public static void SetUserRating(String _UserId,String _Rating)
	{RedisLogic.RedisSet(_UserId + "/Rating", _Rating);}
	
	public static String GetUserRating(String _UserId)
	{return RedisLogic.RedisGet(_UserId + "/Rating");}
	
	
	
}
