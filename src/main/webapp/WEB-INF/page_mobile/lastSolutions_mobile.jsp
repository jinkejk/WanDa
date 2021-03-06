<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>问题档案库</title>
    <link rel="stylesheet" href="css/css_mobile.css">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport">
</head>

<body>
<div class="z_main">
    <div class="z_center">
        <div class="z_top"><a href="javascript:window.history.back();" class="z_topl"></a> 问题档案库</div>
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

        <%--生成一级目录--%>
        <div class="z_ba">
            <ul id="menu_ul">
                <%--js动态生成目录--%>
            </ul>
        </div>

        <div class="z_list">
            <div class="z_list2">
                <div class="zx_list21">新增资料通知
                    <form action="searchSolution" method="get">
                        <input type="hidden" name="pageSize" value="15">
                        <input type="hidden" name="currentPage" value="1">
                        <input type="hidden" name="isNewContent" value="1">
                        <input type="submit" id="z_topn">
                        <label for="z_topn" class="z_topn1"><img src="image/mobile/ss.png"></label>
                        <input type="text" class="z_topn2" value="${searchContent}"
                               id="searchContent" name="searchContent"
                               onfocus="if(value=='请输入你要搜索的内容') {value=''}"
                               onblur="if (value=='') {value='请输入你要搜索的内容'}">
                    </form>
                </div>

                <div class="z_list25">
                    <ul>
                        <c:if test="${solutions.size() > 0}">
                            <c:forEach var="solution" items="${solutions}" varStatus="status">
                                <li>
                                    <div class="zx_l1">
                                        <a href="searchSolution_searchSolutionById?solutionId=${solution.solutionId}"
                                           style="color: #000">${solution.title}</a>
                                    </div>
                                    <div class="zx_l2">
                                        <c:if test="${!empty solution.category.parentTMC}">
                                            ${solution.category.parentTMC.TMCName}
                                        </c:if><c:if test="${empty solution.category.parentTMC}">
                                        ${solution.category.TMCName}
                                    </c:if></div>
                                    <div class="x_r1"><span class="zx_r2">New</span> <span
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
        <div class="z_list">
            <!--    	<div class="z_list1"><img src="img/jian.png"></div>-->
            <div class="z_list2">
                <div class="z_list21">最热门查询资料</div>

                <div class="z_list24">
                    <ul>
                        <c:if test="${hotVisitSolution.size() > 0}">
                            <c:forEach var="solution" items="${hotVisitSolution}" varStatus="status">
                                <li>
                                    <a href="searchSolution_searchSolutionById?solutionId=${solution.solutionId}"
                                       class="z_rm1">${solution.title}</a>
                                    <span class="z_rm2">${solution.clickNum}</span>
                                </li>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty hotVisitSolution || hotVisitSolution.size() <= 0}">
                            <li>无热门查询.......</li>
                        </c:if>
                    </ul>
                </div>
            </div>


        </div>

        <div class="z_list">
            <!--    	<div class="z_list1"><img src="img/jian.png"></div>-->
            <div class="z_list2">
                <div class="zx_list22">热门查询关键字</div>

                <div class="zx_list24">
                    <c:if test="${lastKeywords.size() > 0}">
                        <c:forEach var="lastKeyword" items="${lastKeywords}" varStatus="status">
                            <a href="searchSolution?pageSize=15&searchContent=${lastKeywords[status.index]}&currentPage=1">${fn:substring(lastKeyword,0,10)}</a>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty lastKeywords || lastKeywords.size() <= 0}">
                        <li>无查询记录.......</li>
                    </c:if>
                </div>
            </div>
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

        //动态生成目录
        var contents = '';
        $.each(${firstLevelTMC}, function (index, firstLevel) {
            contents += "<li><a href='searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=" +
                firstLevel.TMCId + "&currentPage=1'><div class='z_ba1'><img src='image/mobile/w"+(index+1)+".png'></div>" +
                "<div class='z_ba2'>"+firstLevel.TMCName+"</div></a></li>"
        });
        $("#menu_ul").append(contents);
    });
</script>
</body>

</html>