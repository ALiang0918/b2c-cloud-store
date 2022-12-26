package org.aliang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.aliang.param.AddressListParam;
import org.aliang.param.AddressRemoveParam;
import org.aliang.pojo.Address;
import org.aliang.utils.R;

public interface AddressService extends IService<Address> {

    /**
     * 根据用户id查询用户地址
     * @param addressListParam 用户id 未经过校验
     * @return 用户地址列表
     */
    R getAddressListById(AddressListParam addressListParam);

    /**
     * 保存地址信息
     * @param address 地址信息 未进行校验
     * @return 返回最新的地址信息列表
     */
    R saveAddress(Address address);

    /**
     * 根据地址id 删除地址信息
     * @param addressRemoveParam 地址id
     * @return 001 删除成功 004 删除失败
     */
    R remove(AddressRemoveParam addressRemoveParam);
}
