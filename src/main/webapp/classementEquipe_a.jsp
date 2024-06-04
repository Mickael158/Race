<%@ page import="Model.Equipe" %>
<%@ page import="Model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="Model.Classement" %>
<%@ page import="Model.Categorie" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html lang="en">
<%
    User user = (User) request.getSession().getAttribute("admin");
    List<Equipe> equipes = (List<Equipe>) request.getAttribute("equipes");
    List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
    List<Classement> classementList = (List<Classement>) request.getAttribute("classements");
    Categorie cat = (Categorie) request.getAttribute("cat");
    List<String> equipe = new ArrayList<>();
    List<String> pt_equipe = new ArrayList<>();
    for (Classement classement : classementList){
        equipe.add(classement.getNomEquipe());
        pt_equipe.add(String.valueOf(classement.getPoint()));
    }
    String Data_equipe = "[" + Arrays.stream(equipe.toArray()).map(s -> "'" + s + "'").collect(Collectors.joining(", ")) + "]";
    String Data_pt_equipe = "[" + String.join(", ", pt_equipe) + "]";
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
    <link rel="stylesheet" href="css/certificate.css">
    <script src="js/Chart.js"></script>
    <script type="text/javascript" src="js/html2pdf.bundle.min.js"></script>
    <script type="text/javascript" src="js/loader.js"></script>
    <link href="css/ruang-admin.min.css" rel="stylesheet">
    <script src="js/stat.js"></script>
    <style>
        #chart {
            max-width: 300px;
            margin: 0 auto;
        }
    </style>
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
                    <a class="collapse-item" href="${pageContext.request.contextPath}/Penalite">Penalite Equipe</a>
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
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseForm1" aria-expanded="true"
               aria-controls="collapseForm">
                <i class="fab fa-fw fa-wpforms"></i>
                <span>Donnees</span>
            </a>
            <div id="collapseForm1" class="collapse" aria-labelledby="headingForm" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Import</h6>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/Import_etape_resultat">Etape / resultat</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/Import_Point">Point</a>
                    <a class="collapse-item" href="${pageContext.request.contextPath}/Reinitialisation">Reinitialisation</a>
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
            <%if(classementList.size() != 0 && cat != null){%>
            <div hidden>
                <div class="champion" >
                    <div id="chart_div">
                        <div class="certificate">
                            <div class="header_certificate">
                                <h1>RUNNING</h1>
                                <h2>CHAMPOIN</h2>
                            </div>
                            <div class="medal_certificate">
                                <img src="img/champion.jpg" alt="Medal" >
                            </div>
                            <div class="medal_certificate1">
                                <img src="img/champion.jpg" alt="Medal" >
                            </div>
                            <div class="content_certificate">
                                <p>This certificate is presented to</p>
                                <h3><%= classementList.get(0).getNomEquipe()%></h3>
                                <% if (cat.getIdCategorie() != 0){%>
                                <p>Equipe champion dans le categorie <%= cat.getNom_Categorie()%></p>
                                <%}else {%>
                                <p>Equipe champion dans le categorie Tous</p>
                                <%}%>
                            </div>
                            <div class="footer_certificate">
                                <div class="date">
                                    <p>Date</p>
                                </div>
                                <div class="stat">
                                    <p>Distance : <%= classementList.get(0).getLongueur()%></p>
                                    <p>Temps : <%= classementList.get(0).getDiffTemps()%></p>
                                    <p>Point : <%= classementList.get(0).getPoint()%> </p>
                                </div>
                                <div class="signature">
                                    <p>Signature</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%}%>
            <div class="container-fluid" id="container-wrapper">
                <div class="col-lg-12">
                    <center>
                        <div class="card-body" >
                            <%if (equipes.size() != 0 && categories.size() != 0){%>
                            <form action="${pageContext.request.contextPath}/ClassementEquipe_a" method="post">
                                <div class="form-group">
                                    <label >Classement par etape</label>
                                    <div class="row row-cols-1 row-cols-md-2">
                                        <div class="col" style="width: 30%">
                                            <p>Categories : </p>
                                            <select class="form-control" style="text-align: center" name="idCategorie">
                                                <option class="form-control" style="text-align: center" value="0">Tous</option>
                                                <%for (Categorie categorie : categories){%>
                                                <option class="form-control" style="text-align: center" value="<%= categorie.getIdCategorie()%>"><%=categorie.getNom_Categorie()%></option>
                                                <%}%>
                                            </select>
                                        </div>
                                    </div>
                                    <button type="submit" class="btn btn-primary" >Voire</button>
                                </div>
                            </form>
                        </br> </br>
                            <button type="submit" class="btn btn-primary" onclick="addPdf('chart_div')">Certificate</button>
                            <%}%>
                        </div>
                    </center>
                </div>
            </div>
            <%if (classementList.size() != 0){%>
            <div id="chart">
                <canvas id="Camembert"></canvas>
            </div>
            <script>
                var ctx = document.getElementById('Camembert').getContext('2d');
                var myPieChart = new Chart(ctx, {
                    type: 'pie',
                    data: {
                        labels: <%=Data_equipe%>,
                        datasets: [{
                            label: 'Graphe De repartition des point',
                            data: <%=Data_pt_equipe%>,
                            hoverOffset: 4
                        }]
                    },
                    options: {
                        responsive: true,
                        plugins: {
                            legend: {
                                position: 'top',
                            },
                            tooltip: {
                                callbacks: {
                                    label: function(context) {
                                        let label = context.label || '';
                                        if (label) {
                                            label += ': ';
                                        }
                                        if (context.parsed !== null) {
                                            label += context.parsed;
                                        }
                                        return label;
                                    }
                                }
                            }
                        }
                    }
                });
            </script>
            <div class="table-responsive p-3">
                <label >Classement etape a <%= classementList.get(0).getLieu()%></label>
                <table class="table align-items-center table-flush" id="dataTable">
                    <thead class="thead-light">
                    <tr>
                        <th>Equipe</th>
                        <th>Penalite</th>
                        <th>Point total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%for (Classement classement : classementList){%>
                    <tr>
                        <th><%= classement.getNomEquipe() %></th>
                        <th><%= classement.getPenalite() %></th>
                        <th><%= classement.getPoint() %></th>
                    </tr>
                    <%}%>
                    </tbody>
                </table>
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
    <script>
        function addPdf(id) {
            var element = document.getElementById(id);
            element.style.padding = '20px';
            element.style.fontSize = "small";
            html2pdf(element);
        }
    </script>
</body>

</html>