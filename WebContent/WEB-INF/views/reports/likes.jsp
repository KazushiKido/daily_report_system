<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${likes != null}">
                <h2>いいね！した人</h2>
                <table id="report_list"> <%-- table名の修正いる --%>
                    <tbody>
                        <tr>
                            <th class="like_name">氏名</th>
                            <th class="like_report_date">いいね！した日付</th>
                        </tr>
                        <c:forEach var="likes" items="${likes}" varStatus="status">
                            <tr class="row${status.count % 2}">
                                <td class="like_report_id"><c:out value="${likes.employee.name}" /></td>
                                <td class="like_created_at"><fmt:formatDate value='${likes.created_at}' pattern='yyyy-MM-dd' /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div id="pagination">
                    （全 ${likes_count} 件）<br />
                    <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
                        <c:choose>
                            <c:when test="${i == page}">
                                <c:out value="${i}" />&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
            </c:otherwise>
        </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>