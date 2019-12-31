package com.charles.idol.pojo;
public class Notice {
	private String sender;
	private String content;
	private String sendtime;
	private String noticeid;
	public Notice() {
	}
	public Notice(String sender, String content, String sendtime, String noticeid) {
		this.sender = sender;
		this.content = content;
		this.sendtime = sendtime;
		this.noticeid = noticeid;
	}
	public String getNoticeid() {
		return noticeid;
	}
	public void setNoticeid(String noticeid) {
		this.noticeid = noticeid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	@Override
	public String toString() {
		return "Notice [sender=" + sender + ", content=" + content + ", sendtime=" + sendtime + "]";
	}
}
