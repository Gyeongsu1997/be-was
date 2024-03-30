# java-was-2023

Java Web Application Server 2023

## 기존 자바 스레드 모델의 한계

스프링 프레임워크는 멀티 스레드 모델을 사용하고 있으며, 1개의 요청을 1개의 스레드가 처리하는 thread-per-request 방식으로 동작하고 있다. 
하지만 기존 JDK의 스레드는 운영 체제(OS) 스레드의 Wrapper이기 때문에, 사용 가능한 스레드의 수가 하드웨어 수준보다 훨씬 적게 제한되어 있었다.
OS 스레드는 비용이 높아 요청량에 비례하여 늘릴 수 없기 때문이다.
또한 자바 스레드는 I/O 작업 시에 블로킹되어 하드웨어를 최적으로 활용하지 못했다.
이러한 문제를 해결하기 위해 자바 개발자들은 2023년 9월에 정식 출시된 JDK21에서 가상 스레드를 도입하게 되었다.

## 가상 스레드의 개념

기존 자바 스레드 모델에서 하드웨어를 최적으로 활용하지 못하는 근본적인 원인은 OS 스레드와 자바 스레드가 일대일 대응되기 때문이다.
따라서 해결 방안은 자바 런타임에서 OS 스레드와 일대일 대응되지 않는 더 효율적인 스레드를 구현해 사용하는 것이다.
운영 체제가 많은 가상 주소 공간을 제한된 양의 물리적 RAM에 매핑하여 메모리가 넉넉한 것처럼 보이게 하는 것처럼, 자바 런타임은 많은 수의 가상 스레드를 적은 수의 OS 스레드에 매핑하여 스레드가 넉넉한 것처럼 보이게 한다.
가상 스레드는 OS가 아닌 JDK에서 제공하는 경량화된 user-mode 스레드이고 가상 스레드는 CPU에서 연산을 수행하는 동안에만 OS 스레드를 사용한다.
가상 스레드는 생성 비용이 저렴하고 거의 무한대로 풍부한 스레드로 이를 통해 하드웨어 활용도가 최적에 가까워져 높은 수준의 동시성을 구현하고 결과적으로 높은 처리량을 제공한다.

참고: https://mangkyu.tistory.com/309

## Concurrent 패키지를 어떻게 사용할 지 고민한 점

기존의 프로젝트에서는 Client의 요청을 받을 때마다 새로운 스레드를 생성하여 요청을 처리한다.
이러한 방식으로는 스레드의 생성 및 종료에 따른 오버헤드가 발생하고 스레드를 계속해서 만들면 운영체제의 자원이 소진될 수 있다.
따라서 Concurrent 패키지에서 제공하는 스레드 풀 기능을 활용하기로 했다.
java.util.concurrent 패키지의 ThreadPoolExecutor 클래스의 생성자를 호출하여 스레드 풀을 만들었다.
아래 아파치 톰캣의 스레드 풀 설정을 참고하여 corePoolSize를 25, maximumPoolSize를 200, keepAliveTime을 60초로 설정했다.

https://tomcat.apache.org/tomcat-8.5-doc/config/executor.html

## 구현 상 특이사항

HTTP Request Header의 첫번째 라인에서 추출한 요청 URL이 "/"인 경우 index.html파일을 반환하도록 하였다.

## 프로젝트 정보 

이 프로젝트는 우아한 테크코스 박재성님의 허가를 받아 https://github.com/woowacourse/jwp-was 
를 참고하여 작성되었습니다.
