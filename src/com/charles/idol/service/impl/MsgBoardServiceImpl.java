package com.charles.idol.service.impl;

import java.util.HashMap;
import java.util.List;

import com.charles.idol.mapper.MsgBoardMapper;
import com.charles.idol.pojo.MsgBoard;
import com.charles.idol.service.MsgBoardService;

public class MsgBoardServiceImpl implements MsgBoardService {
	private MsgBoardMapper msgBoardMapper;
	public void setMsgBoardMapper(MsgBoardMapper msgBoardMapper) {
		this.msgBoardMapper = msgBoardMapper;
	}
	@Override
	public boolean pushMsgBoard(MsgBoard msg) {
		// TODO Auto-generated method stub
		return msgBoardMapper.pushMsgBoard(msg);
	}
	@Override
	public List<MsgBoard> getAllMsg(HashMap<String, Integer> page) {
		// TODO Auto-generated method stub
		return msgBoardMapper.getAllMsg(page);
	}
	@Override
	public boolean delMsgById(String id) {
		// TODO Auto-generated method stub
		return msgBoardMapper.delMsgById(id);
	}
	@Override
	public List<MsgBoard> getAllMsgByEssayId(HashMap<String, Object> id) {
		// TODO Auto-generated method stub
		return msgBoardMapper.getAllMsgByEssayId(id);
	}
}
