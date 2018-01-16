<%@page import="br.ufpr.instituicao.Instituicao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<title>Gerenciamento de Funcionários</title>
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
				<a class="list-group-item" href="#"><i class="fa fa-credit-card"
					aria-hidden="true"></i>&nbsp;Funcionários</a>
				<a class="list-group-item" href="profile"> <i
					class="fa fa-wrench" aria-hidden="true"></i>&nbsp;Minha Conta
				</a>
				<span class="list-group-item">&nbsp;</span>
				<a class="list-group-item" href="logout"><i
					class="fa fa-sign-out" aria-hidden="true"></i>&nbsp;Logout</a>
			</ul>
		</div>
		<div class="col-lg-9">

			<div class="row">
				<div class="col-lg-9">
					<h3>
						Gerenciamento de Funcionários &nbsp;
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#funcionarioModal">Novo Funcionário</button>
					</h3>
					<hr>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<form action="funcionarios" method="GET">
						<div class="form-group">
							<div class="input-group">
								<input type="text" class="form-control" placeholder="Pesquisar"
									name="pesquisa"> <span class="input-group-btn">
									<button type="submit" class="btn btn-default">
										<i class="fa fa-search" aria-hidden="true"></i>&nbsp;Pesquisar
									</button>
								</span>&nbsp; <span class="input-group-btn"><a
									href="funcionarios" class="btn btn-default"> <i
										class="fa fa-paint-brush" aria-hidden="true"></i>&nbsp;Limpar
								</a></span>
							</div>
						</div>
					</form>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<h3>Funcionários Cadastrados</h3>
					<table class="table table-striped table-hover ">
						<thead>
							<tr>
								<th></th>
								<th>#</th>
								<th>Email</th>
								<th>Último Acesso</th>
							</tr>
						</thead>
						<tbody>

							<c:forEach var="funcionario" items="${funcionarios}">
								<tr>
									<td><a class="btn btn-sm btn-primary"
										href="funcionarios?id=${funcionario.id}"> <i
											class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</a> <a class="btn btn-sm btn-default"
										href="funcionarios?id=${funcionario.id}&modal=true"> <i
											class="fa fa-unlock-alt" aria-hidden="true"
											data-placement="right" title="Redefinir Senha"></i>
									</a></td>
									<td>${funcionario.nome}</td>
									<td><a href="mailto:${funcionario.email}">${funcionario.email}</a></td>
									<td><c:if test="${funcionario.ultimoAcesso!=null}">
											<fmt:formatDate pattern="dd-MM-yyyy"
												value="${funcionario.ultimoAcesso}" />
										</c:if> <c:if test="${funcionario.ultimoAcesso==null}">
											Nenhum Acesso
										</c:if></td>

								</tr>
							</c:forEach>
						</tbody>
					</table>
					<ul class="pager">
						<c:if test="${param.s-10>=0}">
							<li><a href="funcionarios?s=${param.s-10}&q=10">&larr;
									Anterior</a></li>
						</c:if>
						<li><a href="funcionarios?s=${param.s+10}&q=10">Próxima
								&rarr;</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>

<div class="modal fade" id="funcionarioModal">
	<div class="modal-dialog">
		<form id="funcionarioForm"
			action="GerenciarFuncionarios?action=salvar" method="POST">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Funcionário</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="inputNome" class="col-lg-2 control-label">Nome:</label>
						<div class="col-lg-10">
							<input type="hidden" name="id" value="${f.id}"> <input
								type="hidden" name="senha" value="${f.senha}"> <input
								type="text" class="form-control" id="inputNome" name="nome"
								placeholder="Nome" value="${f.nome}" autofocus> <br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-lg-2 control-label">Email:</label>
						<div class="col-lg-10">
							<input type="email" class="form-control" id="inputEmail"
								name="email" required="" placeholder="Email" value="${f.email}">
							<br />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">Status</label>
						<div class="col-lg-10">
							<div class="radio">
								<label> <input type="radio" name="status"
									id="optionsRadios1" value="ativo" checked=""> Ativo
								</label>
							</div>
							<div class="radio">
								<label> <input type="radio" name="status"
									id="optionsRadios2" value="inativo"> Inativo
								</label>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<br /> <br /> <br />
					<button type="reset" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" id="salvarBt" class="btn btn-primary">Salvar</button>
				</div>
			</div>
		</form>
	</div>
</div>

<div class="modal fade" id="confirmacaoSenhaModal">
	<div class="modal-dialog">
		<form action="GerenciarFuncionarios" method="GET">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Confirmação</h4>
				</div>
				<div class="modal-body">

					<input type="hidden" value="${param.id}" name="id">

					<p>Você realmente deseja redefinir a senha de acesso deste
						Funcionário?</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="submit" class="btn btn-danger">Redefinir</button>
				</div>
			</div>
		</form>
	</div>
</div>

<div class="modal fade" id="senhaAlteradaModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">&times;</button>
				<h4 class="modal-title">Confirmação</h4>
			</div>
			<div class="modal-body">
				<p>Senha redefinida com sucesso</p>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>
			</div>
		</div>
	</div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/additional-methods.min.js"></script>
<script src="js/jquery.mask.min.js"></script>

<script type="text/javascript" src="js/localization/messages_pt_BR.js"></script>

<c:if test="${f!=null && param.modal==null}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#funcionarioModal').modal('show');
		});
	</script>
</c:if>

<c:if test="${param.id!=null && param.modal!=null}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#confirmacaoSenhaModal').modal('show');
		});
	</script>
</c:if>

<c:if test="${param.alterada!=null}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#senhaAlteradaModal').modal('show');
		});
	</script>
</c:if>

<script src="js/pages/funcionarios.js" charset="UTF-8"></script>

</html>