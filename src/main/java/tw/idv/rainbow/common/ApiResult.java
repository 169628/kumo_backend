package tw.idv.rainbow.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResult<T> success(T data){
        return new ApiResult(1,"success", data);
    }

    public static <T> ApiResult<T> error(String message){
        return new ApiResult(0,message,null);
    }
}
