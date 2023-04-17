package com.hspedu.furn.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class Furn {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotEmpty(message = "请输入家具名")
    private String name;

    @NotEmpty(message = "请输入厂商名")
    private String maker;

    @Range(min = 0,message = "价格不能小于零")
    @NotNull(message = "请输入数字")
    private Double price;

    @Range(min = 0,message = "销量不能小于零")
    @NotNull(message = "请输入数字")
    private Integer sales;

    @Range(min = 0,message = "库存不能小于零")
    @NotNull(message = "请输入数字")
    private Integer stock;
}
