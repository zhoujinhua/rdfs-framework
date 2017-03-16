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
import com.rdfs.framework.taglib.bean.Region;
import com.rdfs.framework.taglib.utils.CacheCxtUtil;
import com.rdfs.framework.taglib.utils.StringUtils;

/**
 * 为了使用字典，自定义select标签
 *
 */
public class SelectFieldTag extends TagSupport implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729252097490938995L;
	private String id ;
	private String name;
	private String type; //省市联动时，type="region"
	private String value;
	private String clazz;
	private String allowBlank;
	private String readonly;
	private String style;
	private String title;
	private String disabled;
	private String tooltip;
	private String multiple;
	private Object dicField;
	private String dicFilter;
	private String renderId; //作为renderId的datatables的查询条件
	private String provinceId; //省份ID
	private String initProvinceValue; //初始化省份值
	
	private Map<String, String> dynamicAttributes = new HashMap<String, String>(); 
	
	@Override
    public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try{
			StringBuffer sb = new StringBuffer("<select");
			for(Iterator<String> iter = dynamicAttributes.keySet().iterator();iter.hasNext();){
				String key = iter.next();
				sb.append(" " + key + "='" + dynamicAttributes.get(key) + "'");
			}
			if(!StringUtils.isBlank(this.getId())){
				sb.append(" id='" + this.getId() + "'");
			}
			if(!StringUtils.isBlank(this.getName())){
				sb.append(" name='" + this.getName() + "'");
			}
			if(!StringUtils.isBlank(this.getType())){
				sb.append(" type='" + this.getType() + "'");
			}
			if(!StringUtils.isBlank(this.getTitle())){
				sb.append(" title='" + this.getTitle() + "'");
			}
			if(!StringUtils.isBlank(this.getStyle())){
				sb.append(" style='" + this.getStyle() + "'");
			}
			if(!StringUtils.isBlank(this.getProvinceId())){
				sb.append(" provinceId='" + this.getProvinceId() + "'");
			}
			if(!StringUtils.isBlank(this.getInitProvinceValue())){
				sb.append(" initProvinceValue='" + this.getInitProvinceValue() + "'");
			}
			List<String> values = new ArrayList<>();
			if(!StringUtils.isBlank(this.getValue())){
				sb.append(" value='" + this.getValue() + "'");
				for(String v : this.getValue().split(",")){
					values.add(v);
				}
			}
			if(!StringUtils.isBlank(this.getReadonly())){
				sb.append(" readonly");
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
			if(!StringUtils.isBlank(this.getMultiple()) && ("true".equalsIgnoreCase(this.getMultiple()) || "multiple".equalsIgnoreCase(this.getMultiple()))){
				sb.append(" multiple ");
			}
			if(!StringUtils.isBlank(this.getRenderId())){
				sb.append(" render-id='" + this.getRenderId() + "'");
			}
			sb.append(">");
			
			if(!StringUtils.isBlank(this.getAllowBlank()) && "true".equals(this.getAllowBlank())){
				sb.append("<option class='empty' value=''>--</option>");
			}
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
				System.out.println("当前cacheParamsService的值是：" + StringUtils.isBlankObj(CacheCxtUtil.cacheDataService));
				if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
					List<DictItem> dictItems = CacheCxtUtil.cacheDataService.getDicList(getDicField().toString());
					if(dictItems!=null && !dictItems.isEmpty()){
						for(DictItem dictItem : dictItems){
							if(!StringUtils.isBlank(this.getDicFilter()) ){
								if(dictCodes.contains(dictItem.getCode()) && flag){
									if(values.contains(dictItem.getCode())){
										sb.append("<option value='"+dictItem.getCode()+"' selected='selected'>"+dictItem.getDesc()+"</option>");
									} else {
										sb.append("<option value='"+dictItem.getCode()+"'>"+dictItem.getDesc()+"</option>");
									}
								}
							} else {
								if(values.contains(dictItem.getCode())){
									sb.append("<option value='"+dictItem.getCode()+"' selected='selected'>"+dictItem.getDesc()+"</option>");
								} else {
									sb.append("<option value='"+dictItem.getCode()+"'>"+dictItem.getDesc()+"</option>");
								}
							}
						}
					}
				}
			}
			if(!StringUtils.isBlank(this.getType()) && (this.getDicField() == null || StringUtils.isBlank(this.getDicField().toString()))){
				if("region".equalsIgnoreCase(this.getType())){
					if(!StringUtils.isBlank(this.getProvinceId())){
						if(!StringUtils.isBlank(this.getInitProvinceValue())){
							if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
								List<Region> citys = CacheCxtUtil.cacheDataService.getCityList(this.getInitProvinceValue().trim());
								if(citys!=null && !citys.isEmpty()){
									for(Region region : citys){
										if(values.contains(region.getRegCode())){
											sb.append("<option value='"+region.getRegCode()+"' selected='selected'>"+region.getRegName()+"</option>");								
										} else {
											sb.append("<option value='"+region.getRegCode()+"'>"+region.getRegName()+"</option>");								
										}
									}
								}
							}
						} 
					} else {
						if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
							List<Region> provinces = CacheCxtUtil.cacheDataService.getProvinceList();
							for(Region region : provinces){
								if(values.contains(region.getRegCode())){
									sb.append("<option value='"+region.getRegCode()+"' selected='selected'>"+region.getRegName()+"</option>");								
								} else {
									sb.append("<option value='"+region.getRegCode()+"'>"+region.getRegName()+"</option>");								
								}
							}
						}
					}
				}
			}
			sb.append("</select>");
			writer.print(sb.toString());
		} catch(Exception e){
			
		}
		return EVAL_BODY_INCLUDE;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(String allowBlank) {
		this.allowBlank = allowBlank;
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

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getRenderId() {
		return renderId;
	}

	public void setRenderId(String renderId) {
		this.renderId = renderId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getInitProvinceValue() {
		return initProvinceValue;
	}

	public void setInitProvinceValue(String initProvinceValue) {
		this.initProvinceValue = initProvinceValue;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
