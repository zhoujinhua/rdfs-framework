package com.rdfs.framework.core.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rdfs.framework.core.bean.ExceptionDto;
import com.rdfs.framework.core.exeption.CustomerException;



/**
 * <b>异常信息参数配置加载类</b>
 */
@SuppressWarnings("rawtypes")
public class ExceptionInfoUtil {

	private static Logger log = LoggerFactory.getLogger(ExceptionInfoUtil.class);

	private static Map<String, Object> parameterCacheDto = null;

	static {
		try {
			if (log.isDebugEnabled()) {
				log.debug("解析XML异常参数配置文件...");
			}
			parameterCacheDto = new HashMap<>();
			SAXReader reader = new SAXReader();
			InputStream is = ExceptionInfoUtil.class.getResourceAsStream("exceptionInfo.xml");
			Document document = reader.read(is);
			Element elRoot = document.getRootElement();
			Iterator elIt = elRoot.elementIterator();
			while (elIt.hasNext()) {
				Element el = (Element) elIt.next();
				Attribute attrKey = el.attribute("id");
				String id = attrKey.getText();
				Attribute attrInfo = el.attribute("info");
				String info = attrInfo.getText();
				Attribute attrSuggest = el.attribute("suggest");
				String suggest = attrSuggest.getText();
				ExceptionDto exceptionVO = new ExceptionDto();
				exceptionVO.setId(id);
				exceptionVO.setInfo(info);
				exceptionVO.setSuggest(suggest);
				parameterCacheDto.put(id, exceptionVO);
			}
		} catch (Exception e) {
			throw new CustomerException("解析XML异常参数配置文件出错.", e);
		}
	}

	/**
	 * 获取异常配置参数
	 * 
	 * @param pKey
	 * @return
	 */
	public static ExceptionDto getExceptionInfo(String errID) {
		ExceptionDto vo = (ExceptionDto) parameterCacheDto.get(errID);
		return vo;
	}

	public static void main(String[] args) {

	}

}
