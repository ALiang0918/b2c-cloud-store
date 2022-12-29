package org.aliang.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aliang.doc.ProductDoc;
import org.aliang.pojo.Product;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitMQListener {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 更新和插入使用同一个api
     * @param product
     * @throws IOException
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "insert.queue"),
                    exchange = @Exchange(name = "topic.ex"),
                    key = "insert.product"
            )
    )
    public void insert(Product product) throws IOException {
        IndexRequest indexRequest = new IndexRequest("product").id(product.getProductId().toString());
        ProductDoc productDoc = new ProductDoc(product);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(productDoc);
        indexRequest.source(json, XContentType.JSON);
        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
    }

    /**
     * 删除数据
     * @param productId
     * @throws IOException
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "remove.queue"),
                    exchange = @Exchange(name = "topic.ex"),
                    key = "remove.product"
            )
    )
    public void remove(Integer productId) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("product").id(productId.toString());
        restHighLevelClient.delete(deleteRequest,RequestOptions.DEFAULT);
    }
}
