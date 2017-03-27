package com.rdfs.framework.auth.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单父表
 */

public class SyMenu implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 2288797108505892172L;
	private Integer menuId;
    private String menuTitle;
    private String menuIcon;
    private Integer sortNo;
    private SyMenu parMenu;
    private String status;
    private Date createTime;
    private List<SyResource> items;
    
    public SyMenu() {
    }

    public Integer getMenuId() {
        return this.menuId;
    }
    
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuTitle() {
        return this.menuTitle;
    }
    
    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public SyMenu getParMenu() {
		return parMenu;
	}

	public void setParMenu(SyMenu parMenu) {
		this.parMenu = parMenu;
	}

	public List<SyResource> getItems() {
		return items;
	}

	public void setItems(List<SyResource> items) {
		this.items = items;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}