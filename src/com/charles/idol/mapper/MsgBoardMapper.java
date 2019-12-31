package com.charles.idol.mapper;
import java.util.HashMap;
import java.util.List;

import com.charles.idol.pojo.MsgBoard;
public interface MsgBoardMapper {
	public boolean pushMsgBoard(MsgBoard msg);
	public List<MsgBoard> getAllMsg(HashMap<String,Integer> page);
	public boolean delMsgById(String id);
	public List<MsgBoard> getAllMsgByEssayId(HashMap<String,Object> id);
}
