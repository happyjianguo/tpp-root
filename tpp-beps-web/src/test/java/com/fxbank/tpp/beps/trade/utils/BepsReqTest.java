package com.fxbank.tpp.beps.trade.utils;

import com.fxbank.cip.base.log.MyLog;
import com.fxbank.tpp.beps.pmts.BEPS_351_001_01;
import com.fxbank.tpp.beps.util.PmtsXmlUtil;
import com.fxbank.tpp.beps.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;

/** 
 * @author 叶浦亮
 * @Description: 351,352,353报文测试类
 * @date 2020年2月28日 下午1:53:13 
 */
public class BepsReqTest {
	
	private static Logger logger = LoggerFactory.getLogger(BepsReqTest.class);
	
	public static void main(String[] args) throws IntrospectionException {
	    /*
        BEPS_351_001_01 beps351 = new BEPS_351_001_01(null,0,0,0);
        BeanUtil.fillBean(beps351);
        logger.info("351请求报文");
        logger.info(BeanUtil.objectToXml(beps351));
	     */
        BEPS_351_001_01 beps352 = new BEPS_351_001_01(new MyLog(),20200228,184221,200);
        BeanUtil.fillBean(beps352);
        String xml = BeanUtil.objectToXml((beps352));
        logger.info(xml);
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<Document xmlns=\"urn:cnaps:std:beps:2010:tech:xsd:beps.351.001.01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <PtcSnReq>\n" +
                "        <GrpHdr>\n" +
                "            <MsgId>2020020509167055</MsgId>\n" +
                "            <CreDtTm>2020-02-05T22:39:54</CreDtTm>\n" +
                "            <InstgPty>\n" +
                "                <InstgDrctPty>313131000016</InstgDrctPty>\n" +
                "                <InstgPty>313131000016</InstgPty>\n" +
                "            </InstgPty>\n" +
                "            <InstdPty>\n" +
                "                <InstdDrctPty>313229000008</InstdDrctPty>\n" +
                "                <InstdPty>313229000008</InstdPty>\n" +
                "            </InstdPty>\n" +
                "            <SysCd>BEPS</SysCd>\n" +
                "            <Rmk>备注</Rmk>\n" +
                "        </GrpHdr>\n" +
                "        <CtrctChngInf>\n" +
                "            <ChngTp>CC00</ChngTp>\n" +
                "            <CtrctTp>CO00</CtrctTp>\n" +
                "            <CstmrId>hfZCzOIckE</CstmrId>\n" +
                "            <RegId>330100</RegId>\n" +
                "            <CstmrNm>张三</CstmrNm>\n" +
                "            <UniSocCdtCd>cvgASLielO</UniSocCdtCd>\n" +
                "            <NbOfPmtItms>3</NbOfPmtItms>\n" +
                "            <PmtItms>\n" +
                "                <PmtItmCd>1yo</PmtItmCd>\n" +
                "                <PmtItmCd>z7Y</PmtItmCd>\n" +
                "                <PmtItmCd>fu7</PmtItmCd>\n" +
                "            </PmtItms>\n" +
                "            <CtrctNb>20200228103954</CtrctNb>\n" +
                "            <ReqId>20200228103954</ReqId>\n" +
                "            <Issr>313229000008</Issr>\n" +
                "            <CstmrAcctType>AO00</CstmrAcctType>\n" +
                "            <AcctId>6217000730028446116</AcctId>\n" +
                "            <OncDdctnLmt>CNY650000.00</OncDdctnLmt>\n" +
                "            <CycDdctnNumLmt>5</CycDdctnNumLmt>\n" +
                "            <CtrctDueDt>2020-02-28</CtrctDueDt>\n" +
                "            <CtrctSgnDt>2020-02-28</CtrctSgnDt>\n" +
                "            <EctDt>2020-02-28</EctDt>\n" +
                "            <PyrInf>\n" +
                "                <Nm>张三</Nm>\n" +
                "                <IdTp>IC00</IdTp>\n" +
                "                <Id>110000201912151234</Id>\n" +
                "            </PyrInf>\n" +
                "            <TelNb>13255556666</TelNb>\n" +
                "            <AdrLine>地址地址地址</AdrLine>\n" +
                "            <Rmk>备注备注备注</Rmk>\n" +
                "            <AuthMd>AM00</AuthMd>\n" +
                "            <TmUt>TU00</TmUt>\n" +
                "            <TmSp>1</TmSp>\n" +
                "            <TmDc>扣款时间描述</TmDc>\n" +
                "            <CycDdctnLmt>CNY650000.00</CycDdctnLmt>\n" +
                "            <CtrctAddtlInf>协议附加数据</CtrctAddtlInf>\n" +
                "        </CtrctChngInf>\n" +
                "    </PtcSnReq>\n" +
                "</Document>";

        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                "<Document xmlns=\"urn:cnaps:std:beps:2010:tech:xsd:beps.351.001.01\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" +
                "    <PtcSnReq>\n" +
                "        <GrpHdr>\n" +
                "            <MsgId>2020020509167055</MsgId>\n" +
                "            <CreDtTm>2020-02-05T22:39:54</CreDtTm>\n" +
                "            <InstgPty>\n" +
                "                <InstgDrctPty>313131000016</InstgDrctPty>\n" +
                "                <InstgPty>313131000016</InstgPty>\n" +
                "            </InstgPty>\n" +
                "            <InstdPty>\n" +
                "                <InstdDrctPty>313229000008</InstdDrctPty>\n" +
                "                <InstdPty>313229000008</InstdPty>\n" +
                "            </InstdPty>\n" +
                "            <SysCd>BEPS</SysCd>\n" +
                "            <Rmk>备注</Rmk>\n" +
                "        </GrpHdr>\n" +
                "        <CtrctChngInf>\n" +
                "            <ChngTp>CC00</ChngTp>\n" +
                "            <CtrctTp>CO00</CtrctTp>\n" +
                "            <CstmrId>hfZCzOIckE</CstmrId>\n" +
                "            <RegId>330100</RegId>\n" +
                "            <CstmrNm>张三</CstmrNm>\n" +
                "            <UniSocCdtCd>cvgASLielO</UniSocCdtCd>\n" +
                "            <NbOfPmtItms>3</NbOfPmtItms>\n" +
                "            <PmtItms>\n" +
                "                <PmtItmCd>1yo</PmtItmCd>\n" +
                "                <PmtItmCd>z7Y</PmtItmCd>\n" +
                "                <PmtItmCd>fu7</PmtItmCd>\n" +
                "            </PmtItms>\n" +
                "            <CtrctNb>20200228103954</CtrctNb>\n" +
                "            <ReqId>20200228103954</ReqId>\n" +
                "            <Issr>313229000008</Issr>\n" +
                "            <CstmrAcctType>AO00</CstmrAcctType>\n" +
                "            <AcctId>6217000730028446116</AcctId>\n" +
                "            <OncDdctnLmt>CNY650000.00</OncDdctnLmt>\n" +
                "            <CycDdctnNumLmt>5</CycDdctnNumLmt>\n" +
                "            <CtrctDueDt>2020-02-28</CtrctDueDt>\n" +
                "            <CtrctSgnDt>2020-02-28</CtrctSgnDt>\n" +
                "            <EctDt>2020-02-28</EctDt>\n" +
                "            <PyrInf>\n" +
                "                <Nm>张三</Nm>\n" +
                "                <IdTp>IC00</IdTp>\n" +
                "                <Id>110000201912151234</Id>\n" +
                "            </PyrInf>\n" +
                "            <TelNb>13255556666</TelNb>\n" +
                "            <AdrLine>地址地址地址</AdrLine>\n" +
                "            <Rmk>备注备注备注</Rmk>\n" +
                "            <AuthMd>AM00</AuthMd>\n" +
                "            <TmUt>TU00</TmUt>\n" +
                "            <TmSp>1</TmSp>\n" +
                "            <TmDc>扣款时间描述</TmDc>\n" +
                "            <CycDdctnLmt>CNY650000.00</CycDdctnLmt>\n" +
                "            <CtrctAddtlInf>协议附加数据</CtrctAddtlInf>\n" +
                "        </CtrctChngInf>\n" +
                "    </PtcSnReq>\n" +
                "</Document>";
        Object obj = PmtsXmlUtil.xmlToObject(com.fxbank.tpp.beps.dto.pmts.BEPS_351_001_01.class, xml);
        logger.info("");

        /*
        BEPS_353_001_01 beps353 = new BEPS_353_001_01(null,0,0,0);
        BeanUtil.fillBean(beps353);
        logger.info("353请求报文");
        logger.info(BeanUtil.objectToXml(beps353));
         */

        /*
      com.fxbank.tpp.beps.dto.pmts.BEPS_352_001_01
         */
	}

}
