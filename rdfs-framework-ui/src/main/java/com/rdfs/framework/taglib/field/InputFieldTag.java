package com.rdfs.framework.taglib.field;

import java.util.Date;
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
 * 为了使用字典，自定义Input标签
 *
 */
public class InputFieldTag extends TagSupport implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729252097490938995L;
	private String id ;
	private String name;
	private String value;
	private String clazz;
	private String style;
	private String tooltip;
	private String type;
	private String title;
	private String format;
	private String readonly;
	private String disabled;
	private String renderId;
	private String negative;
	private String minlength;
	private String maxlength;
	
	private String uploadUrl; //type="file"时，上传的url
	private String fileType; //上传文件类型,以,分割
	private String fileSize; //上传文件大小 数字
	private String callback; //上传成功后的回调函数
	
	private Map<String, String> dynamicAttributes = new HashMap<String, String>(); 
	
	@Override
    public int doStartTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try{
			StringBuffer sb = new StringBuffer("<input");
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
			if(!StringUtils.isBlank(this.getClazz())){
				sb.append(" class='" + this.getClazz() + "'");
			}
			if(!StringUtils.isBlank(this.getTitle())){
				sb.append(" title='" + this.getTitle() + "'");
			}
			if(!StringUtils.isBlank(this.getTooltip())){
				sb.append(" tooltip='" + this.getTooltip() + "'");
			}
			if(!StringUtils.isBlank(this.getMinlength())){
				sb.append(" minlength='" + this.getMinlength() + "'");
			}
			if(!StringUtils.isBlank(this.getMaxlength())){
				sb.append(" maxlength='" + this.getMaxlength() + "'");
			}
			if(!StringUtils.isBlank(this.getUploadUrl())){
				sb.append(" uploadUrl='" + this.getUploadUrl() + "'");
			}
			if(!StringUtils.isBlank(this.getFileType())){
				sb.append(" fileType='" + this.getFileType() + "'");
			}
			if(!StringUtils.isBlank(this.getFileSize())){
				sb.append(" fileSize='" + this.getFileSize() + "'");
			}
			if(!StringUtils.isBlank(this.getCallback())){
				sb.append(" callback='" + this.getCallback() + "'");
			}
			String v = " ";
			boolean flag = true;
			boolean isFile = false;
			if(!StringUtils.isBlank(this.getType())){
				if(this.getType().equalsIgnoreCase("int")){
					sb.append(" xtype='" +this.getType()+ "'");
					if(StringUtils.isBlank(this.getId())){
						Thread.sleep(1);
						sb.append(" id='number-" + new Date().getTime() + "'");
					}
				}
				else if(this.getType().equalsIgnoreCase("float")){
					sb.append(" xtype='" +this.getType()+ "'");
					if(StringUtils.isBlank(this.getId())){
						Thread.sleep(1);
						sb.append(" id='number-" + new Date().getTime() + "'");
					}
				} 
				else if(this.getType().equalsIgnoreCase("file")){
					sb.append(" type='input' xtype='file' readonly");
					isFile = true;
				}
				else if(this.getType().equalsIgnoreCase("filebtn")){
					sb.append(" type='button' xtype='file'");
					isFile = true;
				}
				else if(this.getType().equalsIgnoreCase("date")){
					sb.append(" type='date' readonly");
				} else {
					sb.append(" type='" +this.getType()+ "'");
				}
				if(this.getType().equalsIgnoreCase("dict")&& !StringUtils.isBlank(this.getFormat()) && !StringUtils.isBlank(this.getValue())){
					if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
						for(String part : this.getValue().split(",")){
							String desc = CacheCxtUtil.cacheDataService.getDicDesc(this.getFormat(), part);
							if(!StringUtils.isBlank(desc)){
								flag = false;
								v += desc + ",";
							}
						}
					}
				}
				if(this.getType().equalsIgnoreCase("region") && !StringUtils.isBlank(this.getValue())){
					if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
						for(String part : this.getValue().split(",")){
							Region region = CacheCxtUtil.cacheDataService.getRegion(part);
							if(region != null){
								flag = false;
								v += region.getRegName() + ",";
							}
						}
					}
				}
			}
			v = v.substring(0, v.length()-1);
			if(flag){
				v = this.getValue();
			}
			if(!StringUtils.isBlank(this.getNegative())){
				sb.append(" negative='" + this.getNegative() + "'");
			}
			if(!StringUtils.isBlank(v)){
				sb.append(" value='" +v+"'");
			}
			if(!StringUtils.isBlank(this.getFormat())){
				sb.append(" format='"+this.getFormat()+"'");
			}
			if(!StringUtils.isBlank(this.getReadonly())){
				sb.append(" readonly");
			}
			if(!StringUtils.isBlank(this.getDisabled())){
				sb.append(" disabled='" + this.getDisabled() + "'");
			}
			if(!StringUtils.isBlank(this.getRenderId())){
				sb.append(" render-id='" + this.getRenderId() + "'");
			}
			sb.append(">");
			if(isFile){
				StringBuilder builder = new StringBuilder("<div class='input-group'>");
				builder.append(sb.toString());
				builder.append("<span class='input-group-btn'>");
				builder.append("<a href='javascript:;' class='btn btn-primary file-upload'><i class='glyphicon glyphicon-upload'></i></a>");
				builder.append("</span>");
				builder.append("</div>");
				writer.print(builder.toString());
			} else {
				writer.print(sb.toString());
			}
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

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getDisabled() {
		return disabled;
	}

	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}

	public String getRenderId() {
		return renderId;
	}

	public void setRenderId(String renderId) {
		this.renderId = renderId;
	}

	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNegative() {
		return negative;
	}

	public void setNegative(String negative) {
		this.negative = negative;
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
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
