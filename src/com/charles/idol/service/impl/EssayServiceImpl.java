package com.charles.idol.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.charles.idol.mapper.EssayMapper;
import com.charles.idol.mapper.UserMapper;
import com.charles.idol.pojo.Essay;
import com.charles.idol.service.EssayService;
public class EssayServiceImpl implements EssayService {
	private EssayMapper essayMapper;
	public void setEssayMapper(EssayMapper essayMapper) {
		this.essayMapper = essayMapper;
	}
	@Override
	public Essay getEssayById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean addEssay(Essay e) {
		boolean addEssay = essayMapper.addEssay(e);
		return addEssay;
	}
	@Override
	public List<Essay> getEssayList(HashMap<String, Integer> pages) {
		// TODO Auto-generated method stub
		List<Essay> essays=(List<Essay>) essayMapper.getEssayList(pages);
		return essays;
	}
	@Override
	public boolean delEssayById(String id) {
		return essayMapper.delEssayById(id);
	}
	@Override
	public boolean updateEssayContentById(HashMap<String, String> essay) {
		return essayMapper.updateEssayContentById(essay);
	}
	@Override
	public Essay getEssayContentById(String essayid) {
		// TODO Auto-generated method stub
		return essayMapper.getEssayById(essayid);
	}
	@Override
	public List<Essay> getEssayByType(HashMap<String,Object> type) {
		// TODO Auto-generated method stub
		return essayMapper.getEssayByType(type);
	}
}
