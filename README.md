# Asynchronous communication between Microservices with Kafka
This mono repo contains two microservices projects which was developed-Kafka With Java, Spring and Docker, Asynchronous Communication Between Microservices.
This is for the Sales Messaging System. 



### Running the projects on your machine

- clone this public repo with the command : 'git clone https://github.com/preetak/sales-messaging-system.git' to your local working directory

- both projects contain a docker-compose.yml

- use any of them to run kafka docker environment by executing the command from the root directory of either producer or consumer - 'docker-compose up -d'. This will bring up zookeeper and kafka cluster (kafka depends on zookeeper)

- go to <b>localhost:9000</b> to access Kafkdrop web interface for Kafka. Here you will be able to see the topic and messages once the producer adds messages.


- Once the above steps are done, you can use the command -'docker ps' to view the processes that are up - kafka and zookeeper.

- Next in the root directory of 'producer' within the repo, build it using Maven 'mvn clean install' and then from the /producer/target directory run the java application as - 'java -jar producer***.jar'

- Next in the root directory of 'consumer' within the repo, build it using Maven 'mvn clean install' and then from the /consumer/target directory run the java application as - 'java -jar consumer***.jar'

- For sample request through REST call, I have created postment collection with 3 REST based POST requests for Message Type1, Type2 and Type3. You can change the data and create more requests. 

- The REST requests can be made with url - http://localhost:8080/sales as the producer has a controller to handle REST POST requests. The controller calls the service which then writes the message to the Topic.

- The consumer picks up the messages to be processed from the Topic and proceeds with the processing which is handled by the consumer service (SalesService).

- The Threshold of 50 as max messages- 'that can be processed' as well as the threshold of 10 messages 'for reporting' are both configurable. Currently I have set the latter as 6 instead of 10.


- The H2 Database is used for logging all the messages for preserving the history.

- The output of processing the messaging at the consumer end can be seen displayed out on the consumer console. Also the producer console has information on messages that it writes to topic.

- The JUNIT tests are added to make it production ready.

- At anytime the docker processes can be shutdown by command - 'docker-compose down'

-Below is the screenshot of the docker-compose commands to make the kafka and zookeeper up.
-192:producer preetakuruvilla$ docker-compose up -d
Creating network "kafka-net" with driver "bridge"
Creating zookeeper ... done
Creating kafka     ... done
Creating kafdrop   ... done
192:producer preetakuruvilla$ docker ps
CONTAINER ID   IMAGE                      COMMAND                  CREATED         STATUS         PORTS                                                  NAMES
fcb658672e35   obsidiandynamics/kafdrop   "/kafdrop.sh"            6 seconds ago   Up 4 seconds   0.0.0.0:9000->9000/tcp                                 kafdrop
500aaf13b762   obsidiandynamics/kafka     "/bin/sh -c /opt/kaf…"   6 seconds ago   Up 5 seconds   2181/tcp, 0.0.0.0:9092->9092/tcp                       kafka
f7a55b046196   zookeeper:3.7.0            "/docker-entrypoint.…"   9 seconds ago   Up 6 seconds   2888/tcp, 3888/tcp, 0.0.0.0:2181->2181/tcp, 8080/tcp   zookeeper
192:producer preetakuruvilla$ 


REPORT after 6 or 10  messages :

 ***********The Sales Report of 10 sales is as below****************

    : SalesList Size: 4
    : Product Type: P1
    : Total Number of Sales for the Product: 12
    : Total Value of the ProductType: 157.52



REPORT after 50 messages which has adjustments made for each sales details:

2022-08-21 17:53:57.739  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : ***************Messages Total Received is 50 now. No more messages can be processed *******
2022-08-21 17:53:57.739  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : *********Below is the Report of Adjustments during the time the application was running*********
2022-08-21 17:53:57.739  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Message Max Counter :5
2022-08-21 17:53:57.742  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Sales List Size: 3
2022-08-21 17:53:57.742  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Sale Number: 2
2022-08-21 17:53:57.743  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Product Type: P1
2022-08-21 17:53:57.745  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Product Value: 14.0
2022-08-21 17:53:57.746  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Adjustment: 2.0
2022-08-21 17:53:57.750  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Operation used for Adjustment: ADD
2022-08-21 17:53:57.751  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Sale Number: 2
2022-08-21 17:53:57.751  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Product Type: P1
2022-08-21 17:53:57.751  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Product Value: 14.0
2022-08-21 17:53:57.751  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Adjustment: 2.0
2022-08-21 17:53:57.751  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Operation used for Adjustment: ADD
2022-08-21 17:53:57.753  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Sale Number: 2
2022-08-21 17:53:57.753  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Product Type: P1
2022-08-21 17:53:57.753  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Product Value: 14.0
2022-08-21 17:53:57.753  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Adjustment: 2.0
2022-08-21 17:53:57.754  INFO 7286 --- [ntainer#0-0-C-1] c.g.s.consumer.service.SalesService      : Operation used for Adjustment: ADD

SAMPLE REQUESTS Corresponding to the 3 categories of Message Types:

1. Request #1:
{
    "messageType":"TYPE1",
    "salesNumber":"1" ,
    "productType":"P1",
    "value":"10.76"
}

2. Request #2:
{
 	"messageType":"TYPE2",
        "salesNumber":"2",
	"productType":"P1",
	"value":"10" ,
	"numberSold": "5"
}

3. Request #3:

{
    "messageType":"TYPE3",
    "salesNumber":"3" ,
    "productType":"P1",
    "operation": "ADD",
    "adjustment": "2"
}



