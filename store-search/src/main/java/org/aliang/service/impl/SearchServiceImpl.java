package org.aliang.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aliang.param.ProductSearchParam;
import org.aliang.pojo.Product;
import org.aliang.service.SearchService;
import org.aliang.utils.R;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;


@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    /**
     * 商品搜索
     *
     * @param productSearchParam
     * @return
     */
    @Override
    public R searchProduct(ProductSearchParam productSearchParam) {
        SearchRequest searchRequest = new SearchRequest("product");
        //判断参数是否为空
        if (StringUtils.isEmpty(productSearchParam.getSearch())){
            //为空，查询全部数据
            searchRequest.source().query(QueryBuilders.matchAllQuery());
        }else {
            //不为空，按照参数查询
            searchRequest.source().query(QueryBuilders.matchQuery("all",productSearchParam.getSearch()));
        }
        //设置分页参数
        searchRequest.source().from((productSearchParam.getCurrentPage() - 1) * productSearchParam.getPageSize());
        searchRequest.source().size(productSearchParam.getPageSize());
        SearchResponse response = null;
        try {
            response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //结果解析
        SearchHits hits = response.getHits();
        //获取符合的数量
        long value = hits.getTotalHits().value;
        SearchHit[] hitsHits = hits.getHits();
        ArrayList<Product> arrayList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (SearchHit hitsHit : hitsHits) {
            String json = hitsHit.getSourceAsString();
            Product product = null;
            try {
                product = objectMapper.readValue(json, Product.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            arrayList.add(product);
        }
        R r = R.ok("查询成功", arrayList, value);
        log.info("org.aliang.service.impl.SearchServiceImpl.searchProduct()业务方法执行完毕,查询结果为：{}",r);
        return r;
    }
}
