package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.mapper.AddressMapper;
import org.aliang.param.AddressListParam;
import org.aliang.param.AddressRemoveParam;
import org.aliang.pojo.Address;
import org.aliang.service.AddressService;
import org.aliang.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 根据用户id查询用户地址
     *
     * @param addressListParam 用户id 未经过校验
     * @return 用户地址列表
     */
    @Override
    public R getAddressListById(AddressListParam addressListParam) {
        //1.参数校验
        if (addressListParam == null) {
            return R.fail("参数异常，查询失败！");
        }
        //2.根据userId查询对应用户的地址列表
        LambdaQueryWrapper<Address> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Address::getUserId, addressListParam.getUserId());
        List<Address> addressList = addressMapper.selectList(lambdaQueryWrapper);
        if (addressList == null || addressList.size() == 0) {
            return R.ok("暂无地址");
        }
        log.info("AddressServiceImpl.getAddressListById()业务方法结束，查询：{} 的地址列表", addressListParam.getUserId());
        return R.ok("查询成功", addressList);
    }

    /**
     * 保存地址信息
     *
     * @param address 地址信息 未进行校验
     * @return 返回最新的地址信息列表
     */
    @Override
    public R saveAddress(Address address) {
        //1.参数校验
        if (address == null
                || StringUtils.isBlank(address.getAddress())
                || StringUtils.isBlank(address.getPhone())
                || StringUtils.isBlank(address.getLinkman())
                || address.getUserId() == null) {
            return R.fail("参数异常，保存失败");
        }
        //2.插入数据
        int row = addressMapper.insert(address);
        if (row == 0) {
            return R.fail("保存地址失败！");
        }
        //3.查询地址列表数据并返回
        AddressListParam addressListParam = new AddressListParam();
        addressListParam.setUserId(address.getUserId());
        return getAddressListById(addressListParam);
    }

    /**
     * 根据地址id 删除地址信息
     *
     * @param addressRemoveParam 地址id
     * @return 001 删除成功 004 删除失败
     */
    @Override
    public R remove(AddressRemoveParam addressRemoveParam) {
        //1.参数校验
        if (addressRemoveParam == null || addressRemoveParam.getId() == null) {
            return R.fail("参数异常，删除失败！");
        }
        //2.根据id删除地址信息
        int row = addressMapper.deleteById(addressRemoveParam.getId());
        if (row == 0) {
            return R.fail("删除失败！请重新尝试！");
        }
        log.info("org.aliang.service.impl.AddressServiceImpl.remove() 业务方法执行完毕，{} 地址信息已删除", addressRemoveParam.getId());
        return R.ok("删除成功！");
    }
}
