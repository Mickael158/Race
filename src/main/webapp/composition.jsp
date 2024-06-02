<%@ page import="Model.Equipe" %>
<%@ page import="Model.Etape" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Coureur" %>
<!DOCTYPE html>
<html lang="en">
<%
    Equipe equipe = (Equipe) request.getSession().getAttribute("equipe");
    Etape etape = (Etape) request.getAttribute("etape");
    List<Coureur> coureurList_non_composer = (List<Coureur>) request.getAttribute("coureurList_Non_Composer");
    List<Coureur> coureurList_composer = (List<Coureur>) request.getAttribute("coureurList_Composer");
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
                    <a class="collapse-item" href="${pageContext.request.contextPath}/VoireEtape_e">Affectation coureur</a>
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
                    <a class="collapse-item" href="${pageContext.request.contextPath}/ClassementEtape_e">les points pour chaque étape</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/ClassementEquipe_e"><%= equipe.getNomEquipe() %></a>
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
                            <span class="ml-2 d-none d-lg-inline text-white small"><%= equipe.getNomEquipe() %></span>
                        </a>
                    </li>
                </ul>
            </nav>
            <!-- Topbar -->
            <div class="container-fluid" id="container-wrapper">
                <div class="col-lg-12">
                    <center>
                        <div class="card-body" >
                            <%if (etape.getIdEtape() != 0){%>
                                <div class="form-group">
                                    <label >A prpos</label>
                                    <div class="row row-cols-1 row-cols-md-2">
                                        <div class="col" style="width: 30%">
                                            <p>Lieu de l'Etape : </p><input class="form-control" type="text" id="name-5" style="text-align: center" value="<%= etape.getLieu() %>" disabled>
                                        </div>
                                        <div class="col" style="width: 30%">
                                            <p>Nombre de joueur paticipant : </p><input class="form-control" type="text" id="name-4" name="typeMaison" style="text-align: center" value="<%= coureurList_composer.size() %> / <%= etape.getNbr_Coureur_Equipe() %>" disabled>
                                        </div>
                                    </div>
                                </div>
                            <%}%>
                        </div>
                    </center>
                </div>
            </div>
            <%if (coureurList_composer.size() != 0){%>
            <div class="table-responsive p-3">
                <label >Coureur Admie</label>
                    <table class="table align-items-center table-flush" id="dataTable">
                        <thead class="thead-light">
                        <tr>
                            <th>Numero</th>
                            <th>Nom</th>
                            <th>date de naissance</th>
                            <th>Genre</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%for (Coureur coureur : coureurList_composer){%>
                        <tr>
                            <th><%= coureur.getNumero() %></th>
                            <th><%= coureur.getNom() %></th>
                            <th><%= coureur.getDtn() %> km</th>
                            <th><%= coureur.getNomGenre() %></th>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
            </div>
            <%}%>
            <%if (coureurList_non_composer.size() != 0){%>
            <div class="table-responsive p-3">
                <label >Coureur pas encore Admie</label>
                <form action="${pageContext.request.contextPath}/AddCoureur" method="post">
                    <table class="table align-items-center table-flush" id="dataTable">
                        <thead class="thead-light">
                        <tr>
                            <th>Numero</th>
                            <th>Nom</th>
                            <th>date de naissance</th>
                            <th>Genre</th>
                            <th>Ajouter</th>
                        </tr>
                        </thead>
                        <tbody>
                        <%for (Coureur coureur : coureurList_non_composer){%>
                        <tr>
                            <th><%= coureur.getNumero() %></th>
                            <th><%= coureur.getNom() %></th>
                            <th><%= coureur.getDtn() %> </th>
                            <th><%= coureur.getNomGenre() %></th>
                            <th>
                                <input type="hidden" value="" name="idCoureur">
                                <input type="hidden" value="<%= equipe.getIdEquipe()%>" name="idEquipe">
                                <input type="hidden" value="<%= etape.getIdEtape()%>" name="idEtape">
                                <input type="checkbox" class="form-control" value="<%=coureur.getIdCoureur()%>" name="idCoureur[]">
                            </th>
                        </tr>
                        <%}%>
                        </tbody>
                    </table>
                    <button type="submit" class="btn btn-primary" style="text-align: center">Add</button>
                </form>
            </div>
            <%}%>
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