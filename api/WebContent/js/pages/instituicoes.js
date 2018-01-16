$(document).ready(
		function() {

			$('#inputCnpj').mask('00.000.000/0000-00', {
				reverse : true
			});

			$(document).on("click", "#salvarBt", function() {
				$('#instituicaoForm').submit();
			});

			$('#instituicaoModal').on('hidden.bs.modal', function(e) {
				window.location = 'instituicoes';
			});

			$('#instituicaoForm').validate({
				rules : {
					nome : {
						minlength : 2,
						required : true
					},
					cnpj : {
						verificaCnpj : true,
						onkeyup : false,
					},
				},

				errorClass : "label label-danger",
				highlight : function(element, errorClass, validClass) {
				},
				success : function(label, element) {
					label.parent().removeClass('label label-danger');
					label.remove();
				},
			});

			jQuery.validator.addMethod("verificaCnpj",
					function(value, element) {
						cnpj = value.replace(/[^\d]+/g, "");

						if (cnpj == '')
							return false;

						if (cnpj.length != 14)
							return false;

						// Elimina CNPJs invalidos conhecidos
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
						numeros = cnpj.substring(0, tamanho);
						digitos = cnpj.substring(tamanho);
						soma = 0;
						pos = tamanho - 7;
						for ( var i = tamanho; i >= 1; i--) {
							soma += numeros.charAt(tamanho - i) * pos--;
							if (pos < 2)
								pos = 9;
						}
						resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
						if (resultado != digitos.charAt(0))
							return false;

						tamanho = tamanho + 1;
						numeros = cnpj.substring(0, tamanho);
						soma = 0;
						pos = tamanho - 7;
						for ( var i = tamanho; i >= 1; i--) {
							soma += numeros.charAt(tamanho - i) * pos--;
							if (pos < 2)
								pos = 9;
						}
						resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
						if (resultado != digitos.charAt(1))
							return false;

						var verifica = false;
						$.ajax({
							url : "instituicaoCnpjValidator?cnpj="
									+ $("#inputCnpj").val() + "&id="
									+ $('#inputId'),
							async : false,
							success : function(data) {
								if (data == "true")
									verifica = true;
							}
						});

						if (!verifica)
							return false;

						return true;
					}, "CNPJ inválido ou já utilizado");
		});