package com.fxbank.tpp.beps.pmts;

/**
  * @description: 生成待签名数据接口
  * @author     : 周勇沩
  * @Date       : 2019/4/20 9:33
  */
public interface SIGN_DATA {
    
    /**
     * @description: 需要签名则返回实际签名数据，不需要签名则返回null
     * @Date       : 2019/4/20 9:48
     */
    public String signData();

}