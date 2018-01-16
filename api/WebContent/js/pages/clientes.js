$(document)
		.ready(
				function() {
					$(document).on("click", "#salvarBt", function() {
						$('#clienteForm').submit();
					});

					$(document).on("click", "#gerarBt", function() {
						$('#relatorioForm').submit();
					});

					$('#clienteModal').on('hidden.bs.modal', function(e) {
						window.location = 'clientes';
					});

					$('#clienteForm').validate({
						rules : {
							nome : {
								minlength : 5,
								required : true
							},
							identificacao : {
								required : true,
								onkeyup : false,
								verificaIdentificacao : true
							},
							email : {
								verificaEmail : true,
								onkeyup : false,
								email : true
							},
						},

						errorClass : "label label-danger",
						highlight : function(element, errorClass, validClass) {
						},
						success : function(label, element) {
							label.parent().removeClass('label label-danger');
							label.remove();
						}
					});

					jQuery.validator.addMethod("verificaEmail", function(value,
							element) {
						var verifica = false;
						$.ajax({
							url : 'clienteEmailValidator?email='
									+ $("#inputEmail").val() + '&id='
									+ $("#inputId").val(),
							async : false,
							success : function(data) {
								if (data == "true")
									verifica = true;
							}
						});

						if (!verifica)
							return false;

						return true;
					}, "Email já utilizado");

					jQuery.validator
							.addMethod(
									"verificaIdentificacao",
									function(value, element) {
										cpf = value.replace(/[^\d]+/g, "");
										if (cpf.length == 11) {
											while (cpf.length < 11)
												cpf = "0" + cpf;
											var expReg = /^0+$|^1+$|^2+$|^3+$|^4+$|^5+$|^6+$|^7+$|^8+$|^9+$/;
											var a = [];
											var b = new Number;
											var c = 11;
											for ( var i = 0; i < 11; i++) {
												a[i] = cpf.charAt(i);
												if (i < 9)
													b += (a[i] * --c);
											}
											if ((x = b % 11) < 2) {
												a[9] = 0;
											} else {
												a[9] = 11 - x;
											}
											b = 0;
											c = 11;
											for ( var y = 0; y < 10; y++)
												b += (a[y] * c--);
											if ((x = b % 11) < 2) {
												a[10] = 0;
											} else {
												a[10] = 11 - x;
											}
											if ((cpf.charAt(9) != a[9])
													|| (cpf.charAt(10) != a[10])
													|| cpf.match(expReg))
												return false;
											var verifica = false;
											$
													.ajax({
														url : 'clienteIdentificacaoValidator?identificacao='
																+ $(
																		"#inputCpfCnpj")
																		.val()
																		.replace(
																				/[^\d]+/g,
																				"")
																+ '&id='
																+ $("#inputId")
																		.val(),
														async : false,
														success : function(data) {
															if (data == "true")
																verifica = true;
														}
													});

											if (!verifica)
												return false;

											return true;
										} else if (cpf.length == 14) {
											cnpj = cpf;

											if (cnpj == '')
												return false;

											if (cnpj.length != 14)
												return false;

											// Elimina CNPJs invalidos
											// conhecidos
											if (cnpj == "00000000000000"
													|| cnpj == "11111111111111"
													|| cnpj == "22222222222222"
													|| cnpj == "33333333333333"
													|| cnpj == "44444444444444"
													|| cnpj == "55555555555555"
													|| cnpj == "66666666666666"
													|| cnpj == "77777777777777"
													|| cnpj == "88888888888888"
													|| cnpj == "99999999999999")
												return false;

											tamanho = cnpj.length - 2;
											numeros = cnpj
													.substring(0, tamanho);
											digitos = cnpj.substring(tamanho);
											soma = 0;
											pos = tamanho - 7;
											for ( var i = tamanho; i >= 1; i--) {
												soma += numeros.charAt(tamanho
														- i)
														* pos--;
												if (pos < 2)
													pos = 9;
											}
											resultado = soma % 11 < 2 ? 0
													: 11 - soma % 11;
											if (resultado != digitos.charAt(0))
												return false;

											tamanho = tamanho + 1;
											numeros = cnpj
													.substring(0, tamanho);
											soma = 0;
											pos = tamanho - 7;
											for ( var i = tamanho; i >= 1; i--) {
												soma += numeros.charAt(tamanho
														- i)
														* pos--;
												if (pos < 2)
													pos = 9;
											}
											resultado = soma % 11 < 2 ? 0
													: 11 - soma % 11;
											if (resultado != digitos.charAt(1))
												return false;

											var verifica = false;
											$
													.ajax({
														url : 'clienteIdentificacaoValidator?identificacao='
																+ $(
																		"#inputCpfCnpj")
																		.val()
																		.replace(
																				/[^\d]+/g,
																				"")
																+ '&id='
																+ $("#inputId")
																		.val(),
														async : false,
														success : function(data) {
															if (data == "true")
																verifica = true;
														}
													});

											if (!verifica)
												return false;

											return true;
										} else
											return false;
									},
									"Identificação Inválida ou já Utilizada!");

					if ($('input[name=tipo]:checked', '#clienteForm').val() == 'cpf') {
						$("#labelCnpj").hide();
						$("#labelCpf").show();
						$("#formRg").show();
						$('#inputCpfCnpj').mask('000.000.000-00', {
							reverse : true
						});
					} else {
						$("#labelCpf").hide();
						$("#formRg").hide();
						$("#labelCnpj").show();
						$('#inputCpfCnpj').mask('00.000.000/0000-00', {
							reverse : true
						});
					}
					$("#clienteForm input:radio[name=tipo]").change(function() {
						if ($(this).val() == "cpf") {
							$("#labelCnpj").hide();
							$("#labelCpf").show();
							$("#formRg").show();
							$('#inputCpfCnpj').mask('000.000.000-00', {
								reverse : true
							});
						} else {
							$("#labelCpf").hide();
							$("#formRg").hide();
							$("#labelCnpj").show();
							$('#inputCpfCnpj').mask('00.000.000/0000-00', {
								reverse : true
							});
						}
					});

					$("#tipoRelatorio").on(
							"change",
							function() {
								$("#relatorioForm").attr("action",
										$("#tipoRelatorio").val());
							});
				});