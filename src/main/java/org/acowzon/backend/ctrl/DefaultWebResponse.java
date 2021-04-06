package org.acowzon.backend.ctrl;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DefaultWebResponse {
    private Object data;
    private String status;
    private String msg;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class Builder {
        public static DefaultWebResponse success(Object data) {
            DefaultWebResponse response = new DefaultWebResponse();
            response.setData(data);
            response.setMsg("");
            response.setStatus("success");
            return response;
        }

        public static DefaultWebResponse fail(String message) {
            DefaultWebResponse response = new DefaultWebResponse();
            response.setStatus("error");
            response.setMsg(message);
            return response;
        }
    }
}
