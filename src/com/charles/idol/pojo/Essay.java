package com.charles.idol.pojo;

public class Essay {
	private String essayid;
	private String title;
	private int type;
	private String from;
	private String time;
	private String img;
	private String content;
	private String editor;
	public Essay() {
	}
	public Essay(String essayid, String title, int type, String from, String time, String img, String content,
			String editor) {
		super();
		this.essayid = essayid;
		this.title = title;
		this.type = type;
		this.from = from;
		this.time = time;
		this.img = img;
		this.content = content;
		this.editor = editor;
	}
	public String getEssayid() {
		return essayid;
	}
	public void setEssayid(String essayid) {
		this.essayid = essayid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	@Override
	public String toString() {
		return "Essay [essayid=" + essayid + ", title=" + title + ", type=" + type + ", from=" + from + ", time=" + time
				+ ", img=" + img + ", content=" + content + ", editor=" + editor + "]";
	}
	
}
