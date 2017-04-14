package com.rdfs.framework.taglib.field;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import com.rdfs.framework.taglib.bean.Region;
import com.rdfs.framework.taglib.utils.CacheCxtUtil;
import com.rdfs.framework.taglib.utils.StringUtils;

/**
 * 为了使用字典，自定义select标签
 *
 */
public class LabelFieldTag extends TagSupport implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729252097490938995L;
	private String id ;
	private String value;
	private String clazz;
	private String style;
	private String title;
	private String tooltip;
	private String type;
	private String format;
	
	private Map<String, String> dynamicAttributes = new HashMap<String, String>(); 
	
	@Override
    public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try{
			StringBuffer sb = new StringBuffer("<label");
			for(Iterator<String> iter = dynamicAttributes.keySet().iterator();iter.hasNext();){
				String key = iter.next();
				sb.append(" " + key + "='" + dynamicAttributes.get(key) + "'");
			}
			if(!StringUtils.isBlank(this.getId())){
				sb.append(" id='" + this.getId() + "'");
			}
			if(!StringUtils.isBlank(this.getClazz())){
				sb.append(" class='" + this.getClazz() + "'");
			}
			if(!StringUtils.isBlank(this.getTitle())){
				sb.append(" title='" + this.getTitle() + "'");
			}
			if(!StringUtils.isBlank(this.getTooltip())){
				sb.append(" tooltip='" + this.getTooltip() + "'");
			}
			String v = this.getValue();
			if(!StringUtils.isBlank(this.getType())){
				sb.append(" type='" +this.getType()+ "'");
				if(this.getType().equalsIgnoreCase("dict")&& !StringUtils.isBlank(this.getFormat()) && !StringUtils.isBlank(this.getValue())){
					if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
						String desc = CacheCxtUtil.cacheDataService.getDicDesc(this.getFormat(), this.getValue());
						if(!StringUtils.isBlank(desc)){
							v = desc ;
						}
					}
				}
				if(this.getType().equalsIgnoreCase("region") && !StringUtils.isBlank(this.getValue())){
					System.out.println("label CacheCxtUtil.cacheDataService:"+StringUtils.isBlankObj(CacheCxtUtil.cacheDataService));
					if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
						Region region = CacheCxtUtil.cacheDataService.getRegion(this.getValue());
						if(region != null){
							v = region.getRegName();
						}
					}
				}
			}
			if(!StringUtils.isBlank(this.getFormat())){
				sb.append(" format='"+this.getFormat()+"'");
			}
			sb.append(">").append(v).append("</label>");
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
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

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
