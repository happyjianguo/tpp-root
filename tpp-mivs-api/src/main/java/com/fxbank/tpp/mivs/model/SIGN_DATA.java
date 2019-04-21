package com.fxbank.tpp.mivs.model;

/**
 * @Description: 生成待签名数据接口
 * @Author: 周勇沩
 * @Date: 2019-04-20 09:33:14
 */
public interface SIGN_DATA {
    
    /**
     * @Description: 需要签名则返回实际签名数据，不需要签名则返回null
     * @Author: 周勇沩
     * @Date: 2019-04-20 09:48:32
     */
    public String signData();

}