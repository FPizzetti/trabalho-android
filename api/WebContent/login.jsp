<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/custom.css" rel="stylesheet">
<link href="css/font-awesome.min.css" rel="stylesheet">
<title>Sistema DOR</title>
</head>
<body class="login-bg">

	<div class="row">
		<div class="col-lg-3"></div>
		<div class="col-lg-6">
			<div class="well bs-component login-box">
				<form class="form-horizontal" action="login" method="POST">
					<fieldset>
						<h3>Sistema DOR</h3>
						<hr>
						<div class="form-group">
							<label for="inputEmail" class="col-lg-2 control-label">Email:</label>
							<div class="col-lg-10">
								<input type="text" name="email" class="form-control" required=""
									id="inputEmail" placeholder="Email" autofocus>
							</div>
						</div>
						<div class="form-group">
							<label for="inputSenha" class="col-lg-2 control-label">Senha:</label>
							<div class="col-lg-10">
								<input type="password" name="senha" class="form-control"
									required="" id="inputSenha" placeholder="Senha">
								<div class="checkbox">
									<br /> <label> <input type="checkbox"
										name="permanecerConectado"> Permanecer Conectado
									</label> <br />
								</div>

							</div>
						</div>
						<div class="form-group">
							<div class="col-lg-10 col-lg-offset-2">
								<input type="submit" class="btn btn-primary" value="Login">
								<span class="label label-danger">${erro}</span>
							</div>

						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</body>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</html>