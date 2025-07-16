package rx;

import com.volcengine.ApiClient;
import com.volcengine.ApiException;
import com.volcengine.billing.BillingApi;
import com.volcengine.billing.model.QueryBalanceAcctRequest;
import com.volcengine.billing.model.QueryBalanceAcctResponse;
import com.volcengine.billing.model.UnsubscribeInstanceRequest;
import com.volcengine.sign.Credentials;

/**
 * @author mash
 * @date 2025/4/21 下午2:53
 */
public class BillMain {

    public static void main(String[] args) throws Exception {
        // 注意示例代码安全，代码泄漏会导致AK/SK泄漏，有极大的安全风险。
        String ak = "XXXX";
        String sk = "XXX==";
        String region = "cn-beijing";

        ApiClient apiClient = new ApiClient()
                .setCredentials(Credentials.getCredentials(ak, sk))
                .setRegion(region);

        BillingApi api = new BillingApi(apiClient);

        UnsubscribeInstanceRequest unsubscribeInstanceRequest = new UnsubscribeInstanceRequest();

        try {
            QueryBalanceAcctRequest rq = new QueryBalanceAcctRequest();
            QueryBalanceAcctResponse queryBalanceAcctResponse = api.queryBalanceAcct(rq);
            System.out.println(queryBalanceAcctResponse);
        } catch (ApiException e) {
            // 复制代码运行示例，请自行打印API错误信息。
            // System.out.println(e.getResponseBody());
        }
    }

}
