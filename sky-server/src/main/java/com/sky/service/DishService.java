package com.sky.service;

import com.sky.dto.DishDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

public interface DishService {

    /**
     * 新增菜品，和对应的口味数据
     * @param dishDTO
     */

    void saveWithFlavor(DishDTO dishDTO);

}
