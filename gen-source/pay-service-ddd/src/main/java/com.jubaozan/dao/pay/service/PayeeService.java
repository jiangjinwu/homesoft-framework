/*
 * Copyright(c) 2021 nolan All rights reserved.
 */
package pay.service;

import java.util.List;
import java.util.Map;


import  com.jubaozan.dao.pay.domain.PayeeDO;

/**
 * @version 1.0
 * @author jjw
 */
public interface PayeeService extends IService<PayeeDO> {

     IPage<PayeeDO> search(PayeeSearchDTO search);

     PayeeDO getByUserId(UserId userId);

}
