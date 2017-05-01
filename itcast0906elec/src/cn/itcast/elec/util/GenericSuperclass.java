package cn.itcast.elec.util;

import java.lang.reflect.ParameterizedType;

public class GenericSuperclass {

	//范类转换
	public static Class getActualTypeClass(Class entity) {
		ParameterizedType type = (ParameterizedType) entity.getGenericSuperclass();
		Class entityClass = (Class) type.getActualTypeArguments()[0];
		return entityClass;
	}
	
	

}
