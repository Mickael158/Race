<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link href="img/logo/logo2.png" rel="icon">
    <title>Home Renovation</title>
    <link href="${pageContext.request.contextPath}/css/Login.css" rel="stylesheet">
</head>

<body id="page-top">
<div class="container">
    <div class="screen">
        <div class="screen__content">
            <form class="login" action="${pageContext.request.contextPath}/Login" method="post">
                <div class="login__field">
                    <i class="login__icon fas fa-user"></i>
                    <input type="text" class="login__input" placeholder="Email" name="email">
                </div>
                <div class="login__field">
                    <i class="login__icon fas fa-user"></i>
                    <input type="text" class="login__input" placeholder="Mot de passe" name="pswd">
                </div>
                <button class="button login__submit">
                    <span class="button__text">Se Connecter</span>
                    <i class="button__icon fas fa-chevron-right"></i>
                </button>
            </form>
        </div>
        <div class="screen__background">
            <span class="screen__background__shape screen__background__shape4"></span>
            <span class="screen__background__shape screen__background__shape3"></span>
            <span class="screen__background__shape screen__background__shape2"></span>
            <span class="screen__background__shape screen__background__shape1"></span>
        </div>
    </div>
</div>
</body>
</html>