集群共享session
======
1. 使用cookie --> 虽然有加密但还是很容易伪造
2. 使用nginx的ip_hash(原理：通过ip地址使其访问同一台服务器) --> 因为是通过ip保证访问哪一台服务器，所以需要nginx服务器在最前端
3. 使用缓存机制+session
4. 应用服务器内置session共享机制(session copy) --> 需要同步session消耗服务器性能，而且不同类型的应用服务器不能通讯
