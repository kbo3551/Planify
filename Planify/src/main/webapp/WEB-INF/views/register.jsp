<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<body class="bg-gradient-primary">

    <div class="container">

        <div class="card o-hidden border-0 shadow-lg my-5">
            <div class="card-body p-0">
                <!-- Nested Row within Card Body -->
                <div class="row">
                    <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
                    <div class="col-lg-7">
                        <div class="p-5">
                            <div class="text-center">
                                <h1 class="h4 text-gray-900 mb-4">회원가입</h1>
                            </div>
                            <form class="user" id="registerForm">
<!--                                 <div class="form-group row"> -->
<!--                                     <div class="col-sm-6 mb-3 mb-sm-0"> -->
<!--                                         <input type="text" class="form-control form-control-user" id="exampleFirstName" -->
<!--                                             placeholder="First Name"> -->
<!--                                     </div> -->
<!--                                     <div class="col-sm-6"> -->
<!--                                         <input type="text" class="form-control form-control-user" id="exampleLastName" -->
<!--                                             placeholder="Last Name"> -->
<!--                                     </div> -->
<!--                                 </div> -->
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" id="memberId" name="memberId" placeholder="ID를 입력해주세요." required>
                                </div>
                                <div class="form-group">
<!--                                 <div class="form-group row"> -->
<!--                                     <div class="col-sm-6 mb-3 mb-sm-0"> -->
                                        <input type="password" class="form-control form-control-user" id="password" name="password" placeholder="비밀번호를 입력해주세요" required>
<!--                                     </div> -->
                                </div>
<!--                                     <div class="col-sm-6"> -->
<!--                                         <input type="password" class="form-control form-control-user" -->
<!--                                             id="exampleRepeatPassword" placeholder="확ㅇ비밀번호를 입력해주세요."> -->
<!--                                     </div> -->
<!--                                 </div> -->
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" id="name" name="name" placeholder="이름을 입력해 주세요." required>
                                </div>
                                <div class="form-group">
                                    <input type="text" class="form-control form-control-user" id="nickName" name="nickName" placeholder="닉네임을 입력해 주세요." required>
                                </div>
                                <div class="form-group">
                                    <select name="gender" id="gender" class="custom-select form-control" required>
							            <option value="MALE">남자</option>
                                        <option value="FEMALE">여자</option>
							        </select>
                                </div>
                                <button class="btn btn-primary btn-user btn-block" type="submit">회원가입</button>
<!--                                 <hr> -->
<!--                                 <a href="index.html" class="btn btn-google btn-user btn-block"> -->
<!--                                     <i class="fab fa-google fa-fw"></i> Register with Google -->
<!--                                 </a> -->
<!--                                 <a href="index.html" class="btn btn-facebook btn-user btn-block"> -->
<!--                                     <i class="fab fa-facebook-f fa-fw"></i> Register with Facebook -->
<!--                                 </a> -->
                            </form>
                            <hr>
<!--                             <div class="text-center"> -->
<!--                                 <a class="small" href="forgot-password.html">Forgot Password?</a> -->
<!--                             </div> -->
                            <div class="text-center">
                                <a class="small" href="/login">로그인 페이지</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
<script src="/resources/js/register/register.js"></script>
</body>
