package com.cristik.aop.demo.annotation;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.lang.Nullable;

/**
 * @author cristik
 */
public class DemoConfigurationSelector extends AdviceModeImportSelector<EnableDemo> {

    @Nullable
    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        return new String[0];
    }
}