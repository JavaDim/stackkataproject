const footerText = `<footer class="navbar fixed-bottom navbar-dark bg-secondary">
		<p class="col-md-4 mb-0 text-white">&copy; 2022 Kata.Academy, Inc</p>

		<ul class="nav col-md-4 justify-content-end">
      		<li class="nav-item"><a href="#" class="nav-link px-2 text-white">Home</a></li>
      		<li class="nav-item"><a href="#" class="nav-link px-2 text-white">Features</a></li>
      		<li class="nav-item"><a href="#" class="nav-link px-2 text-white">Pricing</a></li>
      		<li class="nav-item"><a href="#" class="nav-link px-2 text-white">FAQs</a></li>
      		<li class="nav-item"><a href="#" class="nav-link px-2 text-white">About</a></li>
    	</ul>
	</footer>`;

var footer = document.createElement("div");
footer.innerHTML = footerText;
document.body.insertAdjacentElement('beforeend', footer);
