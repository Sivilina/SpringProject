<%@ page import="com.Alina.spring.market.entities.User" %>
<%@ page import="com.Alina.spring.market.entities.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.Alina.spring.market.entities.TransactionHistory" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- SITE TITTLE -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Casher List</title>
    <!-- PLUGINS CSS STYLE -->
    <link href="resources/plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="resources/plugins/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="resources/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- Owl Carousel -->
    <link href="resources/plugins/slick-carousel/slick/slick.css" rel="stylesheet">
    <link href="resources/plugins/slick-carousel/slick/slick-theme.css" rel="stylesheet">
    <!-- Fancy Box -->
    <link href="resources/plugins/fancybox/jquery.fancybox.pack.css" rel="stylesheet">
    <link href="resources/plugins/jquery-nice-select/css/nice-select.css" rel="stylesheet">
    <link href="resources/plugins/seiyria-bootstrap-slider/dist/css/bootstrap-slider.min.css" rel="stylesheet">
    <!-- CUSTOM CSS -->
    <link href="resources/css/style.css" rel="stylesheet">

    <!-- FAVICON -->
    <link href="resources/image/favicon.png" rel="shortcut icon">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body class="body-wrapper">
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <nav class="navbar navbar-expand-lg  navigation">
                    <a class="navbar-brand" href="index.html">
                        <img src="resources/images/logo.png" alt="">
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <% User user =  (User)session.getAttribute("user"); %>
                        <ul class="navbar-nav ml-auto main-nav ">
                            <li class="nav-item active">
                                <a class="nav-link" href="/home">Home</a>
                            </li>
                        </ul>
                        <ul class="navbar-nav ml-auto mt-10">
                            <% if (user == null) {%>
                            <li class="nav-item">
                                <a class="nav-link login-button" href="/login">Login</a>
                            </li>
                            <%} else { %>
                            <li>
                                <a class="nav-item">
                                    <a class="nav-link login-button" href="/logout">Logout</a>
                                </a>
                            </li>
                            <% }%>
                        </ul>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</section>
<% ArrayList<TransactionHistory> transactionHistories = (ArrayList<TransactionHistory>) request.getAttribute("transList");
    ArrayList<Item> itemList = (ArrayList<Item>) request.getAttribute("itemsList");
    ArrayList<User> cashers = (ArrayList<User>) request.getAttribute("casherList");
%>
<section class="dashboard section">
    <div class="container">
        <div class="row">
            <div class="col-md-10 offset-md-1 col-lg-4 offset-lg-0">
                <div class="sidebar">
                    <div class="widget user-dashboard-profile">
                        <div class="profile-thumb">
                            <img src="resources/images/admin.jpg" alt="" class="rounded-circle">
                        </div>
                        <h5 class="text-center"><% out.print(user.getName() +" "+ user.getSurname());%></h5>
                        <p><% out.print(user.getLogin());%></p>
                        <a href="/editProfile" class="btn btn-main-sm">Edit Profile</a>
                    </div>
                    <div class="widget user-dashboard-menu">
                        <ul>
                            <li >
                                <a href="/home"><i class="fa fa-bookmark-o"></i> Items <span> <% if(itemList != null) out.print(itemList.size()); %></span></a></li>
                            <li>
                                <a href="/transactionHistory"><i class="fa fa-file-archive-o"></i> Transaction History <span> <% if(transactionHistories != null) out.print(transactionHistories.size()); %></span></a>
                            </li>
                            <li class="active">
                                <a href="/casherList"><i class="fa fa-bolt"></i> Cashers<span> <% if (cashers != null) out.print(cashers.size()); %></span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="col-md-10 offset-md-1 col-lg-8 offset-lg-0">
                <div class="widget dashboard-container my-adslist">
                    <h3 class="widget-header"> Cashers List </h3>
                    <a class="edit" title="Add Casher" href="/addCasher">
                        <i class="fa fa-plus-circle" ></i>
                    </a>
                    <table class="table table-responsive product-dashboard-table">

                        <thead>
                        <tr>
                            <th>Image</th>
                            <th class="text-center">Casher name</th>
                            <th class="text-center">Casher surname</th>
                            <th class="text-center"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <% if(cashers != null) {for(User casher : cashers){%>
                        <tr>
                            <td class="product-thumb">
                                <img width="80px" height="auto" src="resources/images/user/user-thumb.jpg" alt="image description"></td>
                            <td class="product-category"><span class="categories"><% out.print(casher.getName()); %></span></td>
                            <td class="product-category"><span class="categories"><% out.print(casher.getSurname()); %></span></td>
                            <td class="action" data-title="Action">
                                <div class="">
                                    <ul class="list-inline justify-content-center">
                                        <li class="list-inline-item">
                                            <a class="edit" href="/editCasher?id= <% out.print(casher.getId() + "\">"); %>
                                                <i class="fa fa-pencil"></i>
                                            </a>
                                        </li>
                                        <li class="list-inline-item">
                                            <a class="delete" href="/deleteCasher?id= <% out.print(casher.getId() + "\">"); %>
                                                <i class="fa fa-trash"></i>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                        <% }}%>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</section>
