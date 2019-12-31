/**
 * projectName: IDOL
 * fileName: StudentService.java
 * pakageName: com.charles.idol.service
 * date: 2019年12月10日
 * copyright(c): 2019 2020 charles
 */
package com.charles.idol.service;

import java.util.HashMap;
import java.util.List;

import com.charles.idol.pojo.User;

/**
 * @author charles
 * @title StudentService.java
 * @pakage com.charles.idol.service
 * @description TODO
 * @author charles
 * @version V1.0
 * @date 2019年12月10日 下午8:46:08
 */
public interface UserService {
	public User userLogin(User user);
	public boolean register(User user);
	public List<User> getUserList(HashMap<String,Integer> pages);
	public boolean updatePower(HashMap<String,Object> power);
	public List<User> searchUser(HashMap<String,Object> info);
	public boolean updatePassoword(HashMap<String,String> password);
	public boolean userProfileUpdate(HashMap<String,String> profile);
	public boolean updateUserInfoHandler(HashMap<String,String> uinfo);
}
