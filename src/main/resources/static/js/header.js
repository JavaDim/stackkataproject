const headerText = `<header class="navbar navbar-secondary sticky-top bg-secondary p-1 shadow justify-content-center">
		<div class="d-flex p-1" style="width: 75%">
			<img src="/images/logo.svg" style="width: 9%" class="img-fluid mr-1" alt="Responsive image">

		<form class="w-75">
			<input class="form-control" type="text" placeholder="Search">
		</form>

		<div>
			<a href="/login" class="btn btn-success ml-2 mr-1 p-1">Войти</a>
			<a href="/registration" class="btn btn-primary p-1">Регистрация</a>
		</div>

		</div>
	</header>`;

var header = document.createElement("div");
header.innerHTML = headerText;
document.body.insertAdjacentElement('afterbegin', header);