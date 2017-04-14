package com.rdfs.framework.core.service;

import java.io.Serializable;
import java.util.List;

import com.rdfs.framework.core.bean.TreeDto;

public interface TreeService {

	/**
	 * 获得树节点对象
	 * @param list 待封装对象列表
	 * @param isParent 是否父节点
	 * @param idField id标识字段
	 * @param nameField 展示名称字段
	 * @param pIdField 父ID
	 * @param pList 选中列表
	 * @return
	 * @throws Exception
	 */
	<T extends Serializable> List<TreeDto> getList(List<T> list, boolean isParent, String idField, String nameField, String pIdField, List<T> pList)
			throws Exception;

}
