<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script>
</script>
<!-- Topbar -->
<nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

    <!-- Sidebar Toggle (Topbar) -->
    <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
        <i class="fa fa-bars"></i>
    </button>

    <!-- Topbar Navbar -->
    <ul class="navbar-nav ml-auto">

        <!-- Nav Item - Search Dropdown (Visible Only XS) -->
<!--         <li class="nav-item dropdown no-arrow d-sm-none"> -->
<!--             <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" -->
<!--                 data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> -->
<!--                 <i class="fas fa-search fa-fw"></i> -->
<!--             </a> -->
<!--             Dropdown - Messages -->
<!--             <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" -->
<!--                 aria-labelledby="searchDropdown"> -->
<!--                 <form class="form-inline mr-auto w-100 navbar-search"> -->
<!--                     <div class="input-group"> -->
<!--                         <input type="text" class="form-control bg-light border-0 small" -->
<!--                             placeholder="Search for..." aria-label="Search" -->
<!--                             aria-describedby="basic-addon2"> -->
<!--                         <div class="input-group-append"> -->
<!--                             <button class="btn btn-primary" type="button"> -->
<!--                                 <i class="fas fa-search fa-sm"></i> -->
<!--                             </button> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </form> -->
<!--             </div> -->
<!--         </li> -->

        <!-- Nav Item - User Information -->
        <li class="nav-item dropdown no-arrow">
            <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                <span class="mr-2 text-gray-600 small" id="nameTag"></span>
                <img class="img-profile rounded-circle"
                    src="/resources/template/img/undraw_profile.svg">
            </a>
            <!-- Dropdown - User Information -->
            <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                aria-labelledby="userDropdown">
                <a class="dropdown-item" href="/member/profile">
                    <i class="fas fa-user fa-sm fa-fw mr-2 text-gray-400"></i>
                    마이페이지
                </a>
<!--                 <a class="dropdown-item" href="#"> -->
<!--                     <i class="fas fa-cogs fa-sm fa-fw mr-2 text-gray-400"></i> -->
<!--                     Settings -->
<!--                 </a> -->
                <a id="h2" class="dropdown-item" href="/h2-console" style="display: none">
                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                    H2-Console
                </a>
                <a id="h2" class="dropdown-item" href="/swagger-ui/index.html">
                    <i class="fas fa-list fa-sm fa-fw mr-2 text-gray-400"></i>
                    Swagger-UI
                </a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="/logout" >
                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                    로그아웃
                </a>
            </div>
        </li>

    </ul>

</nav>
<script src="/resources/js/layout/header.js"></script>
<!-- End of Topbar -->