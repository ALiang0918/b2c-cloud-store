package org.aliang.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.CategoryClient;
import org.aliang.param.PageParam;
import org.aliang.pojo.Category;
import org.aliang.service.CategoryService;
import org.aliang.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryClient categoryClient;
    /**
     * 商品类别分页查询
     *
     * @param pageParam
     * @return
     */
    @Override
    @Cacheable(value = "list.category",key = "#pageParam.currentPage+'-'+#pageParam.pageSize")
    public R getCategoryList(PageParam pageParam) {
        return categoryClient.getCategoryList(pageParam);
    }

    /**
     * 新增类别
     *
     * @param category
     * @return
     */
    @Override
    @CacheEvict(value = "list.category",allEntries = true)
    public R saveCategory(Category category) {
        return categoryClient.save(category);
    }

    @Override
    @CacheEvict(value = "list.category",allEntries = true)
    public R remove(Integer categoryId) {
        return categoryClient.remove(categoryId);
    }

    @Override
    @CacheEvict(value = "list.category",allEntries = true)
    public R update(Category category) {
        R r = categoryClient.update(category);
        log.info("CategoryServiceImpl.update业务结束，结果:{}",r);
        return r;
    }
}
