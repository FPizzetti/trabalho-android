<%@page import="br.ufpr.instituicao.Instituicao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<title>Dashboard</title>
</head>
<body>
	<jsp:useBean id="funcionario" class="br.ufpr.funcionario.Funcionario"
		scope="session" />
	<jsp:setProperty property="*" name="funcionario" />
	<div class="row">
		<div class="col-lg-3">
			<ul class="list-group">
				<a class="list-group-item" href="#"><h3>
						<i class="fa fa-home" aria-hidden="true"></i> Sistema DOR
					</h3></a>
				<span class="list-group-item">&nbsp;</span>
				<span class="list-group-item">Usuário: <jsp:getProperty
						name="funcionario" property="email"></jsp:getProperty></span>
				<span class="list-group-item">&nbsp;</span>
				<a class="list-group-item" href="instituicoes"><i
					class="fa fa-university" aria-hidden="true"></i>&nbsp;Instituições</a>
				<a class="list-group-item" href="clientes"><i
					class="fa fa-users" aria-hidden="true"></i>&nbsp;Clientes</a>
				<c:if test="${funcionario.isAdmin}">
					<a class="list-group-item" href="funcionarios"><i
						class="fa fa-credit-card" aria-hidden="true"></i>&nbsp;Funcionários</a>
				</c:if>
				<a class="list-group-item" href="profile"><i
					class="fa fa-wrench" aria-hidden="true"></i>&nbsp;Minha Conta</a>
				<span class="list-group-item">&nbsp;</span>
				<a class="list-group-item" href="logout"><i
					class="fa fa-sign-out" aria-hidden="true"></i>&nbsp;Logout</a>
			</ul>
		</div>
		<div class="col-lg-9">

			<div class="row">
				<div class="col-lg-9">
					<h3>
						Funcionário:
						<jsp:getProperty property="nome" name="funcionario" />
					</h3>
					<hr>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-3">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Clientes Restritos</h3>
						</div>
						<div class="panel-body text-center">${clientesRestritos}</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Total de Clientes</h3>
						</div>
						<div class="panel-body text-center">${totalClientes}</div>
					</div>
				</div>
				<div class="col-lg-3">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<h3 class="panel-title">Clientes não Restritos</h3>
						</div>
						<div class="panel-body text-center">${clientesNaoRestritos}</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<h3>Últimas Alterações</h3>
					<table class="table table-striped table-hover ">
						<thead>
							<tr>
								<th>#</th>
								<th>CPF/CNPJ</th>
								<th>Instituição</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="operacao" items="${operacoes}">
								<tr>
									<td>${operacao.cliente.nome}</td>
									<td><c:if
											test="${operacao.cliente.identificacao.length()==11}">
										${fn:substring(operacao.cliente.identificacao, 0,
										3)}.${fn:substring(operacao.cliente.identificacao, 3,
										6)}.${fn:substring(operacao.cliente.identificacao, 6,
										9)}-${fn:substring(operacao.cliente.identificacao, 9, 11)}
									
									</c:if> <c:if test="${operacao.cliente.identificacao.length()==14}">
										${fn:substring(operacao.cliente.identificacao, 0,
										2)}.${fn:substring(operacao.cliente.identificacao, 2,
										5)}.${fn:substring(operacao.cliente.identificacao, 5,
										8)}/${fn:substring(operacao.cliente.identificacao, 8, 12)}-${fn:substring(operacao.cliente.identificacao, 12, 14)}
									
									</c:if></td>
									<td><c:if test="${!empty operacao.instituicao}"> ${operacao.instituicao.nome} </c:if>
										<c:if test="${empty operacao.instituicao}"> Sistema VSF - Cheque Especial </c:if></td>
									<c:if test="${operacao.isRestricao}">
										<td class="text-danger"><i
											class="fa fa-check-square-o fa-lg" aria-hidden="true"></i></td>
									</c:if>
									<c:if test="${!operacao.isRestricao}">
										<td class="text-success"><i
											class="fa fa-check-square-o fa-lg" aria-hidden="true"></i></td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

</body>

<div class="modal fade" id="alterarSenhaModal">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">Atenção</h4>
			</div>
			<div class="modal-body">
				<p>
					<i class="fa fa-warning"></i>&nbsp;Sua conta está utilizando a
					senha padrão. Altere sua senha agora mesmo!
				</p>
			</div>
			<div class="modal-footer">
				<a href="profile" class="btn btn-warning">Alterar Senha</a>
			</div>
			</form>
		</div>
	</div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<c:if test="${funcionario.senha==funcionario.emailHash}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#alterarSenhaModal').modal('show');
		});
	</script>
</c:if>

</html>