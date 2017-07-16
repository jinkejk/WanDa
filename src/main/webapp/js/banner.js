function move3(url){
	
	document.getElementById("external-frame").src=url;   	
	
}

function move4(url){
	
	document.getElementById("table_Content").src=url;   	
	
	
}
//提交表单
function decodeQRImg(){
	$("decodeResult").hide();
	if(imgValidate()){
		$("decodeResult").html('上传中......');
    	$("decodeResult").show();
		$("#form1").submit();
		$("#QRfile").val('');
	}
}   

//判断文件是否符合要求
function imgValidate(){
    var filepath = $("input[name='QRfile']").val();
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();

    if(filepath!=""){        	
        if (ext != ".BMP" && ext != ".PNG" && ext != ".GIF" && ext != ".JPG" && ext != ".JPEG") {
            alert("图片限于bmp,png,gif,jpeg,jpg格式！");           
            return false;
        } 
    }
    return true;
}

//初始化iframe高度
function setIframeHeight(iframe) {
	if (iframe) {
	var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
	if (iframeWin.document.body) {
	    iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
	}
	}
}

