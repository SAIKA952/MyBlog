package yjb.bysj.common_utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//统一返回结果类
@Data
public class Res {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<String, Object>();

    //构造方法私有化
    private Res(){}

    //成功的静态方法
    public static Res ok(){
        Res res = new Res();
        res.setSuccess(true);
        res.setCode(20000);
        res.setMessage("成功");
        return res;
    }

    //失败的静态方法
    public static Res error(){
        Res res = new Res();
        res.setSuccess(false);
        res.setCode(20001);
        res.setMessage("失败");
        return res;
    }

    public Res success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Res message(String message){
        this.setMessage(message);
        return this;
    }

    public Res code(Integer code){
        this.setCode(code);
        return this;
    }

    public Res data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Res data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
