package com.business.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: GaoLu
 * @Date: 2023-10-20 17:42
 * @Description: 新增、修改自动填充
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 默认策略：如果属性有值则不覆盖，如果填充值为null则不填充

    private static final String CREATE_TIME = "createTime";
    private static final String UPDATE_TIME = "updateTime";

    /**
     * 做insert插入时自动填充的值（这里一般是create_time和update_time）
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter(CREATE_TIME) && metaObject.hasGetter(UPDATE_TIME)) { // 实体类有get方法，就是有这个字段
            LocalDateTime localDateTime = LocalDateTime.now();
            this.strictInsertFill(metaObject, CREATE_TIME, () -> localDateTime, LocalDateTime.class); // 起始版本 3.3.3(推荐)
            //this.strictInsertFill(metaObject, UPDATE_TIME, () -> localDateTime, LocalDateTime.class); // 起始版本 3.3.3(推荐)
        }
    }

    /**
     * 做update更新时自动填充的值（更新就只针对update_time）
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter(UPDATE_TIME)) {
            metaObject.setValue(UPDATE_TIME, LocalDateTime.now());
            // this.strictUpdateFill(metaObject, UPDATE_TIME, () -> LocalDateTime.now(), LocalDateTime.class);
        }
    }
}
