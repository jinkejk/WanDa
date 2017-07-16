<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

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
        <div class="z_top"><a href="" class="z_topl"></a> 问题档案库</div>
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
            <ul>
                <li><a href="searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=38&currentPage=1">
                    <div class="z_ba1"><img src="image/mobile/w1.png"></div>
                    <div class="z_ba2">成本</div>
                </a></li>
                <li><a href="searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=39&currentPage=1">
                    <div class="z_ba1"><img src="image/mobile/w2.png"></div>
                    <div class="z_ba2">工程</div>
                </a></li>
                <li><a href="searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=40&currentPage=1">
                    <div class="z_ba1"><img src="image/mobile/w3.png"></div>
                    <div class="z_ba2">设计</div>
                </a></li>
                <li><a href="searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=41&currentPage=1">
                    <div class="z_ba1"><img src="image/mobile/w4.png"></div>
                    <div class="z_ba2">物管</div>
                </a></li>


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
                                           target="_blank" style="color: #000">${solution.title}</a>
                                    </div>
                                    <div class="zx_l2">${solution.category.TMCName}</div>
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
                                       target="_blank" class="z_rm1">${solution.title}</a>
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
    });

    //点击菜单事件
    function menuClick(TMCName) {
        alert(TMCName);
        if (TMCName == '成本')
            window.location.href = "searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=38&currentPage=1";
        else if (TMCName == '工程')
            window.location.href = "searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=39&currentPage=1";
        else if (TMCName == '设计')
            window.location.href = "searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=40&currentPage=1";
        else if (TMCName == '物管')
            window.location.href = "searchSolution_searchSolutionByCategory?flag=1&pageSize=15&TMCId=41&currentPage=1";

    }
</script>
</body>

</html>