package com.mdrsolutions.SpringJmsExample.service.jms;

import com.mdrsolutions.SpringJmsExample.pojos.BookOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class WareHouseReceiverService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WareHouseReceiverService.class);

    @Autowired
    WarehouseProcessingService warehouseProcessingService;

    @JmsListener(destination = "book.order.queue")
    public void receive(BookOrder bookOrder){
        LOGGER.info("received a message");
        LOGGER.info("Message is == "+bookOrder);

        if(bookOrder.getBook().getTitle().startsWith("L")){
            throw new RuntimeException("OrderId="+bookOrder.getBookOrderId()+"begins" +
                    " with L and these books are not allowed");
        }
        warehouseProcessingService.processOrder(bookOrder);
    }
}
