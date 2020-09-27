###spring-boot-quartz
自动配置类

    org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration
    
监听应用启动时初始化Quartz

        @Bean
        public QuartzInitializerListener quartzInitializerListener() {
            return new QuartzInitializerListener();
        }
        
添加监听器后要通过 org.quartz.jobStore.dataSource 指定数据源
[集群数据源配置](http://www.quartz-scheduler.org/documentation/quartz-2.x/configuration/ConfigDataSources.html)


参考  
https://blog.csdn.net/RickyIT/article/details/77628354