package com.supaak.microservices.camelmicroservicea.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.logging.impl.SimpleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {
    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        // queue
        // transformation
        // database

        from("timer:first-timer") //.to("log:first-timer");
                .log("${body}")
                .transform().constant("My Constant Message")
                .log("${body}")
        .bean("getCurrentTimeBean")
                .log("${body}")
        .bean(loggingComponent)
                .log("${body}")
        .process(new SimpleLoggingProcessor())
                .log("${body}")
        .to("log:first-timer"); //databases
    }


}

/**
 *
 *  2 main tasks
 *  Processing
 *      can make a bean()
 *      can make a process()
 *  Transformation
 *      can make a bean()
 *      can make a transform()
 *
 */

@Component
class GetCurrentTimeBean {
    public String getCurrentTime(){
        return "Time now is " + LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message){
        logger.info("SimpleLoggingProcessingComponent {}",message);
    }
}

class SimpleLoggingProcessor implements Processor {

    private  Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessor {}", exchange);
    }

}