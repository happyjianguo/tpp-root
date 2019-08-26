/**   
* @Title: REP_FRMS.java 
* @Package com.fxbank.tpp.frms.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author YePuLiang
* @date 2019年8月23日 下午2:04:35 
* @version V1.0   
*/
package com.fxbank.tpp.frms.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/** 
* @ClassName: REP_FRMS 
* @Description: 风险监控报文响应
* @author YePuLiang
* @date 2019年8月23日 下午2:04:35 
*  
*/
public class REP_FRMS implements Serializable{

	private static final long serialVersionUID = 6422520431794817368L;

	/**
	 * 返回码
	 */
	@JSONField(name="retCode")
	private String retCode;
	/**
	 * 风险分值
	 */
	@JSONField(name="score")
	private String score;
	
	/**
	 *风险项
	 */
	@JSONField(name="risks")
	private List<Risk> risks;
	/**
	 *风险项
	 */
	@JSONField(name="items")
	private List<Item> items;
	/**
	 *风险项
	 */
	@JSONField(name="trusts")
	private List<Trust> trusts;

	/**
	 * uuid
	 */
	@JSONField(name="uuid")
	private String uuid;
	
	/**
	 * 可信策略
	 */
	@JSONField(name="customization")
	private HashMap<String,String> customization;
	
	
	
	
	public static class VerifyPolicy implements Serializable{
		
		
		/**
		 * @字段：serialVersionUID
		 * @功能描述：
		 * @创建人：qixingfu
		 * @创建时间：2019年8月2日下午4:12:37
		 */
		
		private static final long serialVersionUID = 1004804921647688692L;
		@JSONField(name="@type")
		private String type;
		/**
		 * 验证策略编码
		 */
		@JSONField(name="code")
		private String code;
		
		/**
		 * 验证策略名称
		 */
		@JSONField(name="name")
		private String name;
		
		/**
		 * 失败管控
		 */
		@JSONField(name="failControl")
		private String failControl;
		
		/**
		 * 成功管控
		 */
		@JSONField(name="succControl")
		private String succControl;
		
		/**
		 * 验证策略优先级
		 */
		@JSONField(name="priority")
		private String priority;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getFailControl() {
			return failControl;
		}

		public void setFailControl(String failControl) {
			this.failControl = failControl;
		}

		public String getSuccControl() {
			return succControl;
		}

