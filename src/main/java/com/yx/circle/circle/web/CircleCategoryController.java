package com.yx.circle.circle.web;

import com.yx.circle.circle.service.CircleCategoryService;
import com.yx.circle.common.api.ApiResult;
import com.yx.circle.entity.CircleCategory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "CircleCategory")
@RestController
@RequestMapping("/api/v1/circle-categories")
public class CircleCategoryController {

    private final CircleCategoryService circleCategoryService;

    public CircleCategoryController(CircleCategoryService circleCategoryService) {
        this.circleCategoryService = circleCategoryService;
    }

    @Operation(summary = "List circle categories")
    @GetMapping
    public ApiResult<List<CircleCategory>> list() {
        return ApiResult.ok(circleCategoryService.listAllOrdered());
    }
}
