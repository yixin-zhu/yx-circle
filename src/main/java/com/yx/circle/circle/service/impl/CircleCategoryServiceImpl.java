package com.yx.circle.circle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yx.circle.circle.service.CircleCategoryService;
import com.yx.circle.entity.CircleCategory;
import com.yx.circle.mapper.CircleCategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CircleCategoryServiceImpl extends ServiceImpl<CircleCategoryMapper, CircleCategory>
        implements CircleCategoryService {

    @Override
    public List<CircleCategory> listAllOrdered() {
        return lambdaQuery().orderByAsc(CircleCategory::getSort).list();
    }
}
