package com.charles.idol.service;

import java.util.HashMap;
import java.util.List;

import com.charles.idol.mapper.MsgBoardMapper;
import com.charles.idol.pojo.MsgBoard;

public interface MsgBoardService {
	public boolean pushMsgBoard(MsgBoard msg);
	public List<MsgBoard> getAllMsg(HashMap<String,Integer> page);
	public boolean delMsgById(String id);
	public List<MsgBoard> getAllMsgByEssayId(HashMap<String,Object> id);
}
