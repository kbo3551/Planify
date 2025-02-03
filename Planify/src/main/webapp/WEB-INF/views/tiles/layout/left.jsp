<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- Sidebar -->
<ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion toggled" id="accordionSidebar">

    <!-- Sidebar - Brand -->
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="/main">
        <div class="sidebar-brand-icon rotate-n-15">
            <i class="fas fa-laugh-wink"></i>
        </div>
        <div class="sidebar-brand-text mx-3">Planify</div>
    </a>

    <!-- Divider -->
    <hr class="sidebar-divider my-0">

    <!-- Nav Item - Dashboard -->
    <li class="nav-item " data-url="/main">
        <a class="nav-link" href="/main">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>메인</span>
        </a>
    </li>

    <!-- Divider -->
    <hr class="sidebar-divider">

<!--     Nav Item - Tables -->
    <li class="nav-item" data-url="/notice/list">
        <a class="nav-link" href="/notice/list">
            <i class="fas fa-fw fa-table"></i>
            <span>공지사항</span></a>
    </li>

    <li class="nav-item" data-url="/chat/topic">
        <a class="nav-link" href="/chat/topic">
            <i class="fas fa-fw fa-comments"></i>
            <span>실시간 채팅</span></a>
    </li>

        <!-- Nav Item - Pages Collapse Menu -->
   <li class="nav-item" id="roleArea" ></li>

    <!-- Divider -->
<!--     <hr class="sidebar-divider d-none d-md-block"> -->

    <!-- Sidebar Toggler (Sidebar) -->
    <div class="text-center d-none d-md-inline">
        <button class="rounded-circle border-0" id="sidebarToggle"></button>
    </div>

</ul>
<script src="/resources/js/layout/left.js"></script>
<!-- End of Sidebar -->