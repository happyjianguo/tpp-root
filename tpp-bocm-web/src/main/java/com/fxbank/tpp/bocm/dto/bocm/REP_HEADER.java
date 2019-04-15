package com.fxbank.tpp.bocm.dto.bocm;

public class REP_HEADER  implements FIXP_SERIAL{

	private String tMsgTyp;
	private String tRspCd;
	private String tRspMsg;
	private String rLogNo;

	@Override
	public String creaFixPack() {
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("%-1s", this.tMsgTyp));
		sb.append(String.format("%-6s", this.tRspCd));
		sb.append(String.format("%-30s", this.tRspMsg));
		sb.append(String.format("%-14s", this.rLogNo));
		return sb.toString();
	}

	@Override
	public void chanFixPack(String pack) {
		StringBuffer sb = new StringBuffer(pack);
		int i =0;
		this.tMsgTyp= sb.substring(0, i=i+1).trim();
		this.tRspCd= sb.substring(i, i=i+6).trim();
		this.tRspMsg= sb.substring(i, i=i+30).trim();
		this.rLogNo= sb.substring(i, i=i+14).trim();
	}

	/**
	 * @return the tMsgTyp
	 */
	public String gettMsgTyp() {
		return tMsgTyp;
	}

	/**
	 * @return the rLogNo
	 */
	public String getrLogNo() {
		return rLogNo;
	}

	/**
	 * @param rLogNo the rLogNo to set
	 */
	public void setrLogNo(String rLogNo) {
		this.rLogNo = rLogNo;
	}

	/**
	 * @return the tRspMsg
	 */
	public String gettRspMsg() {
		return tRspMsg;
	}

	/**
	 * @param tRspMsg the tRspMsg to set
	 */
	public void settRspMsg(String tRspMsg) {
		this.tRspMsg = tRspMsg;
	}

	/**
	 * @return the tRspCd
	 */
	public String gettRspCd() {
		return tRspCd;
	}

	/**
	 * @param tRspCd the tRspCd to set
	 */
	public void settRspCd(String tRspCd) {
		this.tRspCd = tRspCd;
	}

	/**
	 * @param tMsgTyp the tMsgTyp to set
	 */
	public void settMsgTyp(String tMsgTyp) {
		this.tMsgTyp = tMsgTyp;
	}

}
