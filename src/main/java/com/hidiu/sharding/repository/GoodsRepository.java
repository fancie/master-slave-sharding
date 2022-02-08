package com.hidiu.sharding.repository;

import com.hidiu.sharding.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author fancie
 * @title: GoodsRepository
 * @projectName multi-sharding
 * @description: TODO
 * @date 2022/2/7 上午11:10
 */
@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g where g.goodsId =(?1) and g.goodsType = (?2)")
    Goods findOne(@Param("id") Long id, @Param("type")Long type);

}
