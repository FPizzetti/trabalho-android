<%@page import="br.ufpr.instituicao.Instituicao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<title>Gerenciamento de Instituições</title>
</head>
<body>

	<jsp:useBean id="funcionario" class="br.ufpr.funcionario.Funcionario"
		scope="session" />
	<jsp:setProperty property="*" name="funcionario" />
	<div class="row">
		<div class="col-lg-3">
			<ul class="list-group">
				<a class="list-group-item" href="dashboard"><h3>
						<i class="fa fa-home" aria-hidden="true"></i> Sistema DOR
					</h3></a>
				<span class="list-group-item">&nbsp;</span>
				<span class="list-group-item">Usuário: <jsp:getProperty
						name="funcionario" property="email"></jsp:getProperty></span>
				<span class="list-group-item">&nbsp;</span>
				<a class="list-group-item" href="#"><i class="fa fa-university"
					aria-hidden="true"></i>&nbsp;Instituições</a>
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
						Gerenciamento de Instituições &nbsp;
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#instituicaoModal">Nova Instituição</button>
					</h3>
					<hr>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<form action="instituicoes" method="GET">
						<div class="form-group">
							<div class="input-group">
								<input type="text" class="form-control" name="pesquisa"
									placeholder="Pesquisar"> <span class="input-group-btn">
									<button type="submit" class="btn btn-default">
										<i class="fa fa-search" aria-hidden="true"></i>&nbsp;Pesquisar
									</button>
								</span>&nbsp; <span class="input-group-btn"><a
									href="instituicoes" class="btn btn-default"> <i
										class="fa fa-paint-brush" aria-hidden="true"></i>&nbsp;Limpar
								</a></span>
							</div>
						</div>
					</form>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<h3>Instituições Cadastradas</h3>
					<table class="table table-striped table-hover ">
						<thead>
							<tr>
								<th></th>
								<th>#</th>
								<th>Data Cadastro</th>
								<th>CNPJ</th>
								<th>Status</th>
								<th></th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="instituicao" items="${instituicoes}">

								<tr>
									<td><a class="btn btn-sm btn-primary"
										href="instituicoes?id=${instituicao.id}"> <i
											class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</a></td>
									<td>${instituicao.nome}</td>
									<td><fmt:formatDate pattern="dd-MM-yyyy"
											value="${instituicao.dataCadastro}" /></td>
									<td><c:if test="${instituicao.cnpj.length()==14}">
										${fn:substring(instituicao.cnpj, 0,2)}.${fn:substring(instituicao.cnpj, 2, 5)}.${fn:substring(instituicao.cnpj, 5,8)}/${fn:substring(instituicao.cnpj, 8, 12)}-${fn:substring(instituicao.cnpj, 12, 14)}	
									</c:if></td>
									<c:if test="${instituicao.isAtiva}">
										<td class="text-success"><i
											class="fa fa-check-square-o fa-lg" aria-hidden="true"></i></td>
									</c:if>
									<c:if test="${!instituicao.isAtiva}">
										<td class="text-danger"><i
											class="fa fa-check-square-o fa-lg" aria-hidden="true"></i></td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<ul class="pager">
						<c:if test="${param.s-10>=0}">
							<li><a href="instituicoes?s=${param.s-10}&q=10">&larr;
									Anterior</a></li>
						</c:if>
						<li><a href="instituicoes?s=${param.s+10}&q=10">Próxima
								&rarr;</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="instituicaoModal">
		<div class="modal-dialog">
			<form id="instituicaoForm"
				action="GerenciarInstituicoes?action=salvar" method="POST">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title">Instituição</h4>
					</div>
					<div class="modal-body">
						<div class="form-group">
							<label for="inputNome" class="col-lg-2 control-label">Nome:</label>
							<div class="col-lg-10">
								<input type="hidden" id="inputId" name="id"
									value="${instituicao.id}"> <input type="text"
									class="form-control" name="nome" required="" id="inputNome"
									value="${instituicao.nome}" placeholder="Nome" autofocus>
								<br />
							</div>
						</div>
						<div class="form-group">
							<label for="inputCnpj" class="col-lg-2 control-label">CNPJ:</label>
							<div class="col-lg-10">
								<input type="text" class="form-control" name="cnpj" required=""
									value="${instituicao.cnpj}" id="inputCnpj" placeholder="CNPJ">
								<br />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-2 control-label">Status</label>
							<div class="col-lg-10">
								<div class="radio">
									<label> <input type="radio" name="ativa"
										id="optionsRadios1" value="ativa" checked=""> Ativa
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="ativa"
										id="optionsRadios2" value="inativa"> Inativa
									</label>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<br /> <br /> <br />
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button type="button" id="salvarBt" class="btn btn-primary">Salvar</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/additional-methods.min.js"></script>
<script src="js/jquery.mask.min.js"></script>

<script type="text/javascript" src="js/localization/messages_pt_BR.js"></script>

<c:if test="${instituicao!=null}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#instituicaoModal').modal('show');
		});
	</script>
</c:if>

<script src="js/pages/instituicoes.js" charset="UTF-8"></script>

</html>