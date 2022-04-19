package com.project.common.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    /**
     * dataSource 를 여러개 등록하되,
     * Routing-datasource&LazyLoading 을 이용하여
     * Transaction 의 read-only 여부에 따라 dataSource 를 분리하여 사용
     */

    @Override
    protected Object determineCurrentLookupKey() { //DataSource 연결이 필요할때마다 DataSourceMap에서 어떤 DataSource를 사용할지 Key를 찾아줌
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly(); //현재 진행중인 트랜잭션이 ReadOnly 인지 여부를 판단하여 Master-Slave DataSource 를 분기

        if (isReadOnly) {
            return "slave";
        } else {
            return "master";
        }
    }

}
