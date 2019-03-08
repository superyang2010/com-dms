package com.dms.admin.controller;

import com.dms.admin.domain.dto.UserDTO;
import com.dms.admin.domain.param.LoginParam;
import com.dms.admin.service.ILoginService;
import com.dms.pub.base.BaseController;
import com.dms.pub.common.Result;
import com.dms.pub.exception.ExceptionHandler;
import com.dms.pub.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 登录
 * @author yangchao.
 * @date 2018/11/23 10:43
 */
@RestController
@CrossOrigin
@RequestMapping("/dms-admin")
public class LoginController extends BaseController {

    @Value("${spring.captcha.enable}")
    private Boolean captchaEnable;

    @Autowired
    private ILoginService loginService;

    @PostMapping(value = "/login")
    public Result<UserDTO> login(@RequestBody() LoginParam loginParam, HttpSession session) {
        String sessionCaptcha = (String)session.getAttribute("SESSION_CAPTCHA");
        this.loginParamValidate(loginParam, sessionCaptcha);
        UserDTO loginDTO = this.loginService.login(loginParam);
        session.setAttribute("LOGIN_USER_INFO", loginDTO);
        UserDTO dto = ObjectUtil.shallowCopy(loginDTO, UserDTO.class);
        maskKeyInfo(dto);
        return Result.success("登陆成功", dto);
    }

    /**
     * 隐藏关键信息
     * @param loginDTO
     */
    private void maskKeyInfo(UserDTO loginDTO) {
        loginDTO.setRoles(null);
        loginDTO.setId(null);
        loginDTO.setStatus(null);
    }

    @PostMapping(value = "/logout")
    public Result<String> logout(HttpSession session) {
        session.removeAttribute("LOGIN_USER_INFO");
        session.invalidate();
        return Result.success("登出成功");
    }

    private void loginParamValidate(LoginParam loginParam, String sessionCaptcha) {
        if (StringUtils.isEmpty(loginParam.getUsername())) {
            ExceptionHandler.publish("DMS-LOGIN-00001", "请输入用户名");
        }
        if (StringUtils.isEmpty(loginParam.getPassword())) {
            ExceptionHandler.publish("DMS-LOGIN-00002", "请输入错误");
        }
        if (captchaEnable) {
            String inputCaptcha = loginParam.getCaptcha();
            if (StringUtils.isEmpty(inputCaptcha)) {
                ExceptionHandler.publish("DMS-LOGIN-00003", "请输入验证码");
            }
            if (StringUtils.isEmpty(sessionCaptcha)) {
                ExceptionHandler.publish("DMS-LOGIN-00004", "验证码已失效，请刷新重试");
            }
            if (!inputCaptcha.equalsIgnoreCase(sessionCaptcha)) {
                ExceptionHandler.publish("DMS-LOGIN-00005", "验证码错误");
            }
        }
    }
}
