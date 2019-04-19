package com.fxbank.tpp.mivs.dto.bocm;

/**
 * @Description: 用于生成错误应答报文
 * @Author: 周勇沩
 * @Date: 2019-04-15 14:44:19
 */
public class REP_ERROR extends REP_BASE {

    @Override
    public String creaFixPack() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.getHeader().creaFixPack());
        return sb.toString();
    }

    @Override
    public void chanFixPack(String pack) {
    }

}