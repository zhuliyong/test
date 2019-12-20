package com.vicmob.toolUtils;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * 有关编辑XML
 * 
 * @author yuson
 *
 */
public class EditorForXML<mian> {

	/**
	 *读取xml文件
	 * @param fileName 文件名
	 * @param key 标记
	 * @return 对应的内容
	 */
	public static String getValueForKey(String fileName, String key) {
		String value = "";
		InputStream in=Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
		try {
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(in); // 读取xml-并建立document
			Element rootElt = document.getRootElement();// 取出rootElt
			value = rootElt.elementText(key);// 取出updateTime 里的值
            System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return value;
	}


}
