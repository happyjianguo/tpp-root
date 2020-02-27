package com.fxbank.tpp.beps.pmts;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/** 
* @Description: 实时客户支付协议管理报文
* @author 周勇沩
* @date 2020/2/21 10:12:54 
*  
*/
public class BEPS_352_001_01_ResFrPtcSn extends REP_BASE implements Serializable, SIGN_DATA {

	private static final long serialVersionUID = 145626186505490648L;

    private static final String MESGTYPE = "beps.352.001.01";

    private GrpHdr GrpHdr = new GrpHdr();
	
	private OrgnlGrpHdr OrgnlGrpHdr = new OrgnlGrpHdr();
	
	private CtrctChngRspnInf CtrctChngRspnInf = new  CtrctChngRspnInf();
	
    @Override
    public String toString() {
        return "实时客户支付协议管理报文[BEPS_352_001_01][" + this.GrpHdr.getMsgId() + "][" + this.CtrctChngRspnInf.getRspnInf() + "][" + this.CtrctChngRspnInf.getCtrctNb() + "]";
    }

    public BEPS_352_001_01_ResFrPtcSn(){
        super.mesgType = MESGTYPE;
    }
	
    public BEPS_352_001_01_ResFrPtcSn.GrpHdr getGrpHdr() {
        return GrpHdr;
    }

    public void setGrpHdr(BEPS_352_001_01_ResFrPtcSn.GrpHdr grpHdr) {
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

		private static final long serialVersionUID = -7164625084979709481L;
		
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

			private static final long serialVersionUID = -6877011574990490256L;
			
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

			private static final long serialVersionUID = -7908652180886639689L;
			
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

		private static final long serialVersionUID = -7911993035001982490L;
		
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
    @XmlType(propOrder = {"RspnInf", "CtrctRtrFlg", "CtrctNb", "OrgnlReqId", "AuthMd", 
    		"AuthChl", "AuthUrl","Rmk"})
    public static class CtrctChngRspnInf implements Serializable {

		private static final long serialVersionUID = -5865956041555846419L;

		private RspnInf RspnInf = new RspnInf();
        private String CtrctRtrFlg;
        private String CtrctNb;
        private String OrgnlReqId;
        private String AuthMd;
        private String AuthChl;
        private String AuthUrl;
        private String Rmk;
        
		public RspnInf getRspnInf() {
			return RspnInf;
		}
		public void setRspnInf(RspnInf rspnInf) {
			RspnInf = rspnInf;
		}
		public String getCtrctRtrFlg() {
			return CtrctRtrFlg;
		}
		public void setCtrctRtrFlg(String ctrctRtrFlg) {
			CtrctRtrFlg = ctrctRtrFlg;
		}
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
		public String getAuthMd() {
			return AuthMd;
		}
		public void setAuthMd(String authMd) {
			AuthMd = authMd;
		}
		public String getAuthChl() {
			return AuthChl;
		}
		public void setAuthChl(String authChl) {
			AuthChl = authChl;
		}
		public String getAuthUrl() {
			return AuthUrl;
		}
		public void setAuthUrl(String authUrl) {
			AuthUrl = authUrl;
		}
		public String getRmk() {
			return Rmk;
		}
		public void setRmk(String rmk) {
			Rmk = rmk;
		}
		
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "CtrctChngRspnInf.RspnInf")
        public static class RspnInf implements Serializable {

			private static final long serialVersionUID = 7938983447603213901L;
			
			private String Sts;
            private String RjctCd;
            private String RjctInf;
            private String PrcPty;
            
			public String getSts() {
				return Sts;
			}
			public void setSts(String sts) {
				Sts = sts;
			}
			public String getRjctCd() {
				return RjctCd;
			}
			public void setRjctCd(String rjctCd) {
				RjctCd = rjctCd;
			}
			public String getRjctInf() {
				return RjctInf;
			}
			public void setRjctInf(String rjctInf) {
				RjctInf = rjctInf;
			}
			public String getPrcPty() {
				return PrcPty;
			}
			public void setPrcPty(String prcPty) {
				PrcPty = prcPty;
			}
    
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
        //OrgnlGrpHdr
        sb.append(this.getOrgnlGrpHdr().getOrgnlMsgId() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlInstgPty() + "|");
        sb.append(this.getOrgnlGrpHdr().getOrgnlMT() + "|");
        //CtrctChngRspnInf组包
        sb.append(this.CtrctChngRspnInf.getRspnInf().getSts() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getSts() + "|");
        sb.append(this.CtrctChngRspnInf.getRspnInf().getRjctCd() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getRjctCd() + "|");
        sb.append(this.CtrctChngRspnInf.getRspnInf().getRjctInf() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getRjctInf() + "|");
        sb.append(this.CtrctChngRspnInf.getRspnInf().getPrcPty() == null ? "" : this.CtrctChngRspnInf.getRspnInf().getPrcPty() + "|");
        sb.append(this.CtrctChngRspnInf.getCtrctRtrFlg() == null ? "" : this.CtrctChngRspnInf.getCtrctRtrFlg() + "|");
        sb.append(this.CtrctChngRspnInf.getCtrctNb() == null ? "" : this.CtrctChngRspnInf.getCtrctNb() + "|");
        sb.append(this.CtrctChngRspnInf.getOrgnlReqId() == null ? "" : this.CtrctChngRspnInf.getOrgnlReqId() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthMd() == null ? "" : this.CtrctChngRspnInf.getAuthMd() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthChl() == null ? "" : this.CtrctChngRspnInf.getAuthChl() + "|");
        sb.append(this.CtrctChngRspnInf.getAuthUrl() == null ? "" : this.CtrctChngRspnInf.getAuthUrl() + "|");
        sb.append(this.CtrctChngRspnInf.getRmk() == null ? "" : this.CtrctChngRspnInf.getRmk() + "|");
        return sb.toString();
    }

}
