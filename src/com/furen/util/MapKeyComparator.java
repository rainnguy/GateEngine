package com.furen.util;

import java.util.Comparator;

/**
 * 比较器
 * @author Administrator
 *
 */
public class MapKeyComparator implements Comparator<String>{
	@Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
