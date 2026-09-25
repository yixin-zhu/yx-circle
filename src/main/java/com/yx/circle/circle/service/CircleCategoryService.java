package com.yx.circle.circle.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yx.circle.entity.CircleCategory;

import java.util.List;

public interface CircleCategoryService extends IService<CircleCategory> {

    List<CircleCategory> listAllOrdered();
}
