<%@ page import="Model.Equipe" %>
<%@ page import="Model.User" %>
<!DOCTYPE html>
<html lang="en">
<%
  User user = (User) request.getSession().getAttribute("admin");
%>

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">
  <link href="img/logo/logo2.png" rel="icon" size="20px" >
  <title>ULTIMATE TEAM RACE</title>
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
  <link href="css/ruang-admin.min.css" rel="stylesheet">
</head>

<body id="page-top">
<div id="wrapper">
  <!-- Sidebar -->
  <ul class="navbar-nav sidebar sidebar-light accordion" id="accordionSidebar">
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="index.html">
      <div class="sidebar-brand-icon">
        <img src="img/logo/logo.png">
      </div>
      <div class="sidebar-brand-text mx-3">RACE</div>
    </a>
    <hr class="sidebar-divider my-0">
    <li class="nav-item active">
      <a class="nav-link" href="index.html">
        <i class="fas fa-fw fa-tachometer-alt"></i>
        <span>Option</span></a>
    </li>
    <hr class="sidebar-divider">
    <div class="sidebar-heading">
      Voire plus...
    </div>
    <li class="nav-item">
      <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseBootstrap"
         aria-expanded="true" aria-controls="collapseBootstrap">
        <i class="far fa-fw fa-window-maximize"></i>
        <span>Etape</span>
      </a>
      <div id="collapseBootstrap" class="collapse" aria-labelledby="headingBootstrap" data-parent="#accordionSidebar">
        <div class="bg-white py-2 collapse-inner rounded">
          <h6 class="collapse-header">Action</h6>
          <a class="collapse-item" href="${pageContext.request.contextPath}/VoireEtape_a">Affectation temps</a>
        </div>
      </div>
    </li>
    <li class="nav-item">
      <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseForm" aria-expanded="true"
         aria-controls="collapseForm">
        <i class="fab fa-fw fa-wpforms"></i>
        <span>Afficher</span>
      </a>
      <div id="collapseForm" class="collapse" aria-labelledby="headingForm" data-parent="#accordionSidebar">
        <div class="bg-white py-2 collapse-inner rounded">
          <h6 class="collapse-header">Classement général</h6>
          <a class="collapse-item" href="${pageContext.request.contextPath}/ClassementEtape_a">les points pour chaque étape</a>
          <a class="collapse-item" href="${pageContext.request.contextPath}/ClassementEquipe_a">Par Equipe</a>
        </div>
      </div>
    </li>

    <hr class="sidebar-divider">
    <div class="version" id="version-ruangadmin"></div>
  </ul>
  <!-- Sidebar -->
  <div id="content-wrapper" class="d-flex flex-column">
    <div id="content">
      <!-- TopBar -->
      <nav class="navbar navbar-expand navbar-light bg-navbar topbar mb-4 static-top">
        <button id="sidebarToggleTop" class="btn btn-link rounded-circle mr-3">
          <i class="fa fa-bars"></i>
        </button>
        <ul class="navbar-nav ml-auto">

          <div class="topbar-divider d-none d-sm-block"></div>
          <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown"
               aria-haspopup="true" aria-expanded="false">
              <img class="img-profile rounded-circle" src="img/boy.png" style="max-width: 60px">
              <span class="ml-2 d-none d-lg-inline text-white small"><%= user.getNom() %></span>
            </a>
          </li>
        </ul>
      </nav>
      <!-- Topbar -->

      <!-- Footer -->
      <footer class="sticky-footer bg-white" style="text-align: end">
        <div class="container my-auto">
          <div class="copyright text-center my-auto">
            <span>copyright &copy; <script> document.write(new Date().getFullYear()); </script> - developed by
              <b><a href="#" target="_blank">1904</a></b>
            </span>
          </div>
        </div>

        <div class="container my-auto py-2">
          <div class="copyright text-center my-auto">
            <span>copyright &copy; <script> document.write(new Date().getFullYear()); </script> - distributed by
              <b><a href="#" target="_blank">1904</a></b>
            </span>
          </div>
        </div>
      </footer>
      <!-- Footer -->
    </div>
  </div>

  <!-- Scroll to top -->
  <a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
  </a>

  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
  <script src="js/ruang-admin.min.js"></script>
  <script src="vendor/chart.js/Chart.min.js"></script>
  <script src="js/demo/chart-area-demo.js"></script>
</body>

</html>