package com.secusoft.web.core.aop;

import com.secusoft.web.common.GlobalApiResult;
import com.secusoft.web.common.exception.BizExceptionEnum;
import com.secusoft.web.common.exception.BussinessException;
import com.secusoft.web.common.exception.InvalidKaptchaException;
import com.secusoft.web.core.base.tips.ErrorTip;
import com.secusoft.web.core.log.LogManager;
import com.secusoft.web.core.log.factory.LogTaskFactory;
import com.secusoft.web.core.shiro.ShiroKit;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.UndeclaredThrowableException;

import static com.secusoft.web.core.support.HttpKit.getIp;
import static com.secusoft.web.core.support.HttpKit.getRequest;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 *
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 拦截业务异常
     *
     *
     */
    @ExceptionHandler(BussinessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(BussinessException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        getRequest().setAttribute("tip", e.getMessage());
        log.error("业务异常:", e);
        return new ErrorTip(e.getCode(), e.getMessage());
    }

    /**
     * 用户未登录
     *
     *
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public GlobalApiResult<Object> unAuth(AuthenticationException e) {
        log.error("用户未登陆：", e);
        return GlobalApiResult.failure(401,"用户未登录");
    }

    /**
     * 账号被冻结
     *
     *
     */
    @ExceptionHandler(DisabledAccountException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public GlobalApiResult<Object> accountLocked(DisabledAccountException e, Model model) {
        String username = getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号被冻结", getIp()));
        model.addAttribute("tips", "账号被冻结");
        return GlobalApiResult.failure(419,"账号被冻结");
    }

    /**
     * 账号密码错误
     *
     *
     */
    @ExceptionHandler(CredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public GlobalApiResult<Object> credentials(CredentialsException e, Model model) {
        String username = getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号密码错误", getIp()));
        model.addAttribute("tips", "账号密码错误");
        return GlobalApiResult.failure(400,"账号密码错误");
    }

    /**
     * 验证码错误
     *
     *
     */
    @ExceptionHandler(InvalidKaptchaException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalApiResult<Object> credentials(InvalidKaptchaException e, Model model) {
        String username = getRequest().getParameter("username");
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", getIp()));
        model.addAttribute("tips", "验证码错误");
        return GlobalApiResult.failure(412,"验证码错误");
    }

    /**
     * 无权访问该资源
     *
     *
     */
    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorTip credentials(UndeclaredThrowableException e) {
        getRequest().setAttribute("tip", "权限不足");
        log.error("权限异常!", e);
        return new ErrorTip(BizExceptionEnum.NO_PERMITION.getCode(),BizExceptionEnum.NO_PERMITION.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     *
     *
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorTip notFount(RuntimeException e) {
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser().getId(), e));
        getRequest().setAttribute("tip", "服务器未知运行时异常");
        log.error("运行时异常:", e);
        return new ErrorTip(BizExceptionEnum.SERVER_ERROR.getCode(),BizExceptionEnum.SERVER_ERROR.getMessage());
    }

    /**
     * session失效的异常拦截
     *
     *
     * @Date 2017/6/7 21:02
     */
    @ExceptionHandler(InvalidSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalApiResult<Object> sessionTimeout(InvalidSessionException e, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("tips", "session超时");
        assertAjax(request, response);
        return GlobalApiResult.failure(410,"session超时");
    }

    /**
     * session异常
     *
     *
     * @Date 2017/6/7 21:02
     */
    @ExceptionHandler(UnknownSessionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public GlobalApiResult<Object> sessionTimeout(UnknownSessionException e, Model model, HttpServletRequest request, HttpServletResponse response) {
        model.addAttribute("tips", "session超时");
        assertAjax(request, response);
        return GlobalApiResult.failure(411,"session异常");
    }

    private void assertAjax(HttpServletRequest request, HttpServletResponse response) {
        if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
            //如果是ajax请求响应头会有，x-requested-with
            response.setHeader("sessionstatus", "timeout");//在响应头设置session状态
        }
    }

}
