package com.foofu.util;

import java.util.Map;
import java.util.TreeMap;

public class MapSort {
		
	/**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, Object> sortMapByKey(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, Object> sortMap = new TreeMap<String, Object>( new MapKeyComparator());
 
        sortMap.putAll(map);
 
        return sortMap;
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, Object> map = new TreeMap<String, Object>();
		 
        map.put("2015-12-25", "kfc");
        map.put("2015-12-20", "wnba");
        map.put("2015-12-22", "nba");
        map.put("2015-12-19", "cba");
 
        Map<String, Object> resultMap = sortMapByKey(map);    //按Key进行排序
 
        for (Map.Entry<String, Object> entry : resultMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
	}

}
