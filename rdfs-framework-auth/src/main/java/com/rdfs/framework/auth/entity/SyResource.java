package com.rdfs.framework.auth.entity;

import java.util.Date;

/**
 * 菜单项
 */

public class SyResource  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3392015057148573094L;
	private Integer itemId;
	private String itemTitle;
	private String status;
	private String itemLocation;
	private SyMenu menu;
	private String itemIcon;
	private Integer sortNo;
	private Date createTime;

    /** default constructor */
    public SyResource() {
    }

    public Integer getItemId() {
        return this.itemId;
    }
    
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle() {
        return this.itemTitle;
    }
    
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItemLocation() {
        return this.itemLocation;
    }
    
    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public SyMenu getMenu() {
		return menu;
	}

	public void setMenu(SyMenu menu) {
		this.menu = menu;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getItemIcon() {
        return this.itemIcon;
    }
    
    public void setItemIcon(String itemIcon) {
        this.itemIcon = itemIcon;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}