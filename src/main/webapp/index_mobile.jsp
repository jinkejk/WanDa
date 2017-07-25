<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>万达首页</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<link href="css/css.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="css/lanrenzhijia.css" />
	<script type="text/javascript" src="js/mobile/jquery-1.8.2.min.js"></script>
	<script src="js/mobile/jquery.touchSlider.js"></script>
	<script>
        $(document).ready(function () {
            $(".main_visual").hover(function(){
                $("#btn_prev,#btn_next").fadeIn()
            },function(){
                $("#btn_prev,#btn_next").fadeOut()
            })
            $dragBln = false;
            $(".main_image").touchSlider({
                flexible : true,
                speed : 200,
                btn_prev : $("#btn_prev"),
                btn_next : $("#btn_next"),
                paging : $(".flicking_con a"),
                counter : function (e) {
                    $(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
                }
            });
            $(".main_image").bind("mousedown", function() {
                $dragBln = false;
            })
            $(".main_image").bind("dragstart", function() {
                $dragBln = true;
            })
            $(".main_image a").click(function() {
                if($dragBln) {
                    return false;
                }
            })
            timer = setInterval(function() { $("#btn_next").click();}, 5000);
            $(".main_visual").hover(function() {
                clearInterval(timer);
            }, function() {
                timer = setInterval(function() { $("#btn_next").click();}, 5000);
            })
            $(".main_image").bind("touchstart", function() {
                clearInterval(timer);
            }).bind("touchend", function() {
                timer = setInterval(function() { $("#btn_next").click();}, 5000);
            })
        });
	</script>
</head>
<body>
<div class="z_main">
	<div class="l_top">
		<div class="l_head"><div class="l_l"></div><img class="l_tt" src="image/mobile/dian.png" alt=""><p class="l_ww">无锡万达城销售物业产品研发平台</p><div class="l_r"></div></div>
	</div>
	<div class="main">
		<div class="pro-switch">
			<div class="slider">
				<div class="flexslider">
					<ul class="slides">
						<li>
							<div class="img"><img src="image/mobile/banner.jpg"  alt="" /></div>
						</li>
						<li>
							<div class="img"><img src="image/mobile/banner.jpg" alt="" /></div>
						</li>
						<li>
							<div class="img"><img src="image/mobile/banner.jpg"  alt="" /></div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!--内容-->
	<ul class="l_nr">
		<li class="lnr">
			<div class="lnr1"><img src="image/mobile/xw1.jpg" alt=""></div>
			<div class="lnr2"><a href="javascript:void(0);">
				<img src="image/mobile/xw11.png" alt="">
				<p>产品研发大事记</p></a>
			</div>
		</li>
		<li class="lnr">
			<div class="lnr2 ls2"><a href="commonAction_lastSolutions">
				<img src="image/mobile/xw2.png" alt="">
				<p>问题档案库</p></a>
			</div>
			<div class="lnr1"><img src="image/mobile/xw22.jpg" alt=""></div>
		</li>
		<li class="lnr">
			<div class="lnr1"><img src="image/mobile/xw1.jpg" alt=""></div>
			<div class="lnr2 ls3"><a href="javascript:void(0);">
				<img src="image/mobile/xw3.png" alt="">
				<p>图纸为二维码</p></a>
			</div>
		</li>
		<li class="lnr">
			<div class="lnr2 ls4"><a href="commonAction_lastSignMaterial">
				<img src="image/mobile/xw4.png" alt="">
				<p>签批资料</p></a>
			</div>
			<div class="lnr1"><img src="image/mobile/xw22.jpg" alt=""></div>
		</li>
		<li class="lnr">
			<div class="lnr1"><img src="image/mobile/xw1.jpg" alt=""></div>
			<div class="lnr2 ls5"><a href="commonAction_lastTrainingMaterial">
				<img src="image/mobile/xw5.png" alt="">
				<p>培训资料</p></a>
			</div>
		</li>
		<li class="lnr">
			<div class="lnr2 ls6"><a href="http://app.connect.trimble.com">
				<img src="image/mobile/xw6.png" alt="">
				<p>BIM</p></a>
			</div>
			<div class="lnr1"><img src="image/mobile/xw22.jpg" alt=""></div>
		</li>
	</ul>

</div>
<script defer src="js/mobile/lanrenzhijia.js"></script>
<script type="text/javascript">
    $(function(){
        $('.flexslider').flexslider({
            animation: "slide",
            start: function(slider){
                $('body').removeClass('loading');
            }
        });
    });
</script>
</body>
</html>
