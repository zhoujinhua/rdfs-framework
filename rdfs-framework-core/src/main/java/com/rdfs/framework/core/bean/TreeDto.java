package com.rdfs.framework.core.bean;

import java.io.Serializable;

public class TreeDto implements Serializable{

	/**
	 * 树节点封装
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;  
    private String pId;  
    private String name;  
    private String url;
    private String icon;
    private boolean open;
    private boolean checked;
    private boolean isParent;
    
    
	public TreeDto() {
		super();
	}
	
	public TreeDto(String id, String pId, String name, boolean isParent) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.isParent = isParent;
	}

	public TreeDto(String id, String pId, String name, boolean open, boolean checked) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.open = open;
		this.checked = checked;
	}
	public TreeDto(String id, String pId, String name, String url, String icon, boolean isParent) {
		super();
		this.id = id;
		this.pId = pId;
		this.name = name;
		this.url = url;
		this.icon = icon;
		this.isParent = isParent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(boolean isParent) {
		this.isParent = isParent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof TreeDto){
			TreeDto dto = (TreeDto)obj;
			
			if(this.getId().equals(dto.getId()) && this.getpId().equals(dto.getpId()) && this.getName().equals(dto.getName())){
				return true;
			}
		}
		return false;
	}
    
    
}
