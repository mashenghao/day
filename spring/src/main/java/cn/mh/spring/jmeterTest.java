package cn.mh.spring;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author mash
 * @date 2024/9/1 下午10:32
 */
@RestController
public class jmeterTest {

    private int i = 0;

    @PostMapping("/tag")
    public List<String> get(@RequestBody Req req) {
        if (i++ % 5 == 0) {
            throw new IllegalArgumentException();
        }

        return req.getUsers().stream().map(e -> e.getUserId() + "-" + i++).collect(Collectors.toList());
    }


    @Data
    private static class Req {
        private List<User> users;
    }

    @Data
    public static class User {
        private String userId;
    }

}
