package com.wanda.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.wanda.beans.User;

/**
 * User类型转换器
 * @author jinkejk
 *未使用
 */
public class UserConverter extends StrutsTypeConverter{

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		User user = new User();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		//将字符串分离
		String[] userValues = values[0].split(",");
		//设置值
		user.setUserId(Integer.parseInt(userValues[0].substring(userValues[0].indexOf("=")+1, userValues[0].length())));
		user.setLoginName(userValues[1]);
		user.setTrueName(userValues[2]);
		user.setPassword(userValues[3]);
		user.setRemark(userValues[4]);
		user.setDept(userValues[5]);
		user.setTelephone(userValues[6]);
		try {
			user.setLastLogin(format.parse(userValues[7]));
			user.setRegisterTime(format.parse(userValues[8]));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setCompany(userValues[9]);
		user.setPosition(userValues[10]);
		user.setQQ(userValues[11]);
//		user.setRole(role);
		return null;
	}

	@Override
	public String convertToString(Map context, Object o) {
		// TODO Auto-generated method stub
		return null;
	}

}
