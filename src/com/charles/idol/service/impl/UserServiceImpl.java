/**
 * projectName: IDOL
 * fileName: UserServiceImpl.java
 * pakageName: com.charles.idol.service.impl
 * date: 2019年12月10日
 * copyright(c): 2019 2020 charles
 */
package com.charles.idol.service.impl;
import java.util.HashMap;
import java.util.List;

import com.charles.idol.mapper.UserMapper;
import com.charles.idol.pojo.User;
import com.charles.idol.service.UserService;
/**
 * @author charles
 * @title UserServiceImpl.java
 * @pakage com.charles.idol.service.impl
 * @description TODO
 * @author charles
 * @version V1.0
 * @date 2019年12月10日 下午8:48:31
 */
public class UserServiceImpl implements UserService {
	private UserMapper userMapper;
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	@Override
	public User userLogin(User user) {
		// TODO Auto-generated method stub
		return userMapper.login(user);
	}
	@Override
	public boolean register(User user) {
		if(user.getUsername().length()<3||user.getUsername().length()>8)
		{
			return false;
		}
		else if(user.getPassword().length()<8)
		{
			return false;
		}
		else if(user.getPlace().length()<4||user.getPlace().length()>50)
		{
			return false;
		}
		else if(!user.getEmail().matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"))
		{
			return false;
		}
		else
		{
			return userMapper.register(user);
		}
	}
	@Override
	public List<User> getUserList(HashMap<String,Integer> pages) {
		return userMapper.getUserList(pages);
	}
	@Override
	public boolean updatePower(HashMap<String, Object> power) {
		// TODO Auto-generated method stub
		return userMapper.updatePower(power);
	}
	@Override
	public List<User> searchUser(HashMap<String, Object> info) {
		// TODO Auto-generated method stub
		return userMapper.searchUser(info);
	}
	@Override
	public boolean updatePassoword(HashMap<String, String> password) {
		// TODO Auto-generated method stub
		return userMapper.updatePassoword(password);
	}
	@Override
	public boolean userProfileUpdate(HashMap<String, String> profile) {
		// TODO Auto-generated method stub
		return userMapper.userProfileUpdate(profile);
	}
	@Override
	public boolean updateUserInfoHandler(HashMap<String, String> uinfo) {
		// TODO Auto-generated method stub
		return userMapper.updateUserInfoHandler(uinfo);
	}
}
