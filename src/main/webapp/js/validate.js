/**
 * 用户注册验证
 */

//用户名userName验证
function userNameValidate(userName){
	userName = jQuery.trim(userName);
	
	//长度验证
	if(userName.length>15)
		return false;
	else
		return true;
}

//密码password验证
function passwordValidate(password){	
	//长度验证
	if(password.length>=3 && password.length<=15)
		return true;
	else
		return false;
}

//telephone验证
function telephoneValidate(telephone){
	if(telephone.length==0)
		return true;
	
	if(!(/^1[34578]\d{9}$/.test(telephone)))
		return false;
	else
		return true;
}

//qq验证
function QQValidate(QQ){
	if(QQ.length==0)
		return true;
	if(!(/^[1-9][0-9]{4,9}$/.test(QQ)))
		return false;
	else
		return true;
}

//trueName验证
function trueNameValidate(trueName){
	if(trueName.length==0)
		return true;
	
	//判断全是否是中文
	if(!(/^[\u4E00-\u9FA5]+$/.test(trueName)))
		return false;
	
	//长度验证
	if(trueName.length>=2 && trueName.length<=4)
		return true;
	else
		return false;
}


