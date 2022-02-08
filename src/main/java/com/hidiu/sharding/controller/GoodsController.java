package com.hidiu.sharding.controller;

import com.hidiu.sharding.entity.Goods;
import com.hidiu.sharding.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fancie
 * @title: GoodsController
 * @projectName multi-sharding
 * @description: TODO
 * @date 2022/2/7 上午11:19
 */
@RestController
public class GoodsController {

    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("/save")
    public String save() {
        for (int i = 1; i <= 30; i++) {
            Goods goods = new Goods();
            goods.setGoodsId((long) i);
            goods.setGoodsName("z" + i);
            goods.setGoodsType((long) (Math.random() * 100 ));
            goodsRepository.save(goods);
        }
        return "success";
    }

    @GetMapping("/getone")
    public Goods getone() {
        Goods goods = goodsRepository.findOne((long)101, (long)26);
        return goods;
    }


}
