package cn.mh.spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试springboot mvc 如何读取请求流并解析为参数映射过来的
 *
 * @author mash
 * @date 2024/3/13 22:43
 */

@RestController
public class ParamterController {
    
    @PostMapping
    public Map<String, String> hello(@RequestBody ParamReq paramReq) {

        return new HashMap<>();
    }

    public static class ParamReq {

        private String name;

        private int date;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }
    }
}
