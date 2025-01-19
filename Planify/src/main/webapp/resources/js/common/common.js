/**
 * common.js
 */
$(document).ready(function() {
    
});

if (typeof window.plan === 'undefined') {
    window.plan = {
         util : new Object()
    }
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
                //alert(jqXHR.responseText || 'error');
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
    
    /**
     * form을 Object로 담아 주는 함수(serialize)
     * @param {String} formTargetId ex) form tag Id
     * @returns {Object} object
     */
    function fetchFormData(formTargetId) {
        var formData = new Object();
        var targetForm = $("#" + formTargetId);
        var inputs = targetForm.find('input, select, textarea');

        inputs.each(function() {
            var input = $(this);
            if (input.is('input')) {
                if (input.attr('type') === 'checkbox') {
                    formData[input.attr('name')] = input.prop('checked');
                } else if (input.attr('type') === 'radio') {
                    if (input.prop('checked')) {
                        formData[input.attr('name')] = input.val();
                    }
                } else {
                    formData[input.attr('name')] = input.val();
                }
            } else {
                formData[input.attr('name')] = input.val();
            }
        });

        return formData;
    }

    /**
     * 쿠기 값 추가
     * @param {String} name
     * @param {String} value
     * @param {String} path
     * @param {Date} expiredays
     */
    function addCookie(name, value, path, expiredays) {
        var expiryDate = new Date();
        // 만료일이 지정된 경우, 해당 일수를 더함
        if (expiredays) {
            expiryDate.setDate(expiryDate.getDate() + expiredays);
        }
        // 쿠키 문자열 구성
        var cookieValue = encodeURIComponent(name) + "=" + encodeURIComponent(value) +
            "; path=" + (path ? path : "/") + // 경로 기본값은 '/'
            (expiredays ? "; expires=" + expiryDate.toUTCString() : ""); // 만료일이 있는 경우만 추가
        document.cookie = cookieValue;
    }

    /**
     * 쿠키 제거
     * @param {String} name
     */
    function removeCookie(name) {
        document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    }

    /**
     * 쿠기 값 가져오기
     * @param {String} name
     * @returns
     */
    function getCookie(name) {
        var nameEQ = encodeURIComponent(name) + "=";
        var ca = document.cookie.split(';'); // 쿠키를 ';'로 분리하여 배열 생성
        for(var i=0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') c = c.substring(1,c.length); // 앞 공백 제거
            if (c.indexOf(nameEQ) === 0) return decodeURIComponent(c.substring(nameEQ.length, c.length));
        }
        return null; // 쿠키가 없는 경우 null 반환
    }

    /**
     * HTML 요소에 CSS 클래스를 추가하는 함수
     * ex) plan.util.addClass('#temp', 'hide');
     * @param {String} element
     * @param {String} className
     */
    function addClass(element, className) {
        if ($(element).hasClass(className) === false) {
            $(element).addClass(className);
        }
    }

    /**
     * HTML 요소에 CSS 클래스를 제거하는 함수
     * * ex) plan.util.removeClass('#temp', 'hide');
     * @param {String} element
     * @param {String} className
     */
    function removeClass(element, className) {
        $(element).removeClass(className);
    }

    /**
     * form 초기화
     * @param {String} formId
     */
    function resetForm(formId){

        $('#'+ formId)[0].reset();
    }

    /**
     * 현재 날짜 및 시간을 ISO 형식으로 반환
     * @param {number} 0,1,2,3
     */
    function getToDateTimeISO(offsetHours = 0) {
        const now = new Date();
        now.setHours(now.getHours() + offsetHours); // 시간에 offset 추가
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day}T${hours}:${minutes}`;
    }
    
    /**
     * SweetAlert2 Toast 함수
     * @returns {Object} - Toast 객체
     */
    function getToast() {
        return Swal.mixin({
            toast: true,
            position: 'center',
            iconColor: 'white',
            customClass: {
                popup: 'colored-toast',
            },
            showConfirmButton: false,
            timer: 1500,
            timerProgressBar: true,
        });
    }
    
    function getAuthenticatedUserInfo(){
        let dfd = $.Deferred();
        AJAX_Json('/api/user/info', '', 'GET', 'json').done(function (response) {
            if (response.status === 200) {
                dfd.resolve(response.data);
            } else {
                dfd.reject('Error: ' + response.message);
            }
        })
        .fail(function (jqXHR) {
            const errorMessage = jqXHR.responseJSON ? jqXHR.responseJSON.error : '알 수 없는 오류';
            dfd.reject(errorMessage);
        });
        return dfd.promise();
    }
    
    return {
        isEmpty: isEmpty,
        AJAX_Request: AJAX_Request,
        AJAX_Form: AJAX_Form,
        AJAX_Json: AJAX_Json,
        fetchFormData : fetchFormData,
        getCookie : getCookie,
        removeCookie : removeCookie,
        addCookie : addCookie,
        addClass : addClass,
        removeClass : removeClass,
        resetForm : resetForm,
        getToDateTimeISO : getToDateTimeISO,
        getToast: getToast,
        getAuthenticatedUserInfo : getAuthenticatedUserInfo,
    };
})();
