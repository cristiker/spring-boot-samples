自动配置类

    org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaConfiguration

参考文章 http://blog.didispace.com/springbootdata2/

junit https://stackoverflow.com/questions/43022602/spring-boot-junit-tests-with-jpa

官方文档 https://docs.spring.io/spring-data/data-jpa/docs/2.0.7.RELEASE/reference/html/

https://my.oschina.net/u/3829444/blog/1820365

@GeneratorValue 4种策略以及支持的数据库

GenerationType.TABLE ---mysql, oracle, postgresql, kingbase GenerationType.AUTO ---mysql, oracle, postgresql, kingbase
GenerationType.IDENTITY ---mysql, postgresql, kingbase GenerationType.SEQUENCE ---oracle, postgresql, kingbase