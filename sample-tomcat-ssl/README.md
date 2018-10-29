
#####1.生成*.p12格式证书
    keytool -genkey -alias tomcat  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650

    1.-storetype 指定密钥仓库类型 
    2.-keyalg 生证书的算法名称，RSA是一种非对称加密算法 
    3.-keysize 证书大小 
    4.-keystore 生成的证书文件的存储路径 
    5.-validity 证书的有效期

#####2.用户openssl生成证书

    # private key
    $openssl genrsa -des3 -out server.key 1024 
    # generate csr
    $openssl req -new -key server.key -out server.csr
    # generate certificate
    $openssl ca -in server.csr -out server.crt -cert ca.crt -keyfile ca.key  
    
#####3.转换成PEM格式

    cat server.crt server.key> server.pem
    
 
    
    
    

