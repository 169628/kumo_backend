package tw.idv.rainbow.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.idv.rainbow.security.JwtUtil;
import tw.idv.rainbow.web.dto.LoginDTO;
import tw.idv.rainbow.web.service.LoginService;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(LoginDTO loginDTO) {
        if (loginDTO.getUsername() == null || loginDTO.getPassword() == null || !Objects.equals("admin", loginDTO.getUsername()) || !Objects.equals("admin", loginDTO.getPassword())) {
            return null;
        }
        return jwtUtil.generateToken(loginDTO.getUsername());
    }
}
