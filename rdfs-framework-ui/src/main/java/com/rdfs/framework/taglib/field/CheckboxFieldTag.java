package com.rdfs.framework.taglib.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.DynamicAttributes;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.rdfs.framework.taglib.bean.DictItem;
import com.rdfs.framework.taglib.utils.CacheCxtUtil;
import com.rdfs.framework.taglib.utils.StringUtils;



/**
 * 为了使用字典，自定义radio标签
 *
 */
public class CheckboxFieldTag extends TagSupport implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729252097490938995L;
	private String id ;
	private String name;
	private String value;
	private String readonly;
	private String style;
	private String title;
	private String disabled;
	private String tooltip;
	private Object dicField;
	private String dicFilter;
	
	private Map<String, String> dynamicAttributes = new HashMap<String, String>(); 
	
	@Override
    public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		
		try{
			StringBuffer builder = new StringBuffer("<input type='checkbox'");
			for(Iterator<String> iter = dynamicAttributes.keySet().iterator();iter.hasNext();){
				String key = iter.next();
				builder.append(" " + key + "='" + dynamicAttributes.get(key) + "'");
			}
			if(!StringUtils.isBlank(this.getId())){
				builder.append(" id='" + this.getId() + "'");
			}
			if(!StringUtils.isBlank(this.getName())){
				builder.append(" name='" + this.getName() + "'");
			}
			if(!StringUtils.isBlank(this.getTitle())){
				builder.append(" title='" + this.getTitle() + "'");
			}
			if(!StringUtils.isBlank(this.getStyle())){
				builder.append(" style='" + this.getStyle() + "'");
			}
			List<String> values = new ArrayList<>();
			if(!StringUtils.isBlank(this.getValue())){
				for(String v : this.getValue().split(",")){
					values.add(v);
				}
			}
			if(!StringUtils.isBlank(this.getReadonly())){
				builder.append(" readonly");
			}
			if(!StringUtils.isBlank(this.getDisabled())){
				builder.append(" disabled='" + this.getDisabled() + "'");
			}
			if(!StringUtils.isBlank(this.getTooltip())){
				builder.append(" tooltip='" + this.getTooltip() + "'");
			}
			
			StringBuffer sb = new StringBuffer();
			if(this.getDicField() != null && !StringUtils.isBlank(this.getDicField().toString())){
				boolean flag = true;
				List<String> dictCodes = new ArrayList<>();
				if(!StringUtils.isBlank(this.getDicFilter())){
					if(this.getDicFilter().trim().substring(0, 1).equals("!")){
						flag = false;
						String[] parts = this.getDicFilter().trim().substring(1, this.getDicFilter().trim().length()).split(",");
						for(String code : parts){
							dictCodes.add(code);
						}
					} else {
						String[] parts = this.getDicFilter().split(",");
						for(String code : parts){
							dictCodes.add(code);
						}
					}
				}
				//字典
				if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
					List<DictItem> dictItems = CacheCxtUtil.cacheDataService.getDicList(getDicField().toString());
					if(dictItems!=null && !dictItems.isEmpty()){
						for(DictItem dictItem : dictItems){
							if(!StringUtils.isBlank(this.getDicFilter()) ){
								if(dictCodes.contains(dictItem.getCode()) && flag){
									if(values.contains(dictItem.getCode())){
										sb.append("<label class='checkbox-inline'>").append(builder).append(" checked value='" + dictItem.getCode() + "'></input>").append(dictItem.getDesc()).append("</label>");
									} else {
										sb.append("<label class='checkbox-inline'>").append(builder).append(" value='" + dictItem.getCode() + "'></input>").append(dictItem.getDesc()).append("</label>");
									}
								}
							} else {
								if(values.contains(dictItem.getCode())){
									sb.append("<label class='checkbox-inline'>").append(builder).append(" checked value='" + dictItem.getCode() + "'></input>").append(dictItem.getDesc()).append("</label>");
								} else {
									sb.append("<label class='checkbox-inline'>").append(builder).append(" value='" + dictItem.getCode() + "'></input>").append(dictItem.getDesc()).append("</label>");
								}
							}
						}
					}
				}
			
			}
			
			writer.print(sb.toString());
		} catch(Exception e){
			
		}
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void setDynamicAttribute(String uri, String localName, Object value) throws JspException {
		if(!StringUtils.isBlankObj(value)){
			dynamicAttributes.put(localName, String.valueOf(value));  
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

	public void setReadonly(String readonly) {
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

	public Object getDicField() {
		return dicField;
	}

	public void setDicField(Object dicField) {
		//this.dicField = dicField;
		//支持el表达式
		 try {
			this.dicField = ExpressionEvaluatorManager.evaluate(
			            "dicField", dicField.toString(), Object.class, this, pageContext);
		} catch (JspException e) {
			e.printStackTrace();
		} 
	}

	public String getDicFilter() {
		return dicFilter;
	}

	public void setDicFilter(String dicFilter) {
		this.dicFilter = dicFilter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
