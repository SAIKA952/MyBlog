package yjb.bysj.service;

import java.util.Map;

public interface MessageService {

    boolean send(Map<String, Object> param, String phone);
}
