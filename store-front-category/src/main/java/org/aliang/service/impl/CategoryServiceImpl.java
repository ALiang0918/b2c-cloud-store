package org.aliang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.ProductClient;
import org.aliang.mapper.CategoryMapper;
import org.aliang.param.PageParam;
import org.aliang.param.ProductHotParam;
import org.aliang.pojo.Category;
import org.aliang.service.CategoryService;
import org.aliang.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductClient productClient;
    /**
     * 根据类别名称查询类别并封装返回
     *
     * @param categoryName 类别名称
     * @return
     */
    @Override
    public R byName(String categoryName) {
        //1.参数校验
        if (StringUtils.isBlank(categoryName)){
            return R.fail("参数异常！查询失败！");
        }
        //2.查询数据库
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Category::getCategoryName,categoryName);
        Category category = categoryMapper.selectOne(lambdaQueryWrapper);
        if (category == null){
            log.info("org.aliang.service.impl.CategoryServiceImpl.byName()业务方法结束，查询类别:={}=没有数据",categoryName);
            return R.fail("查询的类别不存在！");
        }
        log.info("org.aliang.service.impl.CategoryServiceImpl.byName()业务方法结束，查询类别:={}=成功",categoryName);
        return R.ok(category);
    }

    /**
     * 根据传入的热门类别名称集合！返回类别对应的id集合
     *
     * @param productHotParam 类别名称集合 已校验
     * @return
     */
    @Override
    public R hots(ProductHotParam productHotParam) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Category::getCategoryName,productHotParam.getCategoryName());
        lambdaQueryWrapper.select(Category::getCategoryId);
        List<Object> idList = categoryMapper.selectObjs(lambdaQueryWrapper);
        if (idList == null || idList.size() == 0){
            return R.fail("查询失败!");
        }
        log.info("org.aliang.service.impl.CategoryServiceImpl.hots()业务方法执行完毕，结果为：{}","查询成功");
        return R.ok("类别集合id查询成功",idList);
    }

    /**
     * 查询商品类别集合
     *
     * @return
     */
    @Override
    public R getCategoryList() {
        List<Category> categoryList = categoryMapper.selectList(null);
        log.info("org.aliang.service.impl.CategoryServiceImpl.getCategoryList()业务方法执行完毕");
        return R.ok("查询商品类别集合成功！",categoryList);
    }

    /**
     * 根据商品名称查询商品id 供商品服务调用
     *
     * @param productHotParam
     * @return
     */
    @Override
    public R names(ProductHotParam productHotParam) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Category::getCategoryName,productHotParam.getCategoryName());
        lambdaQueryWrapper.select(Category::getCategoryId);
        List<Object> categoryIdList = categoryMapper.selectObjs(lambdaQueryWrapper);
        log.info("org.aliang.service.impl.CategoryServiceImpl.names业务完成，结果为:{}",categoryIdList);
        return R.ok("查询成功！",categoryIdList);
    }

    /**
     * 后台管理 类别分页查询
     *
     * @param pageParam
     * @return
     */
    @Override
    public R pageList(PageParam pageParam) {
        IPage<Category> page = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        page = categoryMapper.selectPage(page,null);
        log.info("org.aliang.service.impl.CategoryServiceImpl.pageList业务结束，结果为:{}",page);
        return R.ok("类别查询成功！",page.getRecords(),page.getTotal());
    }

    /**
     * 后台管理 新增类别
     *
     * @param category
     * @return
     */
    @Override
    public R saveCategory(Category category) {
        if (category == null || StringUtils.isEmpty(category.getCategoryName())){
            return R.fail("类别保存失败!");
        }
        int rows = categoryMapper.insert(category);
        if (rows == 0){
            return R.fail("类别保存失败!");
        }
        return R.ok("类别保存成功");
    }

    /**
     * 根据id删除类别
     *
     * @param categoryId
     * @return
     */
    @Override
    public R remove(Integer categoryId) {
        Long count = productClient.count(categoryId);
        if (count > 0){
            return R.fail("无法删除类别,有:"+count+"件商品引用!");
        }

        int rows = categoryMapper.deleteById(categoryId);

        if (rows == 0){

            return R.fail("删除类别失败!");
        }
        return R.ok("类别删除成功!");
    }

    @Override
    public R update(Category category) {
        int rows = categoryMapper.updateById(category);

        if (rows > 0){
            return R.ok("类别修改成功!");
        }

        return R.fail("类别修改失败!");
    }
}
