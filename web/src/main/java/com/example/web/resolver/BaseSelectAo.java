package com.example.web.resolver;


/**
 * @author wangjunhao
 */
public class BaseSelectAo {

	private String op;
	private String data;
	private String token;


	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "BaseSelectAo{" +
				"op='" + op + '\'' +
				", data='" + data + '\'' +
				", token='" + token + '\'' +
				'}';
	}
}
