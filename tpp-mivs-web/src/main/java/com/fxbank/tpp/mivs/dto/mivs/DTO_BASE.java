package com.fxbank.tpp.mivs.dto.mivs;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

import com.fxbank.cip.base.dto.DataTransObject;
import com.fxbank.tpp.mivs.model.PMTS_HEAD;
import com.fxbank.tpp.mivs.model.PMTS_SIGN;
import com.fxbank.tpp.mivs.model.SIGN_DATA;

public abstract class DTO_BASE extends DataTransObject implements Serializable,SIGN_DATA{

	private static final long serialVersionUID = 5106180480948729931L;

	@XmlTransient
	private PMTS_HEAD head;

	@XmlTransient
	private PMTS_SIGN sign;


	/**
	 * @return the head
	 */
	public PMTS_HEAD getHead() {
		return head;
	}

	/**
	 * @param head the head to set
	 */
	public void setHead(PMTS_HEAD head) {
		this.head = head;
	}

	/**
	 * @return the sign
	 */
	public PMTS_SIGN getSign() {
		return sign;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(PMTS_SIGN sign) {
		this.sign = sign;
	}
}