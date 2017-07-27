<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>搜索结果</title>
    <link rel="stylesheet" href="css/css_mobile.css">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
</head>

<body>

<div class="z_main">
    <div class="z_center">
        <div class="z_top"><a href="javascript:window.history.back();" class="z_topl"></a>
            <div id="top_title">搜索结果</div>
        </div>
        <div class="wrapper02" id="demo06">
            <div class="scroller">
                <ul class="clearfix">
                    <li><a href="javascript:void(0)">产品研发大事记</a></li>
                    <li><a href="commonAction_lastSolutions">问题档案库</a></li>
                    <li><a href="javascript:void(0)">图纸二维码</a></li>
                    <li><a href="commonAction_lastSignMaterial">签批材料</a></li>
                    <li><a href="commonAction_lastTrainingMaterial">培训材料</a></li>
                    <li><a href="http://app.connect.trimble.com" target="_blank">BIM</a></li>
                </ul>
            </div>
        </div>
        <div class="zx_ba">
            <ul class="tab clearfix" id="first_ul">
                <%--动态添加一级目录--%>
            </ul>

            <div class="z_b1 content" id="second_level_div">
                <%--动态添加二级目录--%>
            </div>
        </div>

        <div class="z_list">
            <div class="z_list2">
                <div class="z_list21">相关资料
                    <form action="searchSignMaterial_searchSMBycontent" method="post" onsubmit="return checkForm()">
                        <input type="hidden" name="pageSize" value="${pageSize}">
                        <input type="hidden" name="currentPage" value="1">
                        <input type="submit" id="z_topn">
                        <label for="z_topn" class="z_topn1"><img src="image/mobile/ss.png"></label>
                        <input type="text" class="z_topn2" value="${searchContent}" id="searchContent"
                               name="searchContent" >
                    </form>
                </div>

                <div class="z_list24">
                    <ul>
                        <c:if test="${lastSignMaterials.size() > 0}">
                            <c:forEach var="signMaterial" items="${lastSignMaterials}" varStatus="status">
                                <li>
                                    <div class="z_zl1">
                                        <div class="z_zl11">
                                            <c:if test="${!empty titleList}">${titleList[status.index]}</c:if>
                                            <c:if test="${empty titleList}">${signMaterial.title}</c:if>
                                        </div>
                                        <div class="z_zl12">下载次数：${signMaterial.clickNum }</div>
                                        <div class="z_zl13"><a href="#" onclick="downloadSignFile('${signMaterial.signFile}')"></a></div>
                                    </div>
                                    <div class="z_zl1">
                                        <div class="z_zl21">${signMaterial.category.parentTMC.TMCName}</div>
                                        <div class="z_zl22">${signMaterial.category.TMCName}</div>
                                        <div class="z_zl23">${fn:substring(signMaterial.createDate,0,10)}</div>

                                    </div>
                                    <div class="z_zl1">
                                        备注：${signMaterial.remark}
                                    </div>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${signMaterials.size() <= 0}">
                            <li class="z_zl11">没有资料......</li>
                        </c:if>
                    </ul>


                </div>

            </div>


        </div>
    </div>
    <div class="fy">
        <div class="fyy">
            <div class="f2y"><a href="javascript:void(0)" id="prePage">上一页</a></div>
            <div class="f2y"><a href="javascript:void(0)" id="nextPage">下一页</a></div>
            <input type="text" value="${currentPage}" id="pageNum">
            <div class="f3">/${totalPage}页</div>
            <div class="f2y"><a href="javascript:void(0)" id="go">跳转</a></div>
        </div>
    </div>
</div>

<script type="text/javascript" src="js/mobile/jquery.min.js"></script>
<script type="text/javascript" src="js/mobile/flexible.js"></script>
<script type="text/javascript" src="js/mobile/iscroll.js"></script>
<script type="text/javascript" src="js/mobile/navbarscroll.js"></script>
<script type="text/javascript">
    $(function () {
        //demo示例六 通过id调取
        $('#demo06').navbarscroll({
            defaultSelect: 1,
            scrollerWidth: 6,
            fingerClick: 1,
            endClickScroll: function (obj) {
                console.log(obj.text())
            }
        });
    });
</script>

