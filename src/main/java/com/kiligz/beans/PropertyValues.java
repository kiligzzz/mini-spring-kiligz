package com.kiligz.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * bean属性信息集合
 *
 * @author Ivan
 * @date 2022/8/16 17:22
 */
public class PropertyValues {

	private final List<PropertyValue> propertyValueList = new ArrayList<>();

	public void addPropertyValue(PropertyValue newPv) {
		// 覆盖原pv
		propertyValueList.removeIf(oldPv -> oldPv.getName().equals(newPv.getName()));
		propertyValueList.add(newPv);
	}

	public PropertyValue[] getPropertyValues() {
		return propertyValueList.toArray(new PropertyValue[0]);
	}

	public PropertyValue getPropertyValue(String propertyName) {
		for (PropertyValue pv : propertyValueList) {
			if (pv.getName().equals(propertyName)) {
				return pv;
			}
		}
		return null;
	}
}
