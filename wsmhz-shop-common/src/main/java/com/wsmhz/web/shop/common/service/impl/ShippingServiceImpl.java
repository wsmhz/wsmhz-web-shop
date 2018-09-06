package com.wsmhz.web.shop.common.service.impl;
import com.wsmhz.common.business.service.BaseServiceImpl;
import com.wsmhz.web.shop.common.dao.ShippingMapper;
import com.wsmhz.web.shop.common.domain.Shipping;
import com.wsmhz.web.shop.common.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * create by tangbj on 2018/5/27
 */
@Service
public class ShippingServiceImpl extends BaseServiceImpl<Shipping> implements ShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    @Override
    public List<Shipping> selectAllByUserId(Long userId) {
        Example example = new Example(Shipping.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("update_date desc");
        criteria.andEqualTo("userId",userId);
        return shippingMapper.selectByExample(example);
    }
}
