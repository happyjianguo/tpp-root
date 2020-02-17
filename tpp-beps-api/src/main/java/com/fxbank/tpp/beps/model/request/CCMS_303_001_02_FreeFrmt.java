package com.fxbank.tpp.beps.model.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import com.fxbank.tpp.beps.model.SIGN_DATA;

/**
 * 
 * @类描述： CCMS_303_001_02报文体
 * 
 * @项目名称：tpp-beps-api @包名： com.fxbank.tpp.beps.model.bodyreq
 * @类名称：CCMS_303_001_02_Body
 * @创建人：lit
 * @创建时间：2020年1月17日下午4:39:20
 * @修改人：lit
 * @修改时间：2020年1月17日下午4:39:20 @修改备注：
 * @version v1.0
 * @see
 * @bug
 * @Copyright
 * @mail *@qq.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "GrpHdr", "FreeFrmtInf" })
public class CCMS_303_001_02_FreeFrmt implements Serializable, SIGN_DATA {

	private static final long serialVersionUID = -4564586467834859788L;
	private GrpHdr GrpHdr = new GrpHdr();
	private FreeFrmtInf FreeFrmtInf = new FreeFrmtInf();

	/**
	 * @return the GrpHdr
	 */
	public GrpHdr getGrpHdr() {
		return GrpHdr;
	}

	public FreeFrmtInf getFreeFrmtInf() {
		return FreeFrmtInf;
	}

	public void setFreeFrmtInf(FreeFrmtInf freeFrmtInf) {
		FreeFrmtInf = freeFrmtInf;
	}

	/**
	 * @param GrpHdr
	 *            the GrpHdr to set
	 */
	public void setGrpHdr(GrpHdr GrpHdr) {
		this.GrpHdr = GrpHdr;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty", "InstdPty", "SysCd", "Rmk" })
	public static class GrpHdr implements Serializable {

		private static final long serialVersionUID = -2654047044105533783L;
		private String MsgId = null;
		private String CreDtTm = null;
		private InstgPty InstgPty = new InstgPty();
		private InstdPty InstdPty = new InstdPty();
		private String SysCd = null;
		private String Rmk = null;

		public String getSysCd() {
			return SysCd;
		}

		public void setSysCd(String sysCd) {
			SysCd = sysCd;
		}

		public String getRmk() {
			return Rmk;
		}

		public void setRmk(String rmk) {
			Rmk = rmk;
		}

		/**
		 * @return the msgId
		 */
		public String getMsgId() {
			return MsgId;
		}

		/**
		 * @return the instdPty
		 */
		public InstdPty getInstdPty() {
			return InstdPty;
		}

		/**
		 * @param instdPty
		 *            the instdPty to set
		 */
		public void setInstdPty(InstdPty instdPty) {
			this.InstdPty = instdPty;
		}

		/**
		 * @return the instgPty
		 */
		public InstgPty getInstgPty() {
			return InstgPty;
		}

		/**
		 * @param instgPty
		 *            the instgPty to set
		 */
		public void setInstgPty(InstgPty instgPty) {
			this.InstgPty = instgPty;
		}

		/**
		 * @return the creDtTm
		 */
		public String getCreDtTm() {
			return CreDtTm;
		}

		/**
		 * @param creDtTm
		 *            the creDtTm to set
		 */
		public void setCreDtTm(String creDtTm) {
			this.CreDtTm = creDtTm;
		}

		/**
		 * @param msgId
		 *            the msgId to set
		 */
		public void setMsgId(String msgId) {
			this.MsgId = msgId;
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class InstgPty implements Serializable {

			private static final long serialVersionUID = 4561245285411453064L;
			private String InstgDrctPty = null;
			private String InstgPty = null;

			/**
			 * @return the instgDrctPty
			 */
			public String getInstgDrctPty() {
				return InstgDrctPty;
			}

			/**
			 * @return the instgPty
			 */
			public String getInstgPty() {
				return InstgPty;
			}

			/**
			 * @param instgPty
			 *            the instgPty to set
			 */
			public void setInstgPty(String instgPty) {
				this.InstgPty = instgPty;
			}

			/**
			 * @param instgDrctPty
			 *            the instgDrctPty to set
			 */
			public void setInstgDrctPty(String instgDrctPty) {
				this.InstgDrctPty = instgDrctPty;
			}

			@Override
			public String toString() {
				return "InstgPty{" + "InstgDrctPty='" + InstgDrctPty + '\'' + ", InstgPty='" + InstgPty + '\'' + '}';
			}
		}

		@XmlAccessorType(XmlAccessType.FIELD)
		public static class InstdPty implements Serializable {

			private static final long serialVersionUID = -6339700890116997146L;
			private String InstdDrctPty = null;
			private String InstdPty = null;

			/**
			 * @return the instdDrctPty
			 */
			public String getInstdDrctPty() {
				return InstdDrctPty;
			}

			/**
			 * @return the instdPty
			 */
			public String getInstdPty() {
				return InstdPty;
			}

			/**
			 * @param instdPty
			 *            the instdPty to set
			 */
			public void setInstdPty(String instdPty) {
				this.InstdPty = instdPty;
			}

			/**
			 * @param instdDrctPty
			 *            the instdDrctPty to set
			 */
			public void setInstdDrctPty(String instdDrctPty) {
				this.InstdDrctPty = instdDrctPty;
			}

			@Override
			public String toString() {
				return "InstdPty{" + "InstdDrctPty='" + InstdDrctPty + '\'' + ", InstdPty='" + InstdPty + '\'' + '}';
			}
		}
	}

	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(propOrder = { "MsgCntt" })
	public static class FreeFrmtInf implements Serializable {

		private static final long serialVersionUID = 7212644771608098793L;
		private String MsgCntt = null;

		public String getMsgCntt() {
			return MsgCntt;
		}

		public void setMsgCntt(String msgCntt) {
			MsgCntt = msgCntt;
		}

		@Override
		public String toString() {
			return "FreeFrmtInf{" + MsgCntt + '\'' + '}';
		}
	}

	@Override
	public String signData() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getGrpHdr().getMsgId() + "|");
		sb.append(this.getGrpHdr().getCreDtTm() + "|");
		sb.append(this.getGrpHdr().getInstgPty().getInstgDrctPty() + "|");
		sb.append(this.getGrpHdr().getInstgPty().getInstgPty() + "|");
		sb.append(this.getGrpHdr().getInstdPty().getInstdDrctPty() + "|");
		sb.append(this.getGrpHdr().getInstdPty().getInstdPty() + "|");
		sb.append(this.getFreeFrmtInf().getMsgCntt() + "|");

		return sb.toString();
	}

	@Override
	public String toString() {
		return "CCMS_303_001_02_GetIdFreeFrmtInf{" + "GrpHdr=" + GrpHdr + ", FreeFrmtInf=" + FreeFrmtInf + '}';
	}
}