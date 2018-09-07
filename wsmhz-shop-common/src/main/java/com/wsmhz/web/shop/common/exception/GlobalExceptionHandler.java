package com.wsmhz.web.shop.common.exception;
import com.wsmhz.common.data.response.ResponseCode;
import com.wsmhz.common.data.response.ServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * create by tangbj on 2018/5/19
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String msg = "非法参数或请求方式不匹配";
        logger.error(msg);
        logger.error(ex.getMessage(),ex);
        return new ResponseEntity<Object>(ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(),msg),HttpStatus.NOT_EXTENDED);

    }

    /**
     * 执行逻辑代码时出的全局异常
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ServerResponse<String> globalJsonHandler(HttpServletRequest request, Exception e) throws Exception {

        log(e, request);

        return ServerResponse.createByErrorMessage("服务器内部异常");
    }

    /**
     * 执行业务逻辑代码时出的异常
     */
    @ExceptionHandler(value = BussinessException.class)
    @ResponseBody
    public ServerResponse<String> bussinessJsonHandler(HttpServletRequest request, Exception e) throws Exception {

        log(e, request);

        return ServerResponse.createByErrorMessage(e.getMessage());
    }

    private void log(Exception ex, HttpServletRequest request) {
        logger.error("************************异常开始*******************************");
        logger.error("Exception URI：{}",request.getRequestURI(),ex);
        logger.error("************************异常结束*******************************");
    }
}
