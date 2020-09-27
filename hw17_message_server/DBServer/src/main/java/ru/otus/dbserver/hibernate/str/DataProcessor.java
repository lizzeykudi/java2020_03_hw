package ru.otus.dbserver.hibernate.str;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);

    static void process(Data d) {
        logger.info("key: {}, values:{}", d.getId(), d.getValues());
        d.getValues().clear();
    }

}