<script type="text/javascript">
    $(function () {
        //设置目录
        var contents = '';
        var contents_second = '';
        //动态生成左边的目录
        $.each(${firstLevelTMC}, function (index, firstLevel) {
            //动态生成一级目录
            var backgroud_id = index % 4 + 1;
            var first_index = index;
            <%--alert('${empty index ? 0:index}');--%>
            if (index == ${empty index ? 0:index}) {
                contents += '<li class="tab-active"><a href="searchSignMaterial_searchSignMaterialsByCategory?index='+index+'&pageSize=${pageSize}&TMCId='+firstLevel.TMCId+'&currentPage=1" style="color: #000">' +
                    '<div class="zx_ba1 zx_' + backgroud_id + '">'
                    + firstLevel.TMCName.substring(0, 1) + '</div><div class="zx_ba2">地块</div></a></li>';
                contents_second += '<ul class="inner">'
            }
            else {
                contents += '<li><a href="searchSignMaterial_searchSignMaterialsByCategory?index='+index+'&pageSize=${pageSize}&TMCId='+firstLevel.TMCId+'&currentPage=1" style="color: #000"><div class="zx_ba1 zx_'
                    + backgroud_id + '">' + firstLevel.TMCName.substring(0, 1) + '</div><div class="zx_ba2">地块</div></a></li>';
                contents_second += '<ul class="inner" style="display:none">'
            }

            //生成二级目录
            $.each(${secondLevelTMC}, function (index, secondLevel) {
                if (secondLevel.parentTMC.TMCId == firstLevel.TMCId) {
                    contents_second += '<li class="z_ba2"><a href="searchSignMaterial_searchSignMaterialsByCategory?index='+first_index+'&pageSize=${pageSize}&TMCId='+secondLevel.TMCId+'&currentPage=1" >' + secondLevel.TMCName + '</a></li>';
                }
            });
            contents_second += '</ul>';
            $("#second_level_div").append(contents_second);
            contents_second = '';
        });
        //alert(contents);
        $("#first_ul").append(contents);

        var lists = $('.tab li');
        var contents = $('.content .inner');
        //绑定监听
        lists.each(function (index_li, li) {
            $(this).on('click', function (event) {

                lists.removeClass('tab-active');
                $(this).addClass('tab-active');

                contents.each(function (index_content, content) {
                    if (index_li === index_content) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                });
            });
        });

        //demo示例六 通过id调取
        $('#demo06').navbarscroll({
            defaultSelect:3,
            scrollerWidth:6,
            fingerClick:1,
            endClickScroll:function(obj){
                console.log(obj.text())
            }
        });

        $("#nextPage").click(function(){
            if(${currentPage} >= ${totalPage}){
                alert("已经是最后一页了！");
            }else{
                //下一页
                if($("#searchContent").val() != '')
                    window.location.href="searchSignMaterial_searchSMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=${currentPage+1}";
                else
                    window.location.href="searchSignMaterial_searchSignMaterialsByCategory?TMCId=${TMCId}&currentPage=${currentPage+1}&pageSize=${pageSize}";
            }
        });
        $("#prePage").click(function(){
            if(${currentPage <= 1}){
                alert("已经是第一页了！");
            }else{
                //上一页
                if($("#searchContent").val() != '')
                    window.location.href="searchSignMaterial_searchSMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=${currentPage-1}";
                else
                    window.location.href="searchSignMaterial_searchSignMaterialsByCategory?TMCId=${TMCId}&currentPage=${currentPage-1}&pageSize=${pageSize}&searchContent=${searchContent}";
            }
        });
        $("#go").click(function(){
            if($("#pageNum").val()==${currentPage})
                return;
            if($("#pageNum").val()>=1 && $("#pageNum").val()<= ${totalPage}){
                if($("#searchContent").val() != '')
                    window.location.href="searchSignMaterial_searchSMBycontent?flag=1&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=" + $("#pageNum").val();
                else
                    window.location.href="searchSignMaterial_searchSignMaterialsByCategory?TMCId=${TMCId}&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=" + $("#pageNum").val();
            }
            else
                alert("页码不正确！");
        });

    });

    //点击搜索按钮
    function checkForm(){
        if($("#searchContent").val()=='') {
            alert('请输入内容！');
            return;
        }
    }

    //文件下载，先用异步判断是否存在文件
    function downloadSignFile(signFile){
        $.ajax({
            type:'POST',
            contentType: "application/text",
            url: "downUtilAction_isFileExist?filePath=/signMaterial&fileName="+signFile,
            dataType: "text",
            success:function(datas){
//                alert(datas);
                if(datas == "none"){
                    alert('不存在该文件！');
                    return;
                }
                if(datas == "error"){
                    alert('下载出错！');
                    return;
                }
                if(datas == "error_path"){
                    alert('路径配置错误！');
                    return;
                }
                if(datas == "exist"){
                    window.location.href = "downUtilAction?filePath=/signMaterial&fileName="+signFile;
                }
            }
        });
    }
</script>

</body>
</html>
