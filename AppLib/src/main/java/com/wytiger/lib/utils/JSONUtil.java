package com.wytiger.lib.utils;

import org.json.JSONArray;

public class JSONUtil {

	/**
	 * JSON字符串特殊字符处理, 当文本中含有如下特殊字符时，此方法可以成功处理，让其在前台被正确解析，注意：此法不能处理单引号 1：引号
	 * ，如果是使用单引号，来括值的话，那String 中 ' 单引号为特殊字符 2：正斜杠，反斜杠，换行符号等。另外，必须用 (") 而非 (')
	 * 表示字符串
	 * 
	 * @param s
	 * @return String
	 */
	public static String string2Json(String s) {
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;

			/*
			 * case '\\': //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
			 * sb.append("\\\\"); break;
			 */

			case '/':
				sb.append("\\/");
				break;

			case '\b':// 退格
				sb.append("\\b");
				break;
			case '\f':// 走纸换页
				sb.append("\\f");
				break;
			case '\n':// 换行
				sb.append("\\n");
				break;
			case '\r':// 回车
				sb.append("\\r");
				break;
			case '\t': // 横向跳格
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}
	


	/**
	 * 将json数组里的每个对象的键值对分别解析到字符串数组中
	 * 
	 * @param jsonObject
	 *            需要解析的json对象
	 * @param type
	 *            类型,决定返回值的内容：键或值
	 * @return type="key"：返回json对象中"键"的字符串， type="key""value":返回json对象中"值"的字符串
	 */
	public static String[] convertJsonArrayToArray(JSONArray jsonArray, String type) {

		//预处理
		String str = jsonArray.toString();
		str = str.replace("[", "");//去掉左边中括号
		str = str.replace("]", "");//去掉右边中括号
		str = str.replace("{", "");//去掉左边大括号
		str = str.replace("}", "");//去掉右边大括号
		str = str.replace("\"", "");//遇到转义符\则去掉
		//去掉左右大括号及转义符之后,再进行切割
		String[] objects = str.split(",");//以逗号进行分割
		
		//根据类型获取相应数组
		if ("key".equals(type)) {//获得键
			String[] keys = new String[objects.length];
			for (int i = 0; i < objects.length; i++) {
				keys[i] = objects[i].split(":")[0];
			}
			return keys;
			
		} else if ("value".equals(type)) {//获得值
			String[] values = new String[objects.length];
			for (int i = 0; i < objects.length; i++) {
				values[i] = objects[i].split(":")[1];
			}
			return values;
			
		} else {
			return null;
		}
	}
}
