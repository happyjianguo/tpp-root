package com.fxbank.tpp.bocm.dto.bocm;

public class Req_1001 {

    private String txcode;

    private String data;

	/**
	 * @return the txcode
	 */
	public String getTxcode() {
		return txcode;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @param txcode the txcode to set
	 */
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	@Override
	public String toString() {
		return txcode+"-"+data;
	}
}