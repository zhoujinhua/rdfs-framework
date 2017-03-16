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

import com.rdfs.framework.taglib.utils.CacheCxtUtil;
import com.rdfs.framework.taglib.utils.StringUtils;


/**
 * 为了使用字典，自定义select标签
 *
 */
public class TableFieldTag extends TagSupport implements DynamicAttributes{

	/**
	 * 
	 */
	private static final long serialVersionUID = 729252097490938995L;
	private String id ;
	private String onrender;
	private String prerender;
	private String clazz;
	private String title;
	private String border;
	private String style;
	private String size;
	private List<String> columns = new ArrayList<>();
	
	private Map<String, String> dynamicAttributes = new HashMap<String, String>(); 
	
	@Override
	public int doStartTag() throws JspException {
		columns.clear();
		return EVAL_PAGE;
	}

	@Override
    public int doEndTag() throws JspException {
		JspWriter writer = pageContext.getOut();
		try{
			StringBuffer sb = new StringBuffer("<table");
			for(Iterator<String> iter = dynamicAttributes.keySet().iterator();iter.hasNext();){
				String key = iter.next();
				sb.append(" " + key + "='" + dynamicAttributes.get(key) + "'");
			}
			if(!StringUtils.isBlank(this.getId())){
				sb.append(" id='" + this.getId() + "'");
			}
			if(!StringUtils.isBlank(this.getClazz())){
				sb.append(" class='" + this.getClazz() + " datatables'");
			}
			if(!StringUtils.isBlank(this.getBorder())){
				sb.append(" border='" + this.getBorder() + "'");
			}
			if(!StringUtils.isBlank(this.getTitle())){
				sb.append(" title='" + this.getTitle() + "'");
			}
			if(!StringUtils.isBlank(this.getPrerender())){
				sb.append(" prerender='" + this.getPrerender() + "'");
			}
			if(!StringUtils.isBlank(this.getStyle())){
				sb.append(" style='" + this.getStyle() + "'");
			}
			if(!StringUtils.isBlank(this.getOnrender())){
				sb.append(" url='" + this.getOnrender() + "'");
			}
			if(!StringUtils.isBlank(this.getSize())){
				sb.append(" size='" + this.getSize() + "'");
			} else {
				if(!StringUtils.isBlankObj(CacheCxtUtil.cacheDataService)){
					sb.append(" size='" + CacheCxtUtil.cacheDataService.getParam("_page_size") + "'");
				}
			}
			sb.append(">");
			
			if(!columns.isEmpty()){
				sb.append("<thead><tr>");
				for(String column : columns){
					sb.append(column);
				}
				sb.append("</tr></thead>");
			}
			sb.append("</table>");
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

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getOnrender() {
		return onrender;
	}

	public void setOnrender(String onrender) {
		this.onrender = onrender;
	}

	public String getPrerender() {
		return prerender;
	}

	public void setPrerender(String prerender) {
		this.prerender = prerender;
	}

	public String getBorder() {
		return border;
	}

	public void setBorder(String border) {
		this.border = border;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
}
