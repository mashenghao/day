package org.example.fValueState;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.checkpoint.ListCheckpointed;

import java.util.Collections;
import java.util.List;

/**
 * @author mash
 * @date 2024/1/10 23:27
 */
public class MyMapper implements MapFunction<Tuple2<String, Integer>, Integer>, ListCheckpointed<Integer> {

    Integer i = 0;

    @Override
    public Integer map(Tuple2<String, Integer> value) throws Exception {
        i++;
        return i;
    }

    @Override
    public List<Integer> snapshotState(long checkpointId, long timestamp) throws Exception {
        return Collections.singletonList(i);
    }

    @Override
    public void restoreState(List<Integer> state) throws Exception {
        i = state.stream().mapToInt(e -> e).sum();
    }
}
