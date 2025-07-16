package aa;

/**
 * @author mash
 * @date 2025/2/19 下午2:52
 */

import com.alibaba.fastjson.JSONObject;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionChunk;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionRequest;
import com.volcengine.ark.runtime.model.bot.completion.chat.BotChatCompletionResult;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionChoice;
import com.volcengine.ark.runtime.model.completion.chat.ChatCompletionRequest;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessage;
import com.volcengine.ark.runtime.model.completion.chat.ChatMessageRole;
import com.volcengine.ark.runtime.service.ArkService;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(10);

        ArkService service = ArkService.builder()
                .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
//                .dispatcher(dispatcher)
                .apiKey("XXXX")
//                .connectionPool()
                .callbackExecutor(Executors.newFixedThreadPool(10))
                .build();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        ;
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                final List<ChatMessage> streamMessages = new ArrayList<>();
                final ChatMessage streamSystemMessage = ChatMessage.builder().role(ChatMessageRole.SYSTEM).content("你是豆包，是由字节跳动开发的 AI 人工智能助手").build();
                final ChatMessage streamUserMessage = ChatMessage.builder().role(ChatMessageRole.USER).content("常见的十字花科植物有哪些？").build();
                streamMessages.add(streamSystemMessage);
                streamMessages.add(streamUserMessage);

                BotChatCompletionRequest streamChatCompletionRequest = BotChatCompletionRequest.builder()
                        // 将<YOUR_BOT_ID>替换为您的应用ID
                        .model("bot-XX-XX")
                        .messages(streamMessages)
                        .build();

                service.streamBotChatCompletion(streamChatCompletionRequest)
                        .doOnError(Throwable::printStackTrace)
                        .doOnNext(e -> {
                            concurrentHashMap.put(Thread.currentThread().getId(), 1);
                        })
                        .doOnComplete(() -> concurrentHashMap.remove(Thread.currentThread().getId()))
                        .subscribe();


            });
        }
        while (1 == 1) {
            Thread.sleep(2000);
            System.out.println(concurrentHashMap);
        }
    }
}
