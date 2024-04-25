/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.admin.interceptor;


import com.shop.cereshop.admin.utils.ContextUtil;
import com.shop.cereshop.commons.constant.CoReturnFormat;
import com.shop.cereshop.commons.exception.CoBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
@Slf4j
@Component
public class ExecutorInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
//        Object target = invocation.getTarget();
//        StatementHandler statementHandler = (StatementHandler)target;
//
//        BoundSql boundSql = statementHandler.getBoundSql();
//
//        String trimSql = boundSql.getSql().trim().toLowerCase();
//        //过滤掉登录请求
//        if(trimSql.contains("DELETE FROM cere_redis_key where redis_key=")
//                ||trimSql.contains("insert into cere_redis_key (redis_key, end_time)")){
//            //直接释放
//            return invocation.proceed();
//        }
//        if ((trimSql.startsWith("update")
//                || trimSql.startsWith("insert"))
//                && ContextUtil.getAdmin()) {
//            throw new CoBusinessException(CoReturnFormat.DEMON_ACCOUNT_NOT_PERMITTED);
//        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o,
                this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
