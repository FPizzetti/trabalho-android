<%@page import="br.ufpr.instituicao.Instituicao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<title>Gerenciamento de Clientes</title>
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
						Gerenciamento de Clientes &nbsp;
						<button type="button" class="btn btn-success" data-toggle="modal"
							data-target="#clienteModal">Adicionar Cliente</button>
					</h3>
					<hr>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<form action="clientes" method="GET">
						<div class="form-group">
							<div class="input-group">
								<input type="text" class="form-control" name="pesquisa"
									placeholder="Pesquisar"> <span class="input-group-btn">
									<button type="submit" class="btn btn-default">
										<i class="fa fa-search" aria-hidden="true"></i>&nbsp;Pesquisar
									</button>
								</span>&nbsp; <span class="input-group-btn"><a href="clientes"
									class="btn btn-default"> <i class="fa fa-paint-brush"
										aria-hidden="true"></i>&nbsp;Limpar
								</a></span>
							</div>
						</div>
					</form>
				</div>
			</div>

			<div class="row">
				<div class="col-lg-9">
					<h3>Clientes Cadastrados</h3>
					<table class="table table-striped table-hover ">
						<thead>
							<tr>
								<th></th>
								<th>#</th>
								<th>CPF/CNPJ</th>
								<th>Email</th>
								<th>Status</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="cliente" items="${clientes}">
								<tr>
									<td><a class="btn btn-sm btn-primary"
										href="clientes?cliente=${cliente.id}"> <i
											class="fa fa-pencil-square-o" aria-hidden="true"></i>
									</a> <a class="btn btn-sm btn-default"
										href="clientes?cliente=${cliente.id}&relatorio=true"> <i
											class="fa fa-bar-chart" aria-hidden="true"></i>
									</a></td>
									<td>${cliente.nome}</td>
									<td><c:if test="${cliente.identificacao.length()==11}">
										${fn:substring(cliente.identificacao, 0,
										3)}.${fn:substring(cliente.identificacao, 3,
										6)}.${fn:substring(cliente.identificacao, 6,
										9)}-${fn:substring(cliente.identificacao, 9, 11)}
									
									</c:if> <c:if test="${cliente.identificacao.length()==14}">
										${fn:substring(cliente.identificacao, 0,
										2)}.${fn:substring(cliente.identificacao, 2,
										5)}.${fn:substring(cliente.identificacao, 5,
										8)}/${fn:substring(cliente.identificacao, 8, 12)}-${fn:substring(cliente.identificacao, 12, 14)}
									
									</c:if></td>
									<td><a href="mailto:${cliente.email}">${cliente.email}</a></td>
									<c:if test="${!cliente.restrito}">
										<td class="text-success"><i
											class="fa fa-check-square-o fa-lg" aria-hidden="true"></i></td>
									</c:if>

									<c:if test="${cliente.restrito}">
										<td class="text-danger"><i
											class="fa fa-check-square-o fa-lg" aria-hidden="true"></i></td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<ul class="pager">
						<c:if test="${param.s-10>=0}">
							<li><a href="clientes?s=${param.s-10}&q=10">&larr;
									Anterior</a></li>
						</c:if>
						<li><a href="clientes?s=${param.s+10}&q=10">Próxima
								&rarr;</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>

<div class="modal fade" id="clienteModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="clienteForm" action="GerenciarClientes?action=salvar"
				method="POST">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Cliente</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label class="col-lg-4 control-label">Tipo Cliente</label>
						<div class="col-lg-8">
							<div class="radio tipo">
								<label> <input type="radio" name="tipo"
									id="optionsRadios1" value="cpf"
									<c:if test="${cliente.identificacao.length()==11 || empty cliente}">checked</c:if>>
									Pessoa Física
								</label>
							</div>
							<div class="radio">
								<label> <input type="radio" name="tipo"
									id="optionsRadios2" value="cpnj"
									<c:if test="${cliente.identificacao.length()==14}">checked</c:if>>
									Pessoa Jurídica
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="inputNome" class="col-lg-2 control-label">Nome:</label>
						<div class="col-lg-10">
							<input type="hidden" value="${cliente.id}" id="inputId" name="id">
							<input type="text" class="form-control" id="inputNome" autofocus
								name="nome" placeholder="Nome" value="${cliente.nome}">
							<br />
						</div>
					</div>
					<div class="form-group">
						<label id="labelCpf" for="inputCpfCnpj"
							class="col-lg-2 control-label">CPF:</label> <label id="labelCnpj"
							for="inputCpfCnpj" class="col-lg-2 control-label">CNPJ:</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="inputCpfCnpj"
								required="" name="identificacao" placeholder="Identificacao"
								value="${cliente.identificacao}"> <br />
						</div>
					</div>
					<div class="form-group" id="formRg">
						<label for="inputRg" id="labelRg" class="col-lg-2 control-label">RG:</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="inputRg" name="rg"
								value="${cliente.rg}" placeholder="RG">
						</div>
					</div>
					<div class="form-group">
						<label for="inputCpfCnpj" class="col-lg-2 control-label">Email:</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="inputEmail"
								name="email" placeholder="Email" value="${cliente.email}">
							<br />
						</div>
					</div>
					<div class="form-group">
						<label for="inputInstituicao" class="col-lg-2 control-label">Instituição:</label>
						<div class="col-lg-10">
							<select class="form-control" id="inputInstituicao"
								required="true" name="instituicao">
								<option value="">Selecione uma Instituição</option>
								<c:forEach var="instituicao" items="${instituicoes}">
									<option value="${instituicao.id}">${instituicao.nome}</option>
								</c:forEach>
							</select> <br />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-2 control-label">Status</label>
						<div class="col-lg-10">
							<div class="radio">
								<label> <input type="radio" name="status"
									id="optionsRadios1" value="restrito" checked="">
									Restrito
								</label>
							</div>
							<div class="radio">
								<label> <input type="radio" name="status"
									id="optionsRadios2" value="nrestrito"> Não Restrito
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
			</form>
		</div>
	</div>
</div>

<div class="modal fade" id="relatoriosModal">
	<div class="modal-dialog">
		<form id="relatorioForm" action="GerenciarFuncionarios" method="GET">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Relatórios</h4>
				</div>
				<div class="modal-body">
					<input type="hidden" value="${param.cliente}" name="cliente">
					<select id="tipoRelatorio" name="tipoRelatorio"
						class="form-control">
						<option value="">Selecione um Tipo de Relatório</option>
						<option value="relatorioHistorico">Histórico de
							Restrições</option>
						<option value="relatorioStatus">Status Atual do Cliente</option>
					</select>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button type="button" id="gerarBt" class="btn btn-primary">Gerar
						Relatório</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.validate.min.js"></script>
<script src="js/additional-methods.min.js"></script>
<script src="js/jquery.mask.min.js"></script>

<script type="text/javascript" src="js/localization/messages_pt_BR.js"></script>

<c:if test="${cliente!=null && param.relatorio==null}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#clienteModal').modal('show');
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

<c:if test="${param.cliente!=null && param.relatorio!=null}">
	<script type="text/javascript">
		$(window).load(function() {
			$('#relatoriosModal').modal('show');
		});
	</script>
</c:if>

<script src="js/pages/clientes.js" charset="UTF-8"></script>

</html>