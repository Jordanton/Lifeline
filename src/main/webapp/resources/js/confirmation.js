// Confirmation page
function enableSubmit() {
	$('#submit-button').prop('disabled', false);
	$('#submit-button').removeClass('btn-danger');
	$('#submit-button').removeClass('cursor-not-allowed');
	$('#submit-button').addClass('btn-primary');
}

function disableSubmit() {
	$('#submit-button').prop('disabled', true);
	$('#submit-button').addClass('btn-danger');
	$('#submit-button').addClass('cursor-not-allowed');
	$('#submit-button').removeClass('btn-primary');
}