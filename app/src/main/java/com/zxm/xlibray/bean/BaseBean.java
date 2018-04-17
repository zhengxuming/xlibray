package com.zxm.xlibray.bean;

/**
 * Created by Steven on 2017/8/2.
 */

public class BaseBean<T> {

    public String err_code;
    public String err_msg;
    public String status;
    public T data;

    @Override
    public String toString() {
        return "BaseBean{" +
                "err_code=" + err_code +
                ", err_msg='" + err_msg + '\'' +
                ", status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
