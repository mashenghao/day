package org.example;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mash
 * @date 2024/1/9 23:02
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventLog {

    private String id;

    private String msg;

    private long time;
}
