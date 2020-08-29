package com.lweishi.constant;

/**
 * @Author geek
 * @CreateTime 2020/8/21 19:20
 * @Description 常量
 */
public class Constant {
    public static Boolean BANNER_VALID = true; //轮播图状态启用
    public static Boolean BANNER_INVALID = false; //轮播图状态停用

    public static Boolean BRAND_VALID = true; //品牌状态启用
    public static Boolean BRAND_INVALID = false; //品牌状态停用

    public static Boolean PRODUCT_VALID = true; //手机产品状态启用
    public static Boolean PRODUCT_INVALID = false; //手机产品状态停用

    public static Integer REPAIR_ORDER_TYPE_VISIT = 0; //订单类型为：上门
    public static Integer REPAIR_ORDER_TYPE_POST = 1; //订单类型为：邮寄

    public static Integer REPAIR_ORDER_STATUS_WAITING = 0; //订单状态：待接单
    public static Integer REPAIR_ORDER_STATUS_PROCESSING = 1; //订单状态：进行中
    public static Integer REPAIR_ORDER_STATUS_COMPLETED = 2; //订单状态：已完成
    public static Integer REPAIR_ORDER_STATUS_CANCELLED = 3; //订单状态：已取消

    public static Integer ADDRESS_PREFERRED = 1; //地址为默认：1
    public static Integer ADDRESS_COMMON = 0; //地址为普通：0
}
