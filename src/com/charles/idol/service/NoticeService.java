package com.charles.idol.service;

import java.util.HashMap;
import java.util.List;

import com.charles.idol.pojo.Notice;

public interface NoticeService {
	public boolean  postNotice(Notice notice);
	public boolean delNoticeById(HashMap<String,String> id);
	public List<Notice> getAllNotice(HashMap<String,Integer> page);
	public boolean updateNoticeById(HashMap<String,String> para);
}
