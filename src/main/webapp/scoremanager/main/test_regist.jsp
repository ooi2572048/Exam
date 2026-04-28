<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">成績管理</c:param>

    <c:param name="content">
        <section class="container-fluid mt-4">
            <h2 class="h4 border-bottom pb-2 mb-4">成績管理</h2>

            <%-- 上段：検索条件指定エリア --%>
            <form action="TestRegist.action" method="get">
                <div class="row g-3 align-items-end mb-4 bg-light p-3 rounded border mx-1">
                    <div class="col-md-2">
                        <label class="form-label small">入学年度</label>
                        <select class="form-select" name="f1">
                            <option value="0">--------</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label small">クラス</label>
                        <select class="form-select" name="f2">
                            <option value="0">--------</option>
                            <c:forEach var="num" items="${class_nums}">
                                <option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-3">
                        <label class="form-label small">科目</label>
                        <select class="form-select" name="f3">
                            <option value="0">--------</option>
                            <c:forEach var="subject" items="${subjects}">
                                <option value="${subject.cd}" <c:if test="${subject.cd == f3}">selected</c:if>>${subject.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <label class="form-label small">回数</label>
                        <select class="form-select" name="f4">
                            <option value="0">--------</option>
                            <c:forEach var="i" begin="1" end="10">
                                <option value="${i}" <c:if test="${i == f4}">selected</c:if>>${i}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <button type="submit" class="btn btn-secondary w-100">検索</button>
                    </div>
                </div>
            </form>

            <%-- 下段：成績入力エリア (testsが存在する場合のみ表示) --%>
            <c:if test="${not empty tests}">
                <div class="mt-4 mb-2 fw-bold">
                    科目：${tests[0].subject.name} （${f4}回）
                </div>

                <form action="TestRegistExecute.action" method="post">
                    <input type="hidden" name="subject_cd" value="${f3}">
                    <input type="hidden" name="no" value="${f4}">
                    <input type="hidden" name="class_num" value="${f2}">

                    <table class="table table-hover mt-3">
                        <thead>
                            <tr class="table-light">
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>学生番号</th>
                                <th>氏名</th>
                                <th>点数</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="test" items="${tests}">
                                <tr class="align-middle">
                                    <td>${test.student.entYear}</td>
                                    <td>${test.classNum}</td>
                                    <td>${test.student.studentNo}</td>
                                    <td>${test.student.studentName}</td>
                                    <td>
                                        <input type="number" name="point" value="${test.point}" 
                                               class="form-control" style="max-width: 300px;" 
                                               min="0" max="100">
                                        <input type="hidden" name="student_no" value="${test.student.studentNo}">
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <div class="mt-4">
                        <button type="submit" class="btn btn-secondary px-4">登録して終了</button>
                    </div>
                </form>
            </c:if>
        </section>
    </c:param>
</c:import>