package com.charles.idol.pojo;

public class MsgBoard {
	private String sender;
	private String essayid;
	private String msgid;
	private String sendtime;
	private String content;
	public MsgBoard() {
	}
	public MsgBoard(String sender, String essayid, String msgid, String sendtime, String content) {
		super();
		this.sender = sender;
		this.essayid = essayid;
		this.msgid = msgid;
		this.sendtime = sendtime;
		this.content = content;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getEssayid() {
		return essayid;
	}
	public void setEssayid(String essayid) {
		this.essayid = essayid;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getSendtime() {
		return sendtime;
	}
	public void setSendtime(String sendtime) {
		this.sendtime = sendtime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "MsgBoard [sender=" + sender + ", essayid=" + essayid + ", msgid=" + msgid + ", sendtime=" + sendtime
				+ ", content=" + content + "]";
	}
}
