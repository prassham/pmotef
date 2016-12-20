package com.ibm.pmo.O2Observer;

public class AttachmentPojo {
	private String content_Type;
	private Integer revpos;
	private String digest;
	private Integer length;
	private Boolean stub;

	public String getContentType() {
	return content_Type;
	}

	public void setContentType(String content_Type) {
	this.content_Type = content_Type;
	}

	public Integer getRevpos() {
	return revpos;
	}

	public void setRevpos(Integer revpos) {
	this.revpos = revpos;
	}

	public String getDigest() {
	return digest;
	}

	public void setDigest(String digest) {
	this.digest = digest;
	}

	public Integer getLength() {
	return length;
	}


	public void setLength(Integer length) {
	this.length = length;
	}


	public Boolean getStub() {
	return stub;
	}

	public void setStub(Boolean stub) {
	this.stub = stub;
	}

}
