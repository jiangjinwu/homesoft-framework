/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package com.jubaozan.dao.pay.mapper;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import com.jubaozan.dao.pay.domain.Payee;

@Repository
public interface PayeeMapper extends BaseMapper<PayeeDO>{

 public IPage<PayeeDO>search(Page page,PayeeSearchDTO request);
}
