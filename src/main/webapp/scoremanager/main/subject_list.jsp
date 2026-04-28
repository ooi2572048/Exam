list.jsp
 
<%-- 科目一覧JSP --%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<c:param name="title">

        科目管理 | 得点管理システム
</c:param>
 
    <c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>
 
            <div class="text-end mb-3 px-4">
<a href="SubjectCreate.action">新規登録</a>
</div>
 
            <div class="px-4">
<table class="table table-hover">
<thead>
<tr>
<th>科目コード</th>
<th>科目名</th>
<th></th>
<th></th>
</tr>
</thead>
<tbody>
<c:forEach var="subject" items="${subjects}">
<tr>
<td><c:out value="${subject.cd}" /></td>
<td><c:out value="${subject.name}" /></td>
<td><a href="SubjectUpdate.action?cd=${subject.cd}">変更</a></td>
<td><a href="SubjectDelete.action?cd=${subject.cd}">削除</a></td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
</section>
</c:param>
</c:import>
 
subject_create.jsp
 
 
<%-- 科目情報登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
 
<c:import url="/common/base.jsp">
<c:param name="title">

        科目情報登録 | 得点管理システム
</c:param>
 
    <c:param name="content">
<section class="me-4">
<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
 
            <div class="mt-5 px-4">
<%-- エラーメッセージ表示 --%>
<c:if test="${not empty errors}">
<div class="alert alert-danger">
<c:forEach var="error" items="${errors}">
<div><c:out value="${error}" /></div>
</c:forEach>
</div>
</c:if>
 
                <form action="SubjectCreateExecute.action" method="post">
<div class="mb-4">
<label for="cd" class="form-label">科目コード</label>
<input type="text" id="cd" name="cd" value="${cd}" 

                               class="form-control w-25" placeholder="科目コードを入力してください" 

                               maxlength="3" required>
</div>
 
                    <div class="mb-4">
<label for="name" class="form-label">科目名</label>
<input type="text" id="name" name="name" value="${name}" 

                               class="form-control w-50" placeholder="科目名を入力してください" 

                               maxlength="20" required>
</div>
 
                    <div class="mt-5">
<button type="submit" class="btn btn-primary me-3">登録</button>
<a href="SubjectList.action">戻る</a>
</div>
</form>
</div>
</section>
</c:param>
</c:import>
 