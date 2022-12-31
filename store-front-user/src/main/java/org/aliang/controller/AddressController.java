package org.aliang.controller;

import org.aliang.param.AddressListParam;
import org.aliang.param.AddressParam;
import org.aliang.param.AddressRemoveParam;
import org.aliang.pojo.Address;
import org.aliang.service.AddressService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/list")
    public R getAddressList(@RequestBody AddressListParam addressListParam){
        return addressService.getAddressListById(addressListParam);
    }

    @PostMapping("/save")
    public R saveAddress(@RequestBody @Validated AddressParam addressParam, BindingResult result){
        if (result.hasErrors()){
            return R.fail("参数异常，保存失败！");
        }
        Address address = addressParam.getAdd();
        address.setUserId(addressParam.getUserId());
        return addressService.saveAddress(address);
    }

    @PostMapping("/remove")
    public R removeAddress(@RequestBody AddressRemoveParam addressRemoveParam){
        return addressService.remove(addressRemoveParam);
    }
}
