package com.planify.main.config.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.planify.main.common.ApiResult;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public Object handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Unexpected Error";

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.resolve(statusCode);
            if (httpStatus != null) {
                errorMessage = httpStatus.getReasonPhrase();
                if (statusCode == HttpStatus.NOT_FOUND.value()) {
                    errorMessage = "Page Not Found";
                } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                    errorMessage = "403 Forbidden";
                }
            } else {
                errorMessage = "Status Code: " + statusCode;
            }
        }

        // AJAX 요청인지 확인
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            // JSON 형태로 응답
            return ResponseEntity
                    .status(httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResult.failure(httpStatus != null ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR, errorMessage));
        } else {
            // 일반 요청은 에러 페이지
            model.addAttribute("errorCode", httpStatus.value());
            model.addAttribute("errorMessage", errorMessage);
            return "error/error.body";
        }
    }
}
