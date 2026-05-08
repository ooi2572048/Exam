<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <%-- タイトルの切り替えロジック --%>
            <c:choose>
                <c:when test="${title_type == 'subject'}">
                    <h2 class="h3 mb-3 fw-normal">成績一覧（科目）</h2>
                </c:when>
                <c:when test="${title_type == 'student'}">
                    <h2 class="h3 mb-3 fw-normal">成績一覧（学生）</h2>
                </c:when>
                <c:otherwise>
                    <h2 class="h3 mb-3 fw-normal">成績参照</h2>
                </c:otherwise>
            </c:choose>
            
            <form action="TestList.action" method="get">
                <div class="border border-1 p-4 mb-3">
                    <div class="d-flex align-items-center mb-4">
                        <p class="fw-bold mb-0 me-2 text-nowrap" style="width: 90px;">科目情報</p>
                        <div class="d-flex align-items-center gap-2 flex-nowrap">
                            <label>入学年度</label>
                            <select name="f1" class="form-select" style="width: 95px;">
                                <option value="0">--------</option>
                                <c:forEach var="year" items="${ent_year_set}">
                                    <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                </c:forEach>
                            </select>
                            <label class="ms-1">クラス</label>
                            <select name="f2" class="form-select" style="width: 95px;">
                                <option value="0">--------</option>
                                <c:forEach var="num" items="${class_num_set}">
                                    <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                                </c:forEach>
                            </select>
                            <label class="ms-1">科目</label>
                            <select name="f3" class="form-select" style="width: 170px;">
                                <option value="0">--------</option>
                                <c:forEach var="subject" items="${subjects}">
                                    <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                                </c:forEach>
                            </select>
                            <button type="submit" class="btn btn-secondary ms-2">検索</button>
                        </div>
                    </div>
                    <hr class="my-4">
                    <div class="d-flex align-items-center">
                        <p class="fw-bold mb-0 me-2 text-nowrap" style="width: 90px;">学生情報</p>
                        <div class="d-flex align-items-center gap-2">
                            <label>学生番号</label>
                            <input type="text" name="f4" class="form-control" placeholder="学生番号を入力してください" value="${f4}" style="width: 250px;">
                            <button type="submit" class="btn btn-secondary ms-2">検索</button>
                        </div>
                    </div>
                </div>
            </form>

            <c:choose>
                <c:when test="${not empty tests}">
                    <div class="mt-4">
                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>入学年度</th>
                                    <th>クラス</th>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th>1回</th>
                                    <th>2回</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${tests}">
                                    <tr>
                                        <td>${test.entYear}</td>
                                        <td>${test.classNum}</td>
                                        <td>${test.studentNo}</td>
                                        <td>${test.studentName}</td>
                                        <%-- 点数が -1（未設定）の場合はハイフンを表示する --%>
                                        <td><c:choose><c:when test="${test.point1 == -1}">-</c:when><c:otherwise>${test.point1}</c:otherwise></c:choose></td>
                                        <td><c:choose><c:when test="${test.point2 == -1}">-</c:when><c:otherwise>${test.point2}</c:otherwise></c:choose></td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:when>
                <c:when test="${tests != null && tests.size() == 0}">
                    <p class="text-danger mt-3">学生情報が存在しませんでした</p>
                </c:when>
            </c:choose>
        </section>
    </c:param>
</c:import>