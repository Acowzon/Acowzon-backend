package org.acowzon.backend.ctrl;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DefaultWebResponse {
    private Object data;
    private String status;
    private String msg;

    public static class Builder {
        public static DefaultWebResponse success(String msg) {
            DefaultWebResponse response = new DefaultWebResponse();
            response.setData(null);
            response.setMsg(msg);
            response.setStatus("success");
            return response;
        }

        public static DefaultWebResponse success(String msg,Object data) {
            DefaultWebResponse response = new DefaultWebResponse();
            response.setData(data);
            response.setMsg(msg);
            response.setStatus("success");
            return response;
        }

        public static DefaultWebResponse success(Object data) {
            DefaultWebResponse response = new DefaultWebResponse();
            response.setData(data);
            response.setMsg("");
            response.setStatus("success");
            return response;
        }

        public static DefaultWebResponse fail(String message) {
            DefaultWebResponse response = new DefaultWebResponse();
            response.setData(null);
            response.setStatus("error");
            response.setMsg(message);
            return response;
        }
    }
}
