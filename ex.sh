

exec_process_a()
{
  TARGET=scraping-process
  cd ./${TARGET}/
  java -jar target/${TARGET}-0.0.1-SNAPSHOT.jar --server.port=8083 \
    --spring.rabbitmq.host=dev-shurakaw03-mq.ssk.ynwm.yahoo.co.jp \
    --spring.rabbitmq.port=5672 \
    --spring.rabbitmq.username=rabbit_test \
    --spring.rabbitmq.password=rabbit_test
}

exec_process_b()
{
  TARGET=morphemeanalysis-process
  cd ./${TARGET}/
  java -jar target/${TARGET}-0.0.1-SNAPSHOT.jar --server.port=8087 \
    --spring.rabbitmq.host=dev-shurakaw03-mq.ssk.ynwm.yahoo.co.jp \
    --spring.rabbitmq.port=5672 \
    --spring.rabbitmq.username=rabbit_test \
    --spring.rabbitmq.password=rabbit_test
}

exec_source()
{
  TARGET=vcpf-api
  cd ./${TARGET}/
  java -jar target/${TARGET}-0.0.1-SNAPSHOT.jar --server.port=8085 \
    --spring.rabbitmq.host=dev-shurakaw03-mq.ssk.ynwm.yahoo.co.jp \
    --spring.rabbitmq.port=5672 \
    --spring.rabbitmq.username=rabbit_test \
    --spring.rabbitmq.password=rabbit_test
}

exec_sink()
{
  TARGET=terminator
  cd ./${TARGET}/
  java -jar target/${TARGET}-0.0.1-SNAPSHOT.jar  --server.port=8082 \
    --spring.rabbitmq.host=dev-shurakaw03-mq.ssk.ynwm.yahoo.co.jp \
    --spring.rabbitmq.port=5672 \
    --spring.rabbitmq.username=rabbit_test \
    --spring.rabbitmq.password=rabbit_test
}

#exec_source
#exec_process_a
exec_process_b
#exec_sink


# curl -XPOST http://localhost:8085/vcpf/search/xxx
