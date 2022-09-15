package com.shenkar.gamelobby.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shenkar.gamelobby.utils.GlobalEnums;
import com.shenkar.gamelobby.utils.GlobalFunctions;
import com.shenkar.gamelobby.utils.GlobalVariables;
import com.shenkar.gamelobby.utils.RedisApi;

@WebServlet("/login")
public class loginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public loginController() {super();}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Login(request, response);
	}

	private void Login(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> _ret = new LinkedHashMap<String, Object>(); 
		try
		{
			String _message = request.getParameter("Data");
			Map<String,Object> _parsedJson = GlobalFunctions.DeserializeJson(_message);
			if(_parsedJson.containsKey("Email") && _parsedJson.containsKey("Password"))
			{
				String _email = _parsedJson.get("Email").toString();
				String _password = _parsedJson.get("Password").toString();
				Map<String,String> _loginData = RedisApi.GetUserData(_email);
				
				//if(GlobalVariables.users.containsKey(_email))
				if(_loginData.containsKey("Email"))
				{
					//Map<String,Object> _userData = GlobalVariables.users.get(_email);
					//if(_userData.containsKey("Password") && _userData.get("Password").toString().equals(_password))
					if(_loginData.containsKey("Password") && _loginData.get("Password").toString().equals(_password))
					{	
						_ret.put("IsLoggedIn", true);
						_ret.put("UserId", _loginData.get("UserId"));
					}
					else 
					{
						_ret.put("IsLoggedIn", false);
						_ret.put("ErrorCode", "Wrong Password");
					}
				}
				else
				{
					_ret.put("IsLoggedIn", false);
					_ret.put("ErrorCode", GlobalEnums.errorCodes.UserDoesntExist.getStrValue());
				}
			}
			else
			{
				_ret.put("IsLoggedIn", false);
				_ret.put("ErrorCode", "Missing Variables");
			}
		}
		catch(Exception e) 
		{
			_ret.put("IsLoggedIn", false);
			_ret.put("ErrorCode", GlobalEnums.errorCodes.Unknown.getStrValue());
		};
		
		_ret.put("Response", "Login");
		
		GlobalFunctions.GlobalResponse(response, _ret);
//		String _retString = GlobalFunctions.SerializeToJson(_ret);
//		try 
//		{
//			PrintWriter out = response.getWriter();
//		    out.println(_retString);
//		} 
//		catch (IOException e) {}
	}
}
