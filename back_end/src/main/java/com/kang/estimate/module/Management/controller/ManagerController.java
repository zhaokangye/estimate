package com.kang.estimate.module.Management.controller;


import com.kang.estimate.controller.ViewModel.ServerVO;
import com.kang.estimate.controller.ViewModel.UserVO;
import com.kang.estimate.core.base.BaseController;
import com.kang.estimate.module.Management.entity.Server;
import com.kang.estimate.module.Management.entity.User;
import com.kang.estimate.core.error.BussinessException;
import com.kang.estimate.core.error.EmBussinessError;
import com.kang.estimate.core.response.CommonReturnType;
import com.kang.estimate.module.Management.service.ManagerService;
import com.kang.estimate.utils.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;


@RestController
public class ManagerController extends BaseController {

    @Autowired
    ManagerService managerService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    //添加用户
    @PostMapping("/user")
    public CommonReturnType user(@RequestParam String userName,@RequestParam String password) throws BussinessException {

        return CommonReturnType.create(null);
    }

    //删除用户
    @RequestMapping("/deleteUser/{password}")
    public CommonReturnType deleteUser(@PathVariable("password") String password) throws BussinessException {
        boolean isLogin=(boolean)this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(!isLogin){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_EXIST);
        }
        int count=managerService.deleteUser(userVO.getUserid(),userVO.getUsername(),password);
        this.httpServletRequest.getSession().invalidate();
        return CommonReturnType.create("已影响"+count+"行");
    }

    //登陆
    @RequestMapping("/login/{username}/{password}")
    public CommonReturnType Login(@PathVariable("username") String username,@PathVariable("password") String password) throws BussinessException {
        User user=managerService.login(username,password);
        UserVO userVO= Convert.convertFromUser(user);
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userVO);
        return CommonReturnType.create(userVO);
    }



    //添加主机
    @RequestMapping("/addServer/{host}/{port}/{role}/{password}/{servername}")
    public CommonReturnType addServer(@PathVariable("host") String host,
                                      @PathVariable("port") int port,
                                      @PathVariable("role") String role,
                                      @PathVariable("password") String password,
                                      @PathVariable("servername") String servername) throws BussinessException {
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        Server server=managerService.addServer(host,port,role,password,servername,userVO.getUserid());
        ServerVO serverVO=Convert.convertFromServer(server);
        return CommonReturnType.create(serverVO);
    }



    //查询用户所有已添加的主机
    @RequestMapping("/findUserServer")
    public CommonReturnType findServerByUserid() throws BussinessException {
        boolean isLogin=(Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(!isLogin){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        return CommonReturnType.create(managerService.findServerByUserid(userVO.getUserid()));
    }

    //删除主机
    @RequestMapping("/deleteServer/{host}")
    public CommonReturnType deleteServer(@PathVariable("host") String host) throws BussinessException {
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        managerService.deleteServer(userVO.getUserid(),host);
        return CommonReturnType.create(null);
    }

    //删除所有主机
    @RequestMapping("/deleteAllServer/{password}")
    public CommonReturnType deleteAllServer(@PathVariable("password") String password) throws BussinessException {
        //拦截器 todo
        UserVO userVO=(UserVO)this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        if(userVO==null){
            throw new BussinessException(EmBussinessError.NOT_LOGIN);
        }
        managerService.deleteAllServer(userVO.getUsername(),userVO.getUserid(),password);
        return CommonReturnType.create(null);
    }

    //测试主机连通情况
    @RequestMapping("/testConnection/{host}")
    public CommonReturnType testConnection(@PathVariable("host") String host) throws BussinessException {
        managerService.testConnection(host);
        return CommonReturnType.create(null);
    }
}
