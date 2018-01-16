<%@page import="br.ufpr.instituicao.Instituicao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<a class="list-group-item" href="instituicoes"><i
					class="fa fa-university" aria-hidden="true"></i>&nbsp;Instituições</a>
				<a class="list-group-item" href="clientes"><i
					class="fa fa-users" aria-hidden="true"></i>&nbsp;Clientes</a>
				<c:if test="${funcionario.isAdmin}">
					<a class="list-group-item" href="funcionarios"><i
						class="fa fa-credit-card" aria-hidden="true"></i>&nbsp;Funcionários</a>
				</c:if>
				<a class="list-group-item" href="#"><i class="fa fa-wrench"
					aria-hidden="true"></i>&nbsp;Minha Conta</a>
				<span class="list-group-item">&nbsp;</span>
				<a class="list-group-item" href="logout"><i
					class="fa fa-sign-out" aria-hidden="true"></i>&nbsp;Logout</a>
			</ul>
		</div>
		<div class="col-lg-9">

			<div class="row">
				<div class="col-lg-9">
					<h3>Minha Conta</h3>
					<hr>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<c:if test="${funcionario.ultimoAcesso!=null}">
						<p>
							Data de Último Acesso:
							<fmt:formatDate pattern="dd-MM-yyyy HH:mm"
								value="${funcionario.ultimoAcesso}" />
						</p>
					</c:if>
					<form action="GerenciarConta?action=alterarEmail" method="POST">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-btn">
									<button type="submit" class="btn btn-default">
										<i class="fa fa-at" aria-hidden="true"></i>
									</button>
								</span><input type="email" class="form-control"
									style="padding-left: 10px;" value="${funcionario.email}"
									name="emailReq" required="" placeholder="Meu Email"> <span
									class="input-group-btn" autofocus>
									<button type="submit" class="btn btn-default">
										<i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;Alterar
									</button>
								</span>
							</div>
						</div>
					</form>

					<c:if test="${mensagemEmail!=null}">
						<c:if test="${mensagemEmail.status}">
							<div class="label label-success">${mensagemEmail.descricao}</div>
						</c:if>
						<c:if test="${!mensagemEmail.status}">
							<div class="label label-danger">${mensagemEmail.descricao}</div>
						</c:if>
						<br />
						<br />
					</c:if>

					<form class="form-horizontal"
						action="GerenciarConta?action=alterarSenha" method="POST">
						<div class="panel panel-warning">
							<div class="panel-heading">
								<h3 class="panel-title">Alterar Senha</h3>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<label for="inputEmail" class="col-lg-2 control-label">Senha
										Atual:</label>
									<div class="col-lg-10">
										<input type="password" required="" name="senhaAtual"
											class="form-control" id="inputEmail"
											placeholder="Senha Atual">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail" class="col-lg-2 control-label">Nova
										Senha:</label>
									<div class="col-lg-10">
										<input type="password" required="" name="novaSenha"
											class="form-control" id="inputEmail" placeholder="Nova Senha">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail" class="col-lg-2 control-label">Confirmação:</label>
									<div class="col-lg-10">
										<input type="password" class="form-control" required=""
											name="confirmacaoSenha" id="inputEmail"
											placeholder="Confirmação">
									</div>
								</div>
								<div class="pull-left">
									<c:if test="${mensagem.status}">
										<span class="label label-success">${mensagem.descricao}</span>
									</c:if>
									<c:if test="${!mensagem.status}">
										<span class="label label-danger">${mensagem.descricao}</span>
									</c:if>
								</div>

								<div class="pull-right">
									<button class="btn btn-default">
										<i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;Alterar
									</button>

								</div>
							</div>
						</div>
					</form>

					<div class="form-group">
						<c:if test="${!funcionario.isAdmin}">
							<button class="btn btn-danger" data-toggle="modal"
								data-target="#confirmacaoInativarModal">Desativar Conta</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<div class="modal fade" id="confirmacaoInativarModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<form action="GerenciarConta" method="GET">
				<input type="hidden" value="${funcionario.id}" name="id">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Confirmação</h4>
				</div>
				<div class="modal-body">
					<p>Você realmente deseja desativar sua conta?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-danger">Inativar</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</html>