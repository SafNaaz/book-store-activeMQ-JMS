package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.BookOrder;
import com.mdrsolutions.SpringJmsExample.pojos.ProcessedBookOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class WarehouseProcessingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WarehouseProcessingService.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public Message<ProcessedBookOrder> processOrder(BookOrder bookOrder, String orderState, String storeId){

        if("NEW".equalsIgnoreCase(orderState)){
            return add(bookOrder,storeId);
        }else if("UPDATE".equalsIgnoreCase(orderState)){
            return update(bookOrder,storeId);
        }else if("DELETE".equalsIgnoreCase(orderState)){
            return delete(bookOrder,storeId);
        }else{
            throw new IllegalArgumentException("WarehouseProcessingService.processOrder" +
                    " : orderState did not match expected values ");
        }

    }

    private Message<ProcessedBookOrder> add(BookOrder bookOrder, String storeId){
        LOGGER.info("ADDING A NEW ORDER TO DB");
        //TODO = do some type of db operation
        return build(new ProcessedBookOrder(
                bookOrder,
                new Date(),
                new Date()),"ADDED",storeId
        );
    }

    private Message<ProcessedBookOrder> update(BookOrder bookOrder, String storeId){
        LOGGER.info("UPDATING A ORDER IN DB");
        //TODO = do some type of db operation
        return build(new ProcessedBookOrder(
                bookOrder,
                new Date(),
                new Date()), "UPDATED", storeId
        );
    }

    private Message<ProcessedBookOrder> delete(BookOrder bookOrder, String storeId){
        LOGGER.info("DELETING THE ORDER FROM DB");
        //TODO = do some type of db operation
        return build(new ProcessedBookOrder(
                bookOrder,
                new Date(),
                null), "DELETED", storeId
        );
    }

    private Message<ProcessedBookOrder> build(ProcessedBookOrder bookOrder,
                                              String orderState,
                                              String storeId){
        return MessageBuilder
                .withPayload(bookOrder)
                .setHeader("orderState",orderState)
                .setHeader("storeId",storeId)
                .build();
    }
}
