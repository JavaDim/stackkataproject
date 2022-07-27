const sidebarMenu = `<nav id="sidebarMenu" class="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
			<div class="position-sticky pt-3">
			  <ul class="nav flex-column">
				<li class="nav-item">
				  <a class="nav-link active" aria-current="page" href="#">
					<span data-feather="home"></span>
					Вопросы
				  </a>
				</li>
				<li class="nav-item">
				  <a class="nav-link" href="#">
					<span data-feather="file"></span>
					Метки
				  </a>
				</li>
				<li class="nav-item">
				  <a class="nav-link" href="#">
					<span data-feather="shopping-cart"></span>
					Участники
				  </a>
				</li>
			  </ul>
			</div>
		  </nav>`;
document.getElementById("sidebar").innerHTML = sidebarMenu;