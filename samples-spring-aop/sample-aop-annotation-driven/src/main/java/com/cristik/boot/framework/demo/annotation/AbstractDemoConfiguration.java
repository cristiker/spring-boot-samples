package com.cristik.boot.framework.demo.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

/**
 * @author cristik
 */

@Configuration
public class AbstractDemoConfiguration implements ImportAware {

    @Nullable
    protected AnnotationAttributes enableDemo;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.enableDemo = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableDemo.class.getName(), false));
        if (this.enableDemo == null) {
            throw new IllegalArgumentException(
                    "@EnableDemo is not present on importing class" + importMetadata.getClassName()
            );
        }
    }
}
