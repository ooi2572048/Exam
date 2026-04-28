<%-- 科目情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

            <div class="mt-5 px-4">
                <c:if test="${not empty errors}">
                    <div class="text-danger mb-3">
                        <c:forEach var="error" items="${errors}">
                            <p><c:out value="${error}" /></p>
                        </c:forEach>
                    </div>
                </c:if>

                <form action="SubjectUpdateExecute.action" method="post">
                    <div class="mb-3">
                        <label class="form-label">科目コード</label>
                        <input type="text" name="cd" value="${subject.cd}" 
                               class="form-control-plaintext border-bottom w-25" readonly>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">科目名</label>
                        <input type="text" name="name" value="${subject.name}" 
                               class="form-control w-50" maxlength="20" required 
                               placeholder="科目名を入力してください">
                    </div>

                    <div class="mt-4">
                        <button type="submit" class="btn btn-primary">変更</button>
                        <div class="mt-3">
                            <a href="SubjectList.action">戻る</a>
                        </div>
                    </div>
                </form>
            </div>
        </section>
    </c:param>
</c:import>