package tw.idv.rainbow.web.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.idv.rainbow.common.ApiResult;
import tw.idv.rainbow.web.dto.LoginDTO;
import tw.idv.rainbow.web.service.LoginService;

@Tag(name = "Login")
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Operation(summary = "Login and get JWT token")
    @PostMapping
    public ApiResult login(@RequestBody LoginDTO loginDTO){
        String token = loginService.login(loginDTO);
        if(token == null){
            return ApiResult.error("Wrong username or password");
        }
        return ApiResult.success(token);
    }
}
