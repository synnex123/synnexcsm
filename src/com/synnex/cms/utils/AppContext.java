package com.synnex.cms.utils;

import java.util.HashMap;
import java.util.Map;

public class AppContext {
	private static final AppContext context = new AppContext();
	
	private static final ThreadLocal attributes = new ThreadLocal();
	
	public static AppContext getContext(){
		return context;
	}
	public void put(String key, Object val){
		Map<String, Object> map = (Map<String, Object>) attributes.get();
		if(null == map){
			map = new HashMap<>();
			attributes.set(map);
		}
		map.put(key, val);
	}
	public Object get(String key){
		Map<String, Object> map = (Map<String, Object>) attributes.get();
		if(null == map){
			return null;
		}
		return map.get(key);
	}
}
