package com.rdfs.framework.taglib.field;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import com.rdfs.framework.taglib.utils.StringUtils;


/**
 * 为了使用字典，自定义select标签
 *
 */
public class NumberFieldTag extends TagSupport implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729252097490938995L;
	private String id ;
	private String name;
	private String value;
	private String clazz;
	private String readonly;
	private String style;
	private String title;
	private String disabled;
	private String tooltip;
	private String minlength;
	private String maxlength;
	private String type;
	private String negative;
	private String renderId;
	
	private Map<String, String> dynamicAttributes = new HashMap<String, String>(); 
	
	@Override
    public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try{
			StringBuffer sb = new StringBuffer("<input type='number' ");
			for(Iterator<String> iter = dynamicAttributes.keySet().iterator();iter.hasNext();){
				String key = iter.next();
				sb.append(" " + key + "='" + dynamicAttributes.get(key) + "'");
			}
			if(!StringUtils.isBlank(this.getId())){
				sb.append(" id='" + this.getId() + "'");
			} else {
				sb.append(" id='number-" + new Date().getTime() + "'");
			}
			if(!StringUtils.isBlank(this.getName())){
				sb.append(" name='" + this.getName() + "'");
			}
			if(!StringUtils.isBlank(this.getReadonly())){
				sb.append(" readonly");
			}
			if(!StringUtils.isBlank(this.getTitle())){
				sb.append(" title='" + this.getTitle() + "'");
			}
			if(!StringUtils.isBlank(this.getRenderId())){
				sb.append(" render-id='" + this.getRenderId() + "'");
			}
			if(!StringUtils.isBlank(this.getClazz())){
				sb.append(" class='" + this.getClazz() + "'");
			}
			if(!StringUtils.isBlank(this.getDisabled())){
				sb.append(" disabled='" + this.getDisabled() + "'");
			}
			if(!StringUtils.isBlank(this.getTooltip())){
				sb.append(" tooltip='" + this.getTooltip() + "'");
			}
			if(!StringUtils.isBlank(this.getType())){
				sb.append(" xtype='" + this.getType() + "'");
			}
			if(!StringUtils.isBlank(this.getNegative())){
				sb.append(" negative='" + this.getNegative() + "'");
			}
			if(!StringUtils.isBlank(this.getMinlength())){
				sb.append(" minlength='" + this.getMinlength() + "'");
			}
			if(!StringUtils.isBlank(this.getMaxlength())){
				sb.append(" maxlength='" + this.getMaxlength() + "'");
			}
			sb.append("'>");
			writer.print(sb.toString());
		} catch(Exception e){
			
		}
		return SKIP_BODY;
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		if(localName.equals("class")){
			setClazz(String.valueOf(value));
		} else {
			if(!StringUtils.isBlankObj(value)){
				dynamicAttributes.put(localName, String.valueOf(value));  
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadOnly(String readonly) {
		this.readonly = readonly;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNegative() {
		return negative;
	}

	public void setNegative(String negative) {
		this.negative = negative;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getRenderId() {
		return renderId;
	}

	public void setRenderId(String renderId) {
		this.renderId = renderId;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMinlength() {
		return minlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}

}
