$(document).ready(
		function() {

			$(document).on("click", "#salvarBt", function() {
				$('#funcionarioForm').submit();
			});

			$('#funcionarioModal').on('hidden.bs.modal', function(e) {
				window.location = 'funcionarios';
			});

			$('#funcionarioForm').validate({
				rules : {
					nome : {
						minlength : 5,
						required : true
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
				},
			});

			jQuery.validator.addMethod("verificaEmail",
					function(value, element) {
						var verifica = false;
						$.ajax({
							url : "funcionarioEmailValidator?email="
									+ $("#inputEmail").val() + '&id='
									+ $('#inputId').val(),
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
		});