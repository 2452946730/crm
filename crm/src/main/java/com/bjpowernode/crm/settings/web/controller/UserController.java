package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.RetObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.settings.model.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date 2022/8/23 19:18
 */
@Controller
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/settings/qx/user/doLogin.do")
    public String doLogin(){
        return "settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        System.out.println(request.getRemoteAddr());
        //处理业务
        User user=userService.queryUserByLoginActAndPwd(map);
        //根据查询结果,生成响应信息
        RetObject retmessage=new RetObject();
        if(user == null){
            //登录失败,用户名或者密码错误
            retmessage.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            retmessage.setMessage("用户名或密码错误!");
        }else{
            if(user.getExpireTime().compareTo(DateUtils.formateDateTime(new Date()))<0){
                //用户已过期
                retmessage.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retmessage.setMessage("用户信息已过期!");
            }else if("0".equals(user.getLockState())){
                //用户状态被锁定
                retmessage.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retmessage.setMessage("用户状态被锁定!");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //ip受限
                retmessage.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                retmessage.setMessage("ip受限!");
            }else{
                //登录成功
                retmessage.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                request.getSession().setAttribute(Contants.SYSTEM_USER,user);
                //如果记住密码,写入cookie
                if("true".equals(isRemPwd)){
                    Cookie c1=new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    Cookie c1=new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return retmessage;
    }
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response,HttpSession session){
        //删除Cookie
        Cookie c1=new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2=new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁Session
        session.invalidate();
        //跳转到登录页面,使用重定向
        return "redirect:/";
    }
}
