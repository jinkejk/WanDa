<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>查询结果</title>
    <link rel="stylesheet" href="css/css_mobile.css">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
</head>

<body>
<div class="z_main">
    <div class="z_center">
        <div class="z_top"><a href="javascript:window.history.back();" class="z_topl"></a>查询结果</div>
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
        <div class="z_ba">
            <ul id="menu_ul">
                <%--js动态生成目录--%>
            </ul>
        </div>

        <div class="z_list">
            <!--    	<div class="z_list1"><img src="img/jian.png"></div>-->
            <div class="z_list2">
                <div class="zx_list21">新增资料通知
                    <form action="searchSolution" method="post" id="form1" onsubmit="return search()">
                        <input type="hidden" name="pageSize" value="15">
                        <input type="hidden" name="currentPage" value="1">
                        <input type="hidden" name="isNewContent" value="1">
                        <input type="submit" id="z_topn" value="搜索" class="search_submit">
                        <label for="z_topn" class="z_topn1"><img src="image/mobile/ss.png"></label>
                        <input type="text" class="z_topn2" id="searchContent" name="searchContent"
                               value="${searchContent}">
                    </form>
                </div>

                <div class="z_list25">
                    <ul>
                        <c:if test="${solutions.size() > 0}">
                            <c:forEach var="solution" items="${solutions}" varStatus="status">
                                <li>
                                    <div class="zx_l1">
                                        <a href="searchSolution_searchSolutionById?solutionId=${solution.solutionId}"
                                           style="color: #000">${titleList[status.index]}</a>
                                    </div>
                                    <div class="zx_l2">${solution.category.TMCName}</div>
                                    <div class="x_r1"><span class="zx_r2">New</span><span
                                            class="zx_r22">${fn:substring(solution.createDate,0,10)}</span></div>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty solutions || solutions.size() <= 0}">
                            <li>
                                <div class="zx_l1"> 无相关内容.......</div>
                            </li>
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


        $("#nextPage").click(function () {
            if (${currentPage} >=
            ${totalPage})
            {
                alert("已经是最后一页了！");
            }
            else
            {
                //下一页
                if ($("#searchContent").val() == '')
                    window.location.href = "searchSolution_searchSolutionByCategory?TMCId=${TMCId}&currentPage=${currentPage+1}&pageSize=${pageSize}&searchContent=${searchContent}";
                else
                    window.location.href = "searchSolution_searchSolutionByContent?currentPage=${currentPage+1}&pageSize=${pageSize}&searchContent=${searchContent}";
            }
        });
        $("#go").click(function () {
            if ($("#pageNum").val() ==${currentPage})
                return;
            if ($("#pageNum").val() >= 1 && $("#pageNum").val() <= ${totalPage}) {
                if ($("#searchContent").val() == '')
                    window.location.href = "searchSolution_searchSolutionByCategory?TMCId=${TMCId}&pageSize=${pageSize}&searchContent=${searchContent}&currentPage=" + $("#pageNum").val();
                else
                    window.location.href = "searchSolution_searchSolutionByContent?currentPage="+ $("#pageNum").val()+"&pageSize=${pageSize}&searchContent=${searchContent}";
            }
            else
                alert("页码不正确！");
        });
        $("#prePage").click(function () {
            if (${currentPage <= 1}) {
                alert("已经是第一页了！");
            } else {
                //上一页
                if ($("#searchContent").val() == '')
                    window.location.href = "searchSolution_searchSolutionByCategory?TMCId=${TMCId}&currentPage=${currentPage-1}&pageSize=${pageSize}&searchContent=${searchContent}";
                else
                    window.location.href = "searchSolution_searchSolutionByContent?currentPage=${currentPage-1}&pageSize=${pageSize}&searchContent=${searchContent}";
            }
        });
    });
    //动态生成目录
    var contents = '';
    $.each(${firstLevelTMC}, function (index, firstLevel) {
        contents += "<li><a href='searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=" +
            firstLevel.TMCId + "&currentPage=1'><div class='z_ba1'><img src='image/mobile/w" + (index + 1) + ".png'></div>" +
            "<div class='z_ba2'>" + firstLevel.TMCName + "</div></a></li>"
    });
    $("#menu_ul").append(contents);

    //点击搜索按钮
    function search() {
        if ($("#searchContent").val() == '') {
            alert('请输入内容！');
            return;
        }
    }
</script>


</body>
</html>
