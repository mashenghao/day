package cn.mh.spring.inTx;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

import java.sql.Connection;

import static org.mockito.ArgumentMatchers.anyString;

/**
 * @author mash
 * @date 2024/6/20 下午2:15
 */
@Configuration
public class Config {

    @Bean
    public DataSource dataSource() throws Exception {
        // 配置数据源
        DataSource dataSource = Mockito.mock(DataSource.class);
        Connection connection = Mockito.mock(Connection.class);
        Mockito.when(dataSource.getConnection(anyString(), anyString())).thenReturn(connection);
        Mockito.when(dataSource.getConnection()).thenReturn(connection);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
