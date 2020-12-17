package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.BookOrder;
import com.mdrsolutions.SpringJmsExample.pojos.ProcessedBookOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class WareHouseReceiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WareHouseReceiverService.class);

    @Autowired
    WarehouseProcessingService warehouseProcessingService;

    @JmsListener(destination = "book.order.queue")
    @SendTo("book.order.processed.queue")
    public ProcessedBookOrder receive(@Payload BookOrder bookOrder,
                                      @Header(name="orderState") String orderState,
                                      @Header(name="bookOrderId") String bookOrderId,
                                      @Header(name="storeId") String storeId,
                                      MessageHeaders messageHeaders){
        LOGGER.info("received a message");
        LOGGER.info("Message is == "+bookOrder);

        LOGGER.info("Message property orderState = {}, bookOrderId = {}, storeId = {}",
                orderState, bookOrderId, storeId);
        LOGGER.info("MessageHeaders = {}",messageHeaders);

        if(bookOrder.getBook().getTitle().startsWith("L")){
            throw new IllegalArgumentException("OrderId="+bookOrder.getBookOrderId()+"begins" +
                    " with L and these books are not allowed");
        }
        return warehouseProcessingService.processOrder(bookOrder, orderState, storeId);
    }
}
