/**
 * projectName: IDOL
 * fileName: UserMapper.java
 * pakageName: com.charles.idol.mapper
 * date: 2019年12月10日
 * copyright(c): 2019 2020 charles
 */
package com.charles.idol.mapper;

import java.util.HashMap;
import java.util.List;
import com.charles.idol.pojo.User;
/**
 * @author charles
 * @title UserMapper.java
 * @pakage com.charles.idol.mapper
 * @description TODO
 * @author charles
 * @version V1.0
 * @date 2019年12月10日 下午8:37:31
 */
public interface UserMapper {
	public boolean register(User user);
	public User login(User user);
	public List<User> getUserList(HashMap<String,Integer> pages);
	public boolean updatePower(HashMap<String,Object> power);
	public List<User> searchUser(HashMap<String,Object> info);
	public boolean updatePassoword(HashMap<String,String> password);
	public boolean userProfileUpdate(HashMap<String,String> profile);
	public boolean updateUserInfoHandler(HashMap<String,String> uinfo);
}
