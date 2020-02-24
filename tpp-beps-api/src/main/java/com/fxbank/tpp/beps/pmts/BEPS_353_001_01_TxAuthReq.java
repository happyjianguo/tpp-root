package com.fxbank.tpp.beps.pmts;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/** 
* @Description: 客户身份认证回执报文
* @author 周勇沩
* @date 2020/2/21 10:12:54 
*  
*/
public class BEPS_353_001_01_TxAuthReq implements Serializable, SIGN_DATA {

	private static final long serialVersionUID = 7027401474114391377L;
	
	private GrpHdr GrpHdr = new GrpHdr();
	
	private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();
	
	private CtrctChngRspnInf CtrctChngRspnInf = new CtrctChngRspnInf();
	
	 @Override
	    public String toString() {
	        return "客户身份认证回执报文[BEPS_353_001_01][" + this.GrpHdr.getMsgId() + "][" + this.CtrctChngRspnInf.getCtrctNb() + "][" + this.CtrctChngRspnInf.getAuthCnt() + "]";
	    }

	public GrpHdr getGrpHdr() {
		return GrpHdr;
	}

	public void setGrpHdr(GrpHdr grpHdr) {
		GrpHdr = grpHdr;
	}

	public OrgnlGrpHdr getOrgnlGrpHdr() {
		return OrgnlGrpHdr;
	}

	public void setOrgnlGrpHdr(OrgnlGrpHdr orgnlGrpHdr) {
		OrgnlGrpHdr = orgnlGrpHdr;
	}

	public CtrctChngRspnInf getCtrctChngRspnInf() {
		return CtrctChngRspnInf;
	}

	public void setCtrctChngRspnInf(CtrctChngRspnInf ctrctChngRspnInf) {
		CtrctChngRspnInf = ctrctChngRspnInf;
	}	
	
	
	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"MsgId", "CreDtTm", "InstgPty", "InstdPty", "SysCd", "Rmk"})
    public static class GrpHdr implements Serializable {

		private static final long serialVersionUID = 7971032175761100714L;
		private String MsgId;
        private String CreDtTm;
        private InstgPty InstgPty = new InstgPty();
        private InstdPty InstdPty = new InstdPty();
        private String SysCd;
        private String Rmk;

        public String getMsgId() {
            return MsgId;
        }

        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        public String getCreDtTm() {
            return CreDtTm;
        }

        public void setCreDtTm(String creDtTm) {
            this.CreDtTm = creDtTm;
        }

        public String getSysCd() {
            return SysCd;
        }

        public void setSysCd(String sysCd) {
            this.SysCd = sysCd;
        }

        public String getRmk() {
            return Rmk;
        }

        public void setRmk(String rmk) {
            this.Rmk = rmk;
        }

        public InstgPty getInstgPty() {
            return InstgPty;
        }

        public void setInstgPty(InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        public InstdPty getInstdPty() {
            return InstdPty;
        }

        public void setInstdPty(InstdPty instdPty) {
            this.InstdPty = instdPty;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "GrpHdr.InstgPty")
        public static class InstgPty implements Serializable {

			private static final long serialVersionUID = -7561835102230653918L;
			private String InstgDrctPty;
            private String InstgPty;

            public String getInstgDrctPty() {
                return InstgDrctPty;
            }

            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }

            public String getInstgPty() {
                return InstgPty;
            }

            public void setInstgPty(String instgPty) {
                this.InstgPty = instgPty;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "GrpHdr.InstdPty")
        public static class InstdPty implements Serializable {

			private static final long serialVersionUID = -8468615696562536092L;
			private String InstdDrctPty;
            private String InstdPty;

            public String getInstdDrctPty() {
                return InstdDrctPty;
            }

            public void setInstdDrctPty(String instdDrctPty) {
                this.InstdDrctPty = instdDrctPty;
            }

            public String getInstdPty() {
                return InstdPty;
            }

            public void setInstdPty(String instdPty) {
                this.InstdPty = instdPty;
            }
        }
    }
    
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"OrgnlMsgId", "OrgnlInstgPty", "OrgnlMT"})
    public static class OrgnlGrpHdr implements Serializable {

		private static final long serialVersionUID = -7856406155601141430L;
		private String OrgnlMsgId;
        private String OrgnlInstgPty;
        private String OrgnlMT;

        public String getOrgnlMsgId() {
            return OrgnlMsgId;
        }

        public void setOrgnlMsgId(String orgnlMsgId) {
            this.OrgnlMsgId = orgnlMsgId;
        }

        public String getOrgnlInstgPty() {
            return OrgnlInstgPty;
        }

        public void setOrgnlInstgPty(String orgnlInstgPty) {
            this.OrgnlInstgPty = orgnlInstgPty;
        }

        public String getOrgnlMT() {
            return OrgnlMT;
        }

        public void setOrgnlMT(String orgnlMT) {
            this.OrgnlMT = orgnlMT;
        }
    }
	
	
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"CtrctNb", "OrgnlReqId", "AuthCnt", "Rmk"})
    public static class CtrctChngRspnInf implements Serializable {

		private static final long serialVersionUID = -3564252489098769732L;

    	private String CtrctNb;
    	private String OrgnlReqId;
    	private String AuthCnt;
    	private String Rmk;
    	
		public String getCtrctNb() {
			return CtrctNb;
		}
		public void setCtrctNb(String ctrctNb) {
			CtrctNb = ctrctNb;
		}
		public String getOrgnlReqId() {
			return OrgnlReqId;
		}
		public void setOrgnlReqId(String orgnlReqId) {
			OrgnlReqId = orgnlReqId;
		}
		public String getAuthCnt() {
			return AuthCnt;
		}
		public void setAuthCnt(String authCnt) {
			AuthCnt = authCnt;
		}
		public String getRmk() {
			return Rmk;
		}
		public void setRmk(String rmk) {
			Rmk = rmk;
		}
	
    }
	
	
    @Override
    public String signData() {
    	StringBuffer sb = new StringBuffer();
        //GrpHdr组包
        sb.append(this.getGrpHdr().getMsgId() + "|");
        sb.append(this.getGrpHdr().getCreDtTm() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdDrctPty() + "|");
        sb.append(this.getGrpHdr().getInstdPty().getInstdPty() + "|");
        sb.append(this.getGrpHdr().getSysCd() + "|");
        sb.append(this.getGrpHdr().getRmk() + "|");
        //OrgnlGrpHdr组包
        sb.append(this.getOrgnlGrpHdr().getOrgnlMsgId() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlInstgPty() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMT() + "|");
        //CtrctChngRspnInf组包
        sb.append(this.CtrctChngRspnInf.getCtrctNb() == null ? "" : this.CtrctChngRspnInf.getCtrctNb() + "|");
        sb.append(this.CtrctChngRspnInf.getOrgnlReqId() == null ? "" : this.CtrctChngRspnInf.getOrgnlReqId() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthCnt() == null ? "" : this.CtrctChngRspnInf.getAuthCnt() + "|");
        sb.append(this.CtrctChngRspnInf.getRmk() == null ? "" : this.CtrctChngRspnInf.getRmk() + "|");
        return sb.toString();
    }

}
