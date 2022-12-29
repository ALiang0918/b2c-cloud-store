package org.aliang.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aliang.clients.ProductClient;
import org.aliang.doc.ProductDoc;
import org.aliang.pojo.Product;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class SpringBootListener implements ApplicationRunner {

    @Autowired
    private ProductClient productClient;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //1.判断es中是否存在product索引
        GetIndexRequest request = new GetIndexRequest("product");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);

        //2.判断处理
        if (exists){
            //存在，删除原有数据
            DeleteByQueryRequest queryRequest = new DeleteByQueryRequest("product");
            queryRequest.setQuery(QueryBuilders.matchAllQuery());
            restHighLevelClient.deleteByQuery(queryRequest,RequestOptions.DEFAULT);
        }else {
            //不存在，创建新的索引表即可
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            String indexStr = "{\n" +
                    "  \"mappings\": {\n" +
                    "    \"properties\": {\n" +
                    "      \"productId\":{\n" +
                    "        \"type\": \"integer\"\n" +
                    "      },\n" +
                    "      \"productName\":{\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"analyzer\": \"ik_smart\",\n" +
                    "        \"copy_to\": \"all\"\n" +
                    "      },\n" +
                    "      \"categoryId\":{\n" +
                    "        \"type\": \"integer\"\n" +
                    "      },\n" +
                    "      \"productTitle\":{\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"analyzer\": \"ik_smart\",\n" +
                    "        \"copy_to\": \"all\"\n" +
                    "      },\n" +
                    "      \"productIntro\":{\n" +
                    "        \"type\":\"text\",\n" +
                    "        \"analyzer\": \"ik_smart\",\n" +
                    "        \"copy_to\": \"all\"\n" +
                    "      },\n" +
                    "      \"productPicture\":{\n" +
                    "        \"type\": \"keyword\",\n" +
                    "        \"index\": false\n" +
                    "      },\n" +
                    "      \"productPrice\":{\n" +
                    "        \"type\": \"double\",\n" +
                    "        \"index\": true\n" +
                    "      },\n" +
                    "      \"productSellingPrice\":{\n" +
                    "        \"type\": \"double\"\n" +
                    "      },\n" +
                    "      \"productNum\":{\n" +
                    "        \"type\": \"integer\"\n" +
                    "      },\n" +
                    "      \"productSales\":{\n" +
                    "        \"type\": \"integer\"\n" +
                    "      },\n" +
                    "      \"all\":{\n" +
                    "        \"type\": \"text\",\n" +
                    "        \"analyzer\": \"ik_max_word\"\n" +
                    "      }\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            createIndexRequest.source(indexStr, XContentType.JSON);
            restHighLevelClient.indices().create(createIndexRequest,RequestOptions.DEFAULT);
        }
        //3.查询全部商品数据
        List<Product> productList = productClient.getProductList();

        //4.批量数据插入
        ObjectMapper objectMapper = new ObjectMapper();
        BulkRequest bulkRequest = new BulkRequest();
        for (Product product : productList) {
            ProductDoc productDoc = new ProductDoc(product);
            IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
            String json = objectMapper.writeValueAsString(productDoc);
            indexRequest.source(json,XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        restHighLevelClient.bulk(bulkRequest,RequestOptions.DEFAULT);
    }
}
