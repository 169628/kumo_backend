package tw.idv.rainbow.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.common.ApiResponse;
import tw.idv.rainbow.web.dto.LoginDTO;
import tw.idv.rainbow.web.service.LoginService;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public ApiResponse login(@RequestBody LoginDTO loginDTO){
        String token = loginService.login(loginDTO);
        if(token == null){
            return ApiResponse.error("Wrong username or password");
        }
        return ApiResponse.success(token);
    }
}
