/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.cristik.utils.io;

import com.cristik.utils.lang.ExceptionUtil;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源供给类
 *
 * @author ThinkGem
 * @version 2016-9-16
 */
public class ResourceUtil extends org.springframework.util.ResourceUtils {

    private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * 获取资源加载器（可读取jar内的文件）
     *
     * @author ThinkGem
     */
    public static ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * 获取ClassLoader
     */
    public static ClassLoader getClassLoader() {
        return resourceLoader.getClassLoader();
    }

    /**
     * 获取资源加载器（可读取jar内的文件）
     */
    public static Resource getResource(String location) {
        return resourceLoader.getResource(location);
    }

    /**
     * 获取资源文件流（用后记得关闭）
     *
     * @param location
     * @throws IOException
     * @author ThinkGem
     */
    public static InputStream getResourceFileStream(String location) throws IOException {
        Resource resource = resourceLoader.getResource(location);
        return resource.getInputStream();
    }

    /**
     * 获取资源文件内容
     *
     * @param location
     * @author ThinkGem
     */
    public static String getResourceFileContent(String location) {
        InputStream is = null;
        try {
            is = ResourceUtil.getResourceFileStream(location);
            return IOUtil.toString(is, "UTF-8");
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        } finally {
            IOUtil.closeQuietly(is);
        }
    }

    /**
     * Spring 搜索资源文件
     *
     * @param locationPattern
     * @author ThinkGem
     */
    public static Resource[] getResources(String locationPattern) {
        try {
            Resource[] resources = new PathMatchingResourcePatternResolver()
                    .getResources(locationPattern);
            return resources;
        } catch (IOException e) {
            throw ExceptionUtil.unchecked(e);
        }
    }

}
