/**
 * main.js
 */
$(document).ready(function() {
    // plan.util.init();
});

if (typeof window.plan === 'undefined') {
    window.plan = new Object();
}

window.plan.util = (function() {
    /**
     * AJAX 호출 공통 함수
     * @param {String} url - 통신할 URL
     * @param {Object|FormData} data - 전달할 파라미터
     * @param {String} method - 통신할 HTTP 메서드 (기본값: 'POST')
     * @param {String} responseType - 응답 데이터 타입 (기본값: 'json')
     * @param {Function} callback - 콜백 함수 (선택)
     * @param {boolean} async - 비동기 여부 (기본값: true)
     * @param {boolean} isForm - FormData 사용 여부 (기본값: false)
     * @returns {Promise} - AJAX 호출 결과 Promise
     */
    function AJAX_Request({ url, data = {}, method = 'POST', responseType = 'json', callback, async = true, isForm = false }) {
        var dfd = $.Deferred();
    
        if (!url) {
            console.error('요청 URL 확인해주세요.');
            return dfd.reject('요청 URL 확인해주세요.').promise();
        }
    
        var ajaxOptions = {
            type: method,
            url: url,
            dataType: responseType,
            async: async,
            success: function(response) {
                console.log("url :", url);
                if (typeof callback === 'function') {
                    callback(response);
                }
                dfd.resolve(response);
            },
            error: function(jqXHR) {
                console.error('Error:', url, jqXHR);
                alert(jqXHR.responseText || 'error');
                dfd.reject(jqXHR);
            }
        };
    
        if (isForm) {
            ajaxOptions.data = data instanceof FormData ? data : new FormData($(data)[0]);
            ajaxOptions.processData = false;
            ajaxOptions.contentType = false;
        } else {
            ajaxOptions.data = JSON.stringify(data);
            ajaxOptions.contentType = 'application/json; charset=utf-8';
        }
    
        $.ajax(ajaxOptions);
        return dfd.promise();
    }
    
    /**
     * FormData 기반 AJAX 호출 함수
     * @param {String} url - 통신할 URL
     * @param {Form} formParams - 전달할 Form 객체
     * @param {String} method - 통신할 HTTP 메서드
     * @param {String} responseType - 응답 데이터 타입
     * @param {Function} callback - 콜백 함수 (선택)
     * @param {boolean} async - 비동기 여부
     * @returns {Promise} - AJAX 호출 결과 Promise
     */
    function AJAX_Form(url, formParams, method, responseType, callback, async) {
        return AJAX_Request({ url, data: formParams, method, responseType, callback, async, isForm: true });
    }
    
    /**
     * JSON 객체 기반 AJAX 호출 함수
     * @param {String} url - 통신할 URL
     * @param {Object} params - 전달할 JSON 객체
     * @param {String} method - 통신할 HTTP 메서드
     * @param {String} responseType - 응답 데이터 타입
     * @param {Function} callback - 콜백 함수 (선택)
     * @param {boolean} async - 비동기 여부
     * @returns {Promise} - AJAX 호출 결과 Promise
     */
    function AJAX_Json(url, params, method, responseType, callback, async) {
        return AJAX_Request({ url, data: params, method, responseType, callback, async });
    }
    
    /**
     * 전달 받은 인자 빈값 체크
     * @param {Object} params
     * @returns boolean ex) 비어있을 경우 true, 존재할 경우 false
     */
    function isEmpty(params) {
        if (params == '' || params == null || params == undefined || params == 'null' || (params != null && typeof params == 'object' && !Object.keys(params).length)) {
            return true;
        } else {
            return false;
        }
    }
  
    return {
        isEmpty: isEmpty,
        AJAX_Request: AJAX_Request,
        AJAX_Form: AJAX_Form,
        AJAX_Json: AJAX_Json
    };
})();
