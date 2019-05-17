package com.immoc.order.form;

import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Pattern;


@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 买家手机号
     */
//    @NotEmpty(message = "手机号必填")
//    private String phone;
    @Pattern(regexp = "^1(3|4|5|7|8)\\d{9}$",message = "手机号码格式错误")
    @NotBlank(message = "手机号码不能为空")
    private String phone;

    /**
     * 买家地址
     */
    @NotBlank(message = "地址不能为空")
    private String address;

    /**
     * 买家微信openid
     */
    @NotBlank(message = "openid必填")
    private String openid;

    /**
     * 购物车
     */
    @NotBlank(message = "购物车不能为空")
    private String items;

}
