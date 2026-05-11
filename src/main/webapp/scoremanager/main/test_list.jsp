<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <%-- タイトル：検索前は「成績参照」、検索後は各一覧名に --%>
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">
                <c:choose>
                    <c:when test="${title_type == 'subject'}">成績一覧（科目）</c:when>
                    <c:when test="${title_type == 'student'}">成績一覧（学生）</c:when>
                    <c:otherwise>成績参照</c:otherwise>
                </c:choose>
            </h2>

      
            
            <form action="TestList.action" method="get">
                <div class="border border-1 p-4 mb-3" ">
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
                            <div class="col-auto d-flex align-items-end">
                                <button class="btn btn-secondary ">検索</button>
                            </div>
                        </div>
                    </div>
					
					<div class="mb-3 ms-5">
   						 <c:if test="${title_type == 'subject' && error_type == 'incomplete'}">
        					<p style="color: #ffc107; font-weight: bold;">
            					入学年度とクラスと科目を選択してください
        					</p>
    					</c:if>
					</div>
				
                    <hr>

                    <%-- 学生情報セクション --%>
                    <div class="d-flex align-items-center">
                        <p class="fw-bold mb-0 me-3" style="width: 100px;">学生情報</p>
                        <div class="row g-3 flex-grow-1">
                            <div class="col-md-6">
                                <label class="form-label small">学生番号</label>
                                <input type="text" name="f4" value="${f4}" class="form-control" placeholder="学生番号を入力してください">
                            </div>
                            <div class="col-auto d-flex align-items-end">
                                <button class="btn btn-secondary ">検索</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <%-- フォームの下：初期案内（青文字）や、その他のステータス表示 --%>
            <div class="mb-3">
                <c:choose>
                    <%-- 1. 画面を開いた初期状態（検索前）は青文字を表示 --%>
                    <c:when test="${empty title_type}">
                        <p class="text-primary">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
                    </c:when>
                    
                    <%-- 2. 検索した結果、成績が存在しない場合（黒文字） --%>
                    <c:when test="${error_type == 'empty'}">
                        <p>成績情報が存在しませんでした</p>
                    </c:when>

                    <%-- 3. 学生検索成功時の氏名表示 --%>
                    <c:when test="${title_type == 'student' && not empty tests_student}">
                        <p class="fs-5 mb-3">氏名：${student_info.studentName} (${student_info.studentNo})</p>
                    </c:when>
                </c:choose>
            </div>

            <%-- 結果表示テーブル --%>
            <c:choose>
                <%-- 科目検索結果 --%>
                <c:when test="${not empty tests_subject}">
                    <table class="table table-hover mt-3">
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

                <%-- 学生検索結果 --%>
                <c:when test="${not empty tests_student}">
                    <table class="table table-hover mt-3">
                        
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
            </c:choose>
        </section>
    </c:param>
</c:import>