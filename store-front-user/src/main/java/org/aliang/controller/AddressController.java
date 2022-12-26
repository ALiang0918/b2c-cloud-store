package org.aliang.controller;

import org.aliang.param.AddressListParam;
import org.aliang.param.AddressRemoveParam;
import org.aliang.pojo.Address;
import org.aliang.service.AddressService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
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
    public R saveAddress(@RequestBody Address address){
        return addressService.saveAddress(address);
    }

    @PostMapping("/remove")
    public R removeAddress(@RequestBody AddressRemoveParam addressRemoveParam){
        return addressService.remove(addressRemoveParam);
    }
}
