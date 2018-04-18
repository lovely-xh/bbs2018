package com.galaxy.bbs.domain;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 *<br><b>类描述:</b>
 *<pre>所示PO的父类</pre>
 *@see
 *@since
 */
@SuppressWarnings("serial")
public class BaseDomain implements Serializable
{
	/**
	 * 对象及其属性一行显示
	 */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    /**
     * 对象、属性换行显示
     */
    public String toStringLinefeed() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * 不显示属性名，只显示对象和属性值，在同一行显示
     */
    public String toStringNoField() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.NO_FIELD_NAMES_STYLE);
    }
    
    /**
     * 对象名称简写
     */
    public String toStringShortObject() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    /**
     * 只显示属性值
     */
    public String toStringValueOnly() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
