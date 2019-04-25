package com.fxbank.tpp.mivs.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * @Description: 手机号码联网核查申请报文主节点
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:51:24
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "MsgHdr", "OrgnlBizQry","Rspsn" })
public class MIVS_321_001_01_RtrIdVrfctn implements Serializable,SIGN_DATA{

    private static final long serialVersionUID = 2906655856390529936L;
    private MsgHdr MsgHdr = new MsgHdr();
    private OrgnlBizQry OrgnlBizQry = new OrgnlBizQry();
    private Rspsn Rspsn = new Rspsn();

    @Override
    public String toString() {
        return "手机号码联网核查申请报文[MIVS_321_001_01]";
    }

    /**
     * @return the msgHdr
     */
    public MsgHdr getMsgHdr() {
        return MsgHdr;
    }

    /**
     * @param msgHdr the msgHdr to set
     */
    public void setMsgHdr(MsgHdr msgHdr) {
        this.MsgHdr = msgHdr;
    }

    public OrgnlBizQry getOrgnlBizQry() {
		return OrgnlBizQry;
	}

	public void setOrgnlBizQry(OrgnlBizQry orgnlBizQry) {
		OrgnlBizQry = orgnlBizQry;
	}

	public Rspsn getRspsn() {
		return Rspsn;
	}

	public void setRspsn(Rspsn rspsn) {
		Rspsn = rspsn;
	}

	@XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "MsgId", "CreDtTm", "InstgPty" })
    public static class MsgHdr implements Serializable{
        private static final long serialVersionUID = -581103924009687799L;
        private String MsgId = null;
        private String CreDtTm = null;
        private InstgPty InstgPty = new InstgPty();
        
        /**
         * @return the msgId
         */
        public String getMsgId() {
            return MsgId;
        }

        /**
         * @return the instgPty
         */
        public InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(InstgPty InstgPty) {
            this.InstgPty = InstgPty;
        }

        /**
         * @return the creDtTm
         */
        public String getCreDtTm() {
            return CreDtTm;
        }

        /**
         * @param creDtTm the creDtTm to set
         */
        public void setCreDtTm(String creDtTm) {
            this.CreDtTm = creDtTm;
        }

        /**
         * @param msgId the msgId to set
         */
        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 7162761168675476519L;
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
             * @param instgPty the instgPty to set
             */
            public void setInstgPty(String InstgPty) {
                this.InstgPty = InstgPty;
            }

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }
        }

        }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(namespace="aa",name= "OrgnlBizQry",propOrder = { "MsgId", "InstgPty" })
    public static class OrgnlBizQry implements Serializable{
        private static final long serialVersionUID = -581103924009687799L;
        private String MsgId = null;
        private InstgPty InstgPty = new InstgPty();

        /**
         * @return the msgId
         */
        public String getMsgId() {
            return MsgId;
        }

        /**
         * @return the instgPty
         */
        public InstgPty getInstgPty() {
            return InstgPty;
        }

        /**
         * @param instgPty the instgPty to set
         */
        public void setInstgPty(InstgPty instgPty) {
            this.InstgPty = instgPty;
        }

        /**
         * @param msgId the msgId to set
         */
        public void setMsgId(String msgId) {
            this.MsgId = msgId;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        public static class InstgPty implements Serializable{
            private static final long serialVersionUID = 7162761168675476519L;
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
             * @param instgPty the instgPty to set
             */
            public void setInstgPty(String instgPty) {
                this.InstgPty = instgPty;
            }

            /**
             * @param instgDrctPty the instgDrctPty to set
             */
            public void setInstgDrctPty(String instgDrctPty) {
                this.InstgDrctPty = instgDrctPty;
            }
        }

        }
    
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = { "VrfctnInf" })
    public static class Rspsn implements Serializable{
        private static final long serialVersionUID = -581103924009687799L;
        private VrfctnInf VrfctnInf = new VrfctnInf();

      
        public VrfctnInf getVrfctnInf() {
			return VrfctnInf;
		}


		public void setVrfctnInf(VrfctnInf vrfctnInf) {
			VrfctnInf = vrfctnInf;
		}


		@XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(propOrder = { "MobNb","Rslt","MobCrr","LocMobNb","LocNmMobNb","CdTp","Sts" })
        public static class VrfctnInf implements Serializable{
            private static final long serialVersionUID = 7162761168675476519L;
			private String MobNb = null;
            private String Rslt = null;
            private String MobCrr = null;
            private String LocMobNb = null;
            private String LocNmMobNb = null;
            private String CdTp = null;
            private String Sts = null;
			public String getMobNb() {
				return MobNb;
			}
			public void setMobNb(String mobNb) {
				MobNb = mobNb;
			}
			public String getRslt() {
				return Rslt;
			}
			public void setRslt(String rslt) {
				Rslt = rslt;
			}
			public String getMobCrr() {
				return MobCrr;
			}
			public void setMobCrr(String mobCrr) {
				MobCrr = mobCrr;
			}
			public String getLocMobNb() {
				return LocMobNb;
			}
			public void setLocMobNb(String locMobNb) {
				LocMobNb = locMobNb;
			}
			public String getLocNmMobNb() {
				return LocNmMobNb;
			}
			public void setLocNmMobNb(String locNmMobNb) {
				LocNmMobNb = locNmMobNb;
			}
			public String getCdTp() {
				return CdTp;
			}
			public void setCdTp(String cdTp) {
				CdTp = cdTp;
			}
			public String getSts() {
				return Sts;
			}
			public void setSts(String sts) {
				Sts = sts;
			}

            
        }

        }
 
    



    @Override
    public String signData() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.getMsgHdr().getMsgId() + "|");
        sb.append(this.getMsgHdr().getCreDtTm() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getMsgHdr().getInstgPty().getInstgPty() + "|");
        sb.append(this.getOrgnlBizQry().getMsgId() + "|");
        sb.append(this.getOrgnlBizQry().getInstgPty().getInstgDrctPty() + "|");
        sb.append(this.getOrgnlBizQry().getInstgPty().getInstgPty() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getMobNb() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getRslt() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getMobCrr() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getLocMobNb() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getLocNmMobNb() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getCdTp() + "|");
        sb.append(this.getRspsn().getVrfctnInf().getSts() + "|");
        return sb.toString();
    }

}