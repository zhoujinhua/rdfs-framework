package com.rdfs.framework.core.service;

import java.io.Serializable;
import java.util.List;

import com.rdfs.framework.core.bean.TreeDto;

public interface TreeService {

	<T extends Serializable> List<TreeDto> getList(List<T> list, boolean isParent, String idField, String nameField, String pIdField, List<T> pList)
			throws Exception;

}
