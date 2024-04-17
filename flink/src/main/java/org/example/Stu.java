package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mash
 * @date 2023/12/30 18:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stu {

    private int id;

    private String name;

    private String sex;

    private String classId;


    public static void sleep(long s) {
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
