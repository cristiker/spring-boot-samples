package com.cristik.sample.entity.sharding.validator;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author zhenghua.ni
 */
@Data
@Accessors(chain = true)
public class SampleShardingValidator {

    private Long userId;

    private String name;

}
