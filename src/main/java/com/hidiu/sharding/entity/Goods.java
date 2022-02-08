package com.hidiu.sharding.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fancie
 * @title: Goods
 * @projectName multi-sharding
 * @description: TODO
 * @date 2022/2/7 上午11:19
 */
@Entity
@Table(name="goods")
@Data
public class Goods {

    @Id
    private Long goodsId;

    private String goodsName;

    private Long goodsType;

}
