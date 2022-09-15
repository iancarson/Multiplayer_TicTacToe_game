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
import com.shenkar.gamelobby.utils.GlobalFunctions;
import com.shenkar.gamelobby.utils.GlobalVariables;
import com.shenkar.gamelobby.utils.RedisApi;

@WebServlet("/register")
public class registerControl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public registerControl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Register(request,  response);
	}

	private void Register(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> _ret = new LinkedHashMap<String, Object>();
		try
		{
			String _message = request.getParameter("Data");
			Map<String, Object> _parsedJson = GlobalFunctions.DeserializeJson(_message);
			if(_parsedJson.containsKey("Email") && _parsedJson.containsKey("Password")) 
			{
				System.out.println("Success");
				String _email = _parsedJson.get("Email").toString();
				String _password = _parsedJson.get("Password").toString();
				Map<String,String> _loginData = RedisApi.GetUserData(_email);
//				String phoneNumber = Red
				//If t
			
				//if(GlobalVariables.users.containsKey(_email) == false)
				
				if(_loginData.containsKey("Email") == false)
				{
					Map<String,String> _userData = new LinkedHashMap<String, String>();
					_userData.put("Password", _password);
					_userData.put("Email", _email);
					_userData.put("CreatedTime", GlobalFunctions.GetUTCDate());
					_userData.put("UserId", GlobalFunctions.CreateUserUUID());
					//GlobalVariables.users.put(_email,_userData);
					RedisApi.SetUserData(_email,_userData);
					RedisApi.SetUserRating(_userData.get("UserId").toString(),"500");
					_ret.put("IsCreated", true);
				}
				else
				{
					_ret.put("IsCreated", false);
					_ret.put("ErrorCode", "User Already Exist");
					/*
											 * 
						_ret.put("Response", "CodeValidation");
						_ret.put("IsCreated", true);
						_ret.put("nickname", "name");
						_ret.put("gems", NUMBER);
						_ret.put("UserId", "UserId");
					 */
				}
			}
			else
			{
				_ret.put("IsCreated", false);
				_ret.put("ErrorCode", "Missing Variables");
			}
			
			_ret.put("Response", "Register");
			String _retString = GlobalFunctions.SerializeToJson(_ret);
			PrintWriter out = response.getWriter();
		 	out.println(_retString);
		}
		catch(Exception e) 
		{
			_ret.put("IsCreated", false);
			_ret.put("ErrorCode", e.getMessage());
		}
	}
}
