package tw.idv.rainbow.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Login username & password")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @Schema(description = "username", example = "admin")
    private String username;
    @Schema(description = "password", example = "admin")
    private String password;
}
