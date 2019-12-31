package com.charles.idol.mapper;
import java.util.HashMap;
import java.util.List;

import com.charles.idol.pojo.Essay;
public interface EssayMapper {
	public Essay getEssayById(String id);
	public boolean addEssay(Essay e);
	public List<Essay> getEssayList(HashMap<String,Integer> pages);
	public boolean delEssayById(String id);
	public boolean updateEssayContentById(HashMap<String,String> essay);
	public Essay getEssayContentById(String essayid);
	public List<Essay> getEssayByType(HashMap<String,Object> type);
}
