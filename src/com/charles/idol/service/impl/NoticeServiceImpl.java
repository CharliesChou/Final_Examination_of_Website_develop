package com.charles.idol.service.impl;

import java.util.HashMap;
import java.util.List;

import com.charles.idol.mapper.NoticeMapper;
import com.charles.idol.pojo.Notice;
import com.charles.idol.service.NoticeService;
public class NoticeServiceImpl implements NoticeService {
	private NoticeMapper noticeMapper;
	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}
	@Override
	public boolean postNotice(Notice notice) {
		// TODO Auto-generated method stub
		return noticeMapper.postNotice(notice);
	}
	@Override
	public boolean delNoticeById(HashMap<String, String> id) {
		// TODO Auto-generated method stub
		return noticeMapper.delNoticeById(id);
	}
	@Override
	public List<Notice> getAllNotice(HashMap<String, Integer> page) {
		// TODO Auto-generated method stub
		return noticeMapper.getAllNotice(page);
	}
	@Override
	public boolean updateNoticeById(HashMap<String, String> para) {
		// TODO Auto-generated method stub
		return noticeMapper.updateNoticeById(para);
	}
}
