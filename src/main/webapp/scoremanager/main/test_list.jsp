<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <%-- タイトル：検索前は「成績参照」、検索後は各一覧名に --%>
            <h2 class="h3 mb-3 fw-normal">
                <c:choose>
                    <c:when test="${title_type == 'subject'}">成績一覧（科目）</c:when>
                    <c:when test="${title_type == 'student'}">成績一覧（学生）</c:when>
                    <c:otherwise>成績参照</c:otherwise>
                </c:choose>
            </h2>
            
            <form action="TestList.action" method="get">
                <div class="border border-1 p-4 mb-3" style="background-color: #f8f9fa;">
                    <%-- 科目情報セクション --%>
                    <div class="d-flex align-items-center mb-3">
                        <p class="fw-bold mb-0 me-3" style="width: 100px;">科目情報</p>
                        <div class="row g-3 flex-grow-1">
                            <div class="col-md-3">
                                <label class="form-label small">入学年度</label>
                                <select name="f1" class="form-select">
                                    <option value="0">--------</option>
                                    <c:forEach var="year" items="${ent_year_set}">
                                        <option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small">クラス</label>
                                <select name="f2" class="form-select">
                                    <option value="0">--------</option>
                                    <c:forEach var="c" items="${class_num_set}">
                                        <option value="${c}" <c:if test="${c == f2}">selected</c:if>>${c}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3">
                                <label class="form-label small">科目</label>
                                <select name="f3" class="form-select">
                                    <option value="0">--------</option>
                                    <c:forEach var="s" items="${subjects}">
                                        <option value="${s.cd}" <c:if test="${s.cd == f3}">selected</c:if>>${s.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-3 d-flex align-items-end">
                                <button class="btn btn-secondary w-100">検索</button>
                            </div>
                        </div>
                    </div>

                    <hr>

                    <%-- 学生情報セクション --%>
                    <div class="d-flex align-items-center">
                        <p class="fw-bold mb-0 me-3" style="width: 100px;">学生情報</p>
                        <div class="row g-3 flex-grow-1">
                            <div class="col-md-9">
                                <label class="form-label small">学生番号</label>
                                <input type="text" name="f4" value="${f4}" class="form-control" placeholder="学生番号を入力してください">
                            </div>
                            <div class="col-md-3 d-flex align-items-end">
                                <button class="btn btn-secondary w-100">検索</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <%-- メッセージ・結果表示エリア --%>
            <c:choose>
                <%-- 1. デフォルト画面（検索前：title_typeが空の時） --%>
                <c:when test="${empty title_type}">
                    <div class="mt-3">
                        <p>入学年度とクラスと科目を選択してください</p>
                    </div>
                </c:when>

                <%-- 2. 科目検索の結果 --%>
                <c:when test="${title_type == 'subject'}">
                    <c:choose>
                        <%-- 入力不足エラー（Actionからerror_type='incomplete'が送られた場合） --%>
                        <c:when test="${error_type == 'incomplete'}">
                            <div class="mt-3">
                                <p>入学年度とクラスと科目を選択してください</p>
                            </div>
                        </c:when>
                        <%-- 正常にデータがある場合 --%>
                        <c:when test="${not empty tests_subject}">
                            <table class="table table-hover mt-3">
                                <thead class="table-light">
                                    <tr>
                                        <th>入学年度</th><th>クラス</th><th>学生番号</th><th>氏名</th><th>1回</th><th>2回</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${tests_subject}">
                                        <tr>
                                            <td>${t.entYear}</td><td>${t.classNum}</td><td>${t.studentNo}</td><td>${t.studentName}</td>
                                            <td>${t.point1 == -1 ? '-' : t.point1}</td>
                                            <td>${t.point2 == -1 ? '-' : t.point2}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <%-- 検索結果が0件の場合 --%>
                        <c:otherwise>
                            <div class="mt-3">
                                <p>成績情報が存在しませんでした</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>

                <%-- 3. 学生検索の結果 --%>
                <c:when test="${title_type == 'student'}">
                    <c:choose>
                        <%-- 正常にデータがある場合 --%>
                        <c:when test="${not empty tests_student}">
                            <div class="mt-4 mb-3">
                                <p class="fs-5">氏名：${student_info.studentName} (${student_info.studentNo})</p>
                            </div>
                            <table class="table table-hover">
                                <thead class="table-light">
                                    <tr>
                                        <th>科目名</th><th>科目コード</th><th>回数</th><th>点数</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="t" items="${tests_student}">
                                        <tr>
                                            <td>${t.subjectName}</td><td>${t.subjectCd}</td><td>${t.num}</td><td>${t.point}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <%-- 検索結果が0件または学生が見つからない場合 --%>
                        <c:otherwise>
                            <div class="mt-3">
                                <p>成績情報が存在しませんでした</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
        </section>
    </c:param>
</c:import>