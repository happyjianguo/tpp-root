package com.fxbank.tpp.tcex.TEST;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.xml.bind.Element;

import org.w3c.dom.Document;

import com.tienon.io.tcp.RefbdcTcpClient;

public class TcpClient
{
  private static String TxStatus;
  private static String RtnMessage;

  public static void main(String[] args)
    throws Exception
  {
    String bankCode = "313157";
    String ip = "128.192.179.124";
    int port = 8282;

    StringBuffer sb = new StringBuffer();
    sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>").append("\n");
    sb.append("<message>").append("\n");
    sb.append("<head>").append("\n");
    sb.append("<field name=\"SendDate\">20160506</field>").append("\n");
    sb.append("<field name=\"SendTime\">113930</field>").append("\n");
    sb.append("<field name=\"SendSeqNo\">12341234</field>").append("\n");
    sb.append("<field name=\"TxUnitNo\">01</field>").append("\n");
    sb.append("<field name=\"SendNode\">313157</field>").append("\n");
    sb.append("<field name=\"TxCode\">SBDC301</field>").append("\n");
    sb.append("<field name=\"ReceiveNode\">105000</field>").append("\n");
    sb.append("<field name=\"CustNo\">001</field>").append("\n");
    sb.append("<field name=\"OperNo\">001</field>").append("\n");
    sb.append("</head>").append("\n");
    sb.append("<body>").append("\n");
    sb.append("<field name=\"BankCode\">");
    sb.append(bankCode);

    sb.append("</field>").append("\n");
    sb.append("</body>").append("\n");
    sb.append("</message>");

    System.out.println("Connecting......");

    RefbdcTcpClient cc = new RefbdcTcpClient(ip, port, "313157", 60000);
    byte[] ret;
    try
    {
      ret = cc.login(sb.toString().getBytes());
    }
    catch (Exception e1)
    {
      System.out.println("Connection Error!");
      return;
    }
    String response = new String(ret,"GBK");
    System.out.println(response);
//    String key = getSessionKey(response);
/*
    if ("1".equals(TxStatus)) {
      System.out.println(RtnMessage);
    }
    if ("0".equals(TxStatus))
    {
      File keyfile = new File("key.txt");

      if (!keyfile.exists()) {
        keyfile.delete();
      }
      BufferedWriter bufw = null;
      try
      {
        OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(keyfile), 
          "UTF-8");
        bufw = new BufferedWriter(write);
        bufw.write(key);
      } catch (Exception e) {
        System.out.println("字符串写入到文件失败");

        if (bufw != null)
          try {
            bufw.close();
          } catch (IOException e) {
            System.out.println("关闭字符输出流失败");
          }
      }
      finally
      {
        if (bufw != null) {
          try {
            bufw.close();
          } catch (IOException e) {
            System.out.println("关闭字符输出流失败");
          }
        }
      }
      System.out.println("key文件路径为：" + keyfile.getAbsolutePath());
    }*/
  }

}