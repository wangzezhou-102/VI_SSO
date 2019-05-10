package com.secusoft.web.controller;

import com.google.code.kaptcha.Constants;
import com.secusoft.web.common.GlobalApiResult;
import com.secusoft.web.common.exception.InvalidKaptchaException;
import com.secusoft.web.core.base.controller.BaseController;
import com.secusoft.web.core.log.LogManager;
import com.secusoft.web.core.log.factory.LogTaskFactory;
import com.secusoft.web.core.node.MenuNode;
import com.secusoft.web.core.shiro.ShiroKit;
import com.secusoft.web.core.shiro.ShiroUser;
import com.secusoft.web.core.util.ApiMenuFilter;
import com.secusoft.web.core.util.KaptchaUtil;
import com.secusoft.web.core.util.ToolUtil;
import com.secusoft.web.persistence.mapper.MenuMapper;
import com.secusoft.web.persistence.mapper.UserMapper;
import com.secusoft.web.persistence.model.User;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.secusoft.web.core.support.HttpKit.getIp;

/**
 * 登录控制器
 *
 *
 * @Date 2017年1月10日 下午8:25:24
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    UserMapper userMapper;

    /**
     * 点击登录执行的动作
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public GlobalApiResult<Object> loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }


        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }
        //用shiro比对密码之前先判断是否是前端用户
        List<Map<String, Object>> list = userMapper.getRolecode(username);
        String code=null;
        if(list != null&&list.size()>0){
             code = (String)list.get(0).get("code");
        }

        if(!"web".equals(code)){
            return GlobalApiResult.failure(500,"账号类型错误");
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return GlobalApiResult.success();
    }

    /**
     * 退出登录
     */
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public GlobalApiResult<Object> logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return GlobalApiResult.success();
    }
}
