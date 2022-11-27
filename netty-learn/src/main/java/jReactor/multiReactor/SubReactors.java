package jReactor.multiReactor;


import java.io.IOException;
import java.nio.channels.Selector;
import java.util.concurrent.ExecutorService;

/**
 * 子Reactors。
 *
 * @author mahao
 * @date 2022/11/20
 */
public class SubReactors {
    int num = 2;
    Selector[] selectors;
    SubReactor[] subReactors;

    public SubReactors(ExecutorService workerThreadPool) throws IOException {
        selectors = new Selector[num];
        subReactors = new SubReactor[num];
        for (int i = 0; i < selectors.length; i++) {
            Selector selector = Selector.open();
            SubReactor subReactor = new SubReactor(selector);
            selectors[i] = selector;
            subReactors[i] = subReactor;
            //启动了subReactor
            workerThreadPool.execute(subReactor);
        }
    }

    public SubReactors() {


    }
}