		public void setSuccControl(String succControl) {
			this.succControl = succControl;
		}

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public static class Risk implements Serializable{
		
		
		/**
		 * @字段：serialVersionUID
		 * @功能描述：
		 * @创建人：qixingfu
		 * @创建时间：2019年8月2日下午2:29:57
		 */
		
		private static final long serialVersionUID = 1574920381372434850L;
		
		@JSONField(name="@type")
		private String type;

		/**
		 * 备注
		 */
		@JSONField(name="comments")
		private String comments;
		
		/**
		 * 规则标签
		 */
		@JSONField(name="customization")
		private String customization;
		
		/**
		 * 动态分
		 */
		@JSONField(name="dynamicScore")
		private String dynamicScore;
		
		
		/**
		 * 通知策略
		 */
		@JSONField(name="notifyPolicies")
		private String notifyPolicies;
		
		/**
		 * 风险类型
		 */
		@JSONField(name="riskTypes")
		private String riskTypes;
		
		/**
		 * 风险等级
		 */
		@JSONField(name="ruleLevel")
		private String ruleLevel;
		
		/**
		 * 规则名称
		 */
		@JSONField(name="ruleName")
		private String ruleName;
		
		/**
		 * 规则包名称
		 */
		@JSONField(name="rulePackageName")
		private String rulePackageName;
		
		/**
		 * 规则顺序
		 */
		@JSONField(name="ruleSeq")
		private String ruleSeq;
		
		/**
		 * 规则状态
		 */
		@JSONField(name="ruleStatus")
		private String ruleStatus;
		
		/**
		 * 规则类型
		 */
		@JSONField(name="ruleType")
		private String ruleType;
		
		/**
		 * 规则分值
		 */
		@JSONField(name="score")
		private String score;
		
		/**
		 * 验证策略
		 */
		@JSONField(name="verifyPolicy")
		private String verifyPolicy;
		
		/**
		 * 权重
		 */
		@JSONField(name="weight")
		private String weight;

		public String getComments() {
			return comments;
		}

		public void setComments(String comments) {
			this.comments = comments;
		}

		public String getCustomization() {
			return customization;
		}

		public void setCustomization(String customization) {
			this.customization = customization;
		}

		public String getDynamicScore() {
			return dynamicScore;
		}

		public void setDynamicScore(String dynamicScore) {
			this.dynamicScore = dynamicScore;
		}

		public String getNotifyPolicies() {
			return notifyPolicies;
		}

		public void setNotifyPolicies(String notifyPolicies) {
			this.notifyPolicies = notifyPolicies;
		}

		public String getRiskTypes() {
			return riskTypes;
		}

		public void setRiskTypes(String riskTypes) {
			this.riskTypes = riskTypes;
		}

		public String getRuleLevel() {
			return ruleLevel;
		}

		public void setRuleLevel(String ruleLevel) {
			this.ruleLevel = ruleLevel;
		}

		public String getRuleName() {
			return ruleName;
		}

		public void setRuleName(String ruleName) {
			this.ruleName = ruleName;
		}

		public String getRulePackageName() {
			return rulePackageName;
		}

		public void setRulePackageName(String rulePackageName) {
			this.rulePackageName = rulePackageName;
		}

		public String getRuleSeq() {
			return ruleSeq;
		}

		public void setRuleSeq(String ruleSeq) {
			this.ruleSeq = ruleSeq;
		}

		public String getRuleStatus() {
			return ruleStatus;
		}

		public void setRuleStatus(String ruleStatus) {
			this.ruleStatus = ruleStatus;
		}

		public String getRuleType() {
			return ruleType;
		}

		public void setRuleType(String ruleType) {
			this.ruleType = ruleType;
		}

		public String getScore() {
			return score;
		}

		public void setScore(String score) {
			this.score = score;
		}

		public String getVerifyPolicy() {
			return verifyPolicy;
		}

		public void setVerifyPolicy(String verifyPolicy) {
			this.verifyPolicy = verifyPolicy;
		}

		public String getWeight() {
			return weight;
		}

		public void setWeight(String weight) {
			this.weight = weight;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
	}
	
	public static class NotifyPolicie implements Serializable{
		
		
		/**
		 * @字段：serialVersionUID
		 * @功能描述：
		 * @创建人：qixingfu
		 * @创建时间：2019年8月2日下午1:44:31
		 */
		
		private static final long serialVersionUID = 7722663108528860601L;
		
		@JSONField(name="@type")
		private String type;

		/**
		 * 通知策略编号
		 */
		@JSONField(name="code")
		private String code;
		
		/**
		 * 通知策略名称
		 */
		@JSONField(name="name")
		private String name;
		
		/**
		 * 通知策略优先级
		 */
		@JSONField(name="priority")
		private String priority;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPriority() {
			return priority;
		}

		public void setPriority(String priority) {
			this.priority = priority;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	public static class Customization implements Serializable{
		
		
		/**
		 * @字段：serialVersionUID
		 * @功能描述：
		 * @创建人：qixingfu
		 * @创建时间：2019年8月2日下午2:29:48
		 */
		
		private static final long serialVersionUID = -3825039885425636771L;
		
		@JSONField(name="@type")
		private String type;

		/**
		 * 风险名称
		 */
		@JSONField(name="riskName")
		private String riskName;
		
		/**
		 * 风险名称
		 */
		@JSONField(name="riskLevel")
		private String riskLevel;

		public String getRiskName() {
			return riskName;
		}

		public void setRiskName(String riskName) {
			this.riskName = riskName;
		}

		public String getRiskLevel() {
			return riskLevel;
		}

		public void setRiskLevel(String riskLevel) {
			this.riskLevel = riskLevel;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
	
	public static class Item implements Serializable{

		
		/**
		 * @字段：serialVersionUID
		 * @功能描述：
		 * @创建人：qixingfu
		 * @创建时间：2019年8月2日下午4:52:00
		 */
		
		private static final long serialVersionUID = -8689689476793177789L;
		
	}
	public static class Trust implements Serializable{

		
		/**
		 * @字段：serialVersionUID
		 * @功能描述：
		 * @创建人：qixingfu
		 * @创建时间：2019年8月2日下午4:57:21
		 */
		
		private static final long serialVersionUID = -707547323594161079L;
		
		
		
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<Risk> getRisks() {
		return risks;
	}

	public void setRisks(List<Risk> risks) {
		this.risks = risks;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public List<Trust> getTrusts() {
		return trusts;
	}

	public void setTrusts(List<Trust> trusts) {
		this.trusts = trusts;
	}

	public HashMap<String, String> getCustomization() {
		return customization;
	}

	public void setCustomization(HashMap<String, String> customization) {
		this.customization = customization;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
