<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body class="bg-gradient-primary">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-xl-10 col-lg-12 col-md-9">
                <div class="card o-hidden border-0 shadow-lg my-5">
                    <div class="card-body p-0">
                        <!-- Nested Row within Card Body -->
                        <div class="row">
                            <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                            <div class="col-lg-6">
                                <div class="p-5">
                                    <div class="text-center">
                                        <h1 class="h4 text-gray-900 mb-4"><strong>Planify</strong> 로그인</h1>
                                    </div>
                                    <form class="user" id="loginForm">
                                        <div class="form-group">
                                            <input type="text" class="form-control form-control-user" id="memberId" placeholder="Enter ID" value="test">
                                        </div>
                                        <div class="form-group">
                                            <input type="password" class="form-control form-control-user" id="password" placeholder="Password" value="1234">
                                        </div>
<!--                                         <div class="form-group"> -->
<!--                                             <div class="custom-control custom-checkbox small"> -->
<!--                                                 <input type="checkbox" class="custom-control-input" id="customCheck"> -->
<!--                                                 <label class="custom-control-label" for="customCheck">Remember Me</label> -->
<!--                                             </div> -->
<!--                                         </div> -->
                                        <button type="submit" class="btn btn-primary btn-user btn-block">
                                            로그인
                                        </button>
                                        <hr>
<%--                                         <a href="index.html" class="btn btn-google btn-user btn-block">--%>
<%--                                             <i class="fab fa-google fa-fw"></i> Login with Google--%>
<%--                                         </a>--%>
<%--                                        <div class="text-center">--%>
                                            <a href="/oauth2/authorization/kakao" class="btn-user btn-block" style="display: flex;justify-content: center;align-items: center;" >
                                                <img src="/resources/image/kakao_login_medium_wide.png" alt="Kakao Logo" class="img-fluid" style="width: 120%;max-width: 100%; height: auto; border-radius: 10rem;min-width: 250px;">
                                            </a>
<%--                                        </div>--%>

                                    </form>
                                    <hr>
<!--                                     <div class="text-center"> -->
<!--                                         <a class="small" href="forgot-password.html">Forgot Password?</a> -->
<!--                                     </div> -->
                                    <div class="text-center">
                                        <a class="small" href="/register">회원가입</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script src="/resources/js/login/login.js"></script>
</body>