<!--============================
=            Footer            =
=============================-->

<footer class="footer section section-sm">
    <!-- Container Start -->
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-7 offset-md-1 offset-lg-0">
                <!-- About -->
                <div class="block about">
                    <!-- footer logo -->
                    <img src="resources/images/logo-footer.png" alt="">
                    <!-- description -->
                    <p class="alt-color">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
                </div>
            </div>
            <!-- Promotion -->
            <div class="col-lg-4 col-md-7">
                <!-- App promotion -->
                <div class="block-2 app-promotion">
                    <a href="">
                        <!-- Icon -->
                        <img src="resources/images/footer/phone-icon.png" alt="mobile-icon">
                    </a>
                    <p>Get the Dealsy Mobile App and Save more</p>
                </div>
            </div>
        </div>
    </div>
    <!-- Container End -->
</footer>
<!-- Footer Bottom -->
<footer class="footer-bottom">
    <!-- Container Start -->
    <div class="container">
        <div class="row">
            <div class="col-sm-6 col-12">
                <!-- Copyright -->
                <div class="copyright">
                    <p>Copyright © 2016. All Rights Reserved</p>
                </div>
            </div>
            <div class="col-sm-6 col-12">
                <!-- Social Icons -->
                <ul class="social-media-icons text-right">
                    <li><a class="fa fa-facebook" href=""></a></li>
                    <li><a class="fa fa-twitter" href=""></a></li>
                    <li><a class="fa fa-pinterest-p" href=""></a></li>
                    <li><a class="fa fa-vimeo" href=""></a></li>
                </ul>
            </div>
        </div>
    </div>
    <!-- Container End -->
    <!-- To Top -->
    <div class="top-to">
        <a id="top" class="" href=""><i class="fa fa-angle-up"></i></a>
    </div>
</footer>

<!-- JAVASCRIPTS -->
<script src="resources/plugins/jquery/jquery.min.js"></script>
<script src="resources/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="resources/plugins/tether/js/tether.min.js"></script>
<script src="resources/plugins/raty/jquery.raty-fa.js"></script>
<script src="resources/plugins/bootstrap/dist/js/popper.min.js"></script>
<script src="resources/plugins/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="resources/plugins/seiyria-bootstrap-slider/dist/bootstrap-slider.min.js"></script>
<script src="resources/plugins/slick-carousel/slick/slick.min.js"></script>
<script src="resources/plugins/jquery-nice-select/js/jquery.nice-select.min.js"></script>
<script src="resources/plugins/fancybox/jquery.fancybox.pack.js"></script>
<script src="resources/plugins/smoothscroll/SmoothScroll.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCC72vZw-6tGqFyRhhg5CkF2fqfILn2Tsw"></script>
<script src="resources/js/scripts.js"></script>

</body>

</html>

