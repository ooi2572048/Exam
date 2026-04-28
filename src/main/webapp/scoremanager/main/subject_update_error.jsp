<%-- 科目情報変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">
        科目情報変更 | 得点管理システム
    </c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
            
            <div class="mt-5 px-4">
                <c:if test="${not empty errors}">
                    <div class="alert alert-danger">
                        <c:forEach var="error" items="${errors}">
                            <div><c:out value="${error}" /></div>
                        </c:forEach>
                    </div>
                </c:if>

                <form action="SubjectUpdateExecute.action" method="post">
                    <div class="mb-4">
                        <label class="form-label d-block">科目コード</label>
                        <input type="text" name="cd" value="${subject.cd}" 
                               class="form-control-plaintext border-bottom w-25" readonly>
                    </div>

                    <div class="mb-4">
                        <label class="form-label d-block">科目名</label>
                        <input type="text" name="name" value="${not empty name ? name : subject.name}" 
                               class="form-control w-50" placeholder="科目名を入力してください" 
                               maxlength="20" required>
                    </div>

                    <div class="mt-5">
                        <button type="submit" class="btn btn-primary me-3">変更</button>
                        <a href="SubjectList.action">戻る</a>
                    </div>
                </form>
            </div>
        </section>
    </c:param>
</c:import>