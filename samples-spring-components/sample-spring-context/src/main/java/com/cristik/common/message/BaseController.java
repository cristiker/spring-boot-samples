package com.cristik.common.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author cristik
 */
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 管理基础路径
     */
    @Value("${application.prefix:}")
    protected String systemPrefix;

}
