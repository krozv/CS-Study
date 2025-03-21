# Computer Architecture

- [Computer Architecture](#computer-architecture)
  - [Contents](#contents)
  - [Questions](#questions)

## Contents

- 컴퓨터 구조 기초
- 컴퓨터의 구성
- 중앙처리장치(CPU) 작동 원리
- 캐시 메모리
- 고정 소수점 & 부동 소수점
- 패리티 비트 & 해밍 코드
- ARM 프로세서

## Questions

### 1. 시스템 소프트웨어와 응용 소프트웨어의 차이는?

> 시스템 소프트웨어란 컴퓨터의 하드웨어를 동작하고 데이터에 접근할 수 있도록 해주는 도구입니다. 시스템 구동에 필수적으로 요구되고 응용 소프트웨어를 실행할 수 있도록 플랫폼을 제공합니다.
>
> 응용 소프트웨어는 운영체제 위에서 실행되는 모든 소프트웨어입니다. 응용 소프트웨어의 예시로는 워드, 한글, 포토샵 등이 있습니다.

#### 운영체제란? (Operating System)

> 운영체제란 컴퓨터 하드웨어 바로 위에 설치되어 사용자 및 다른 모든 소프트웨어와 하드웨어를 연결하는 소프트웨어 계층입니다.

#### 컴파일러란? (Compiler)

> 컴파일러란 프로그래밍 언어로 작성된 소스 코드를 컴퓨터가 이해할 수 있는 기계어로 변환하는 소프트웨어 도구입니다.

### 2. 시스템 버스란?

> 시스템 버스란 컴퓨터의 4가지 핵심 장치인 CPU, 주기억장치, 보조기억장치, 입출력장치가 서로 정보를 주고받는 통로입니다. 주소 버스, 데이터 버스, 제어 버스로 구분됩니다.
>
> 주소 버스란 주소를 주고받는 통로로, CPU가 메모리나 입출력장치로 주소를 전달하기 때문에 단방향입니다.
>
> 데이터 버스란 명령어와 데이터를 주고받는 통로로, CPU가 메모리나 입출력장치와 명령어 및 데이터를 주고받기 때문에 양방향입니다.
>
> 제어 버스란 제어 신호를 주고받는 통로로, 메모리 읽기/쓰기, 인터럽트 요청/승인 등의 제어 신호를 통해 양방향으로 이루어집니다.

### 3. 명령어 처리 과정은?

> 명령어는 인출 → 해독 → 실행 순의 과정을 통해 처리가 이루어집니다. 명령어 처리는 크게 인출 사이클과 실행 사이클로 이루어집니다.
>
> 인출 사이클은 CPU가 다음에 실행할 명령어를 메모리에서 가져오는 과정입니다. PC에 저장된 데이터를 MAR로 가져오고, 저장된 데이터를 바탕으로 메모리의 해당 주소에서 명령어를 인출하여 MBR에 저장합니다. MBR에 저장된 내용을 IR에 전달하고, 다음 명령어 인출을 위해 PC 값을 증가합니다.
>
> 실행 사이클에서는 인출 사이클에 의해 IR에 저장된 명령어를 CPU가 실제로 해석하고 실행하는 과정입니다. 연산, 이동, 저장, 주소값 변경 등의 동작을 실행합니다.

### 4. 고정 소수점과 부동 소수점은?

> 고정 소수점이란 소수점이 찍힐 위치를 미리 정해놓고 소수를 표현하는 방식입니다. 부호비트, 정수부, 소수부로 이루어져 있고, 주로 리소스가 제한적이고 높은 정밀도가 필요하지 않은 환경에서 사용합니다.
>
> 부동 소수점은 지수 값에 따라 소수점이 움직이는 방식을 활용한 실수 표현 방식입니다. 부호비트, 지수부, 가수부로 이루어져 있고, 더 넓은 범위와 높은 정밀도를 필요로 하는 복잡한 계산에 적합합니다.

#### 장단점?

> 고정 소수점의 경우에는 단순한 정수 연산이기 때문에 연산 속도가 빠르고, 예측 가능한 계산 결과를 얻을 수 있다는 장점이 있습니다. 하지만 표현할 수 있는 수의 범위가 제한적이고, 오버플로우 및 언더플로우가 발생할 위험이 존재합니다.
>
> 부동 소수점의 경우에는 넓은 범위를 표현할 수 있다는 장점을 가지고 있지만, 반올림 오류가 발생할 수 있고 고정 소수점에 비해 느린 연산 속도를 갖는다는 단점이 존재합니다.

#### 고정 소수점 방식을 사용하는 경우는?

> 임베디드 시스템과 같이 메모리와 처리 능력이 제한된 환경에서 사용됩니다. 또한, 예측 가능한 실행 시간이 중요한 실시간 시스템이나, 비용이 민감한 하드웨어에서 사용됩니다.

### 5. RISC, CISC는?

> RICS란 CPU 명령어의 개수를 줄여 개발 명령어의 실행 속도를 빠르게 한 컴퓨터입니다. 명령어가 4바이트의 고정 길이를 가지고 있어 단순하고 처리 속도가 빠릅니다. 하지만 명령어의 종류가 적기 때문에 복잡한 기능을 수행하기 위해서는 여러 개의 명령어를 사용해야 합니다.
>
> CISC란 복잡한 집합을 갖는 CPU 아키텍쳐입니다. 명령어의 종류가 많기 때문에 명령어 하나로 복잡한 작업을 한꺼번에 처리할 수 있습니다. 하지만 명령어의 길이가 가변적이므로 복잡하고 처리 속도가 느립니다.

### 6. [파이프라이닝이란?](https://velog.io/@kio0207/%EC%BB%B4%ED%93%A8%ED%84%B0%EA%B5%AC%EC%A1%B0-%ED%8C%8C%EC%9D%B4%ED%94%84%EB%9D%BC%EC%9D%B4%EB%8B%9D)

> 파이프라이닝이란 CPU의 프로그램 처리 속도를 높이기 위해 CPU 내부 하드웨어를 여러 단계로 나누어 동시에 처리하는 기술입니다.

### 7. 캐시메모리란?

> 캐시 메모리란 장치 간 속도 차이에 따른 병목 현상을 줄이기 위한 메모리입니다. CPU가 주기억장치에서 저장된 데이터를 읽어올 때, 자주 사용하는 데이터를 캐시 메모리에 저장하여 다음 이용 시 캐시 메모리에 먼저 접근하면서 속도를 향상시킵니다.

#### 캐시 메모리의 작동 원리는?

> 캐시 메모리는 지역성의 원리에 따라 작동됩니다. 특히, 시간 지역성과 공간 지역성으로 분류됩니다. 시간 지역성이란 한 번 참조된 데이터는 잠시 후 다시 참조될 가능성이 높은 특성입니다. 공간 지역성이란 참조된 데이터 근처에 있는 데이터가 잠시 후 다시 사용될 가능성이 높은 특성입니다.

### 8. HDD와 SSD란?

> HDD(Hard Disk Drive)란 내부에 플래터라는 회전하는 디스크가 있고 그 위에 정보를 기록하고 읽는 장치입니다.
>
> SDD(Solid State Drive)란 플래시 메모리를 사용하여 구성된 저장 장치입니다. 각 셀에 기록된 정보를 읽고 쓰는 방식입니다.

#### HDD와 SSD의 차이?

> HDD는 대용량 데이터를 저렴한 비용으로 저장할 수 있다는 장점이 있습니다. 하지만 속도가 느리고, 충격에 취약하며, 소음이 발생할 수 있습니다.
>
> SDD는 빠른 속도, 충격에 강함, 소음이 없다는 장점이 있습니다. 하지만 가격이 비싸고, HDD에 비해 상대적으로 용량이 작다는 단점이 있습니다.

### 9. CPU란?

> CPU란 컴퓨터의 두뇌를 담당하는 장치로, 메모리에 저장된 명령어를 읽고, 해석하고, 실행하는 역할을 합니다. ALU, 레지스터, CU로 구성되어 있습니다. ALU는 비교와 연산을 담당하는 계산을 위해 존재하는 부품이고, 레지스터는 속도가 빠른 데이터 기억장소입니다. CU는 명령어의 해석과 실행을 담당하는 부품입니다.

### 10. GPU란? (Graphic Processing Unit)

> GPU란 수학 연산을 빠른 속도로 수행하는 전자 회로입니다. 그래픽 렌더링, 머신러닝, 비디오 편집 등의 작업을 수행하기 위해서는 대형 데이터 세트에 유사한 수학 연산을 적용해야 합니다. GPU는 다양한 데이터 값에 동일한 작업을 동시에 수행할 수 있도록 설계되었습니다.

#### GPU와 CPU의 차이?

> 일반적으로 CPU는 전체 시스템 제어와 관리 및 범용 작업을 처리합니다. GPU는 비디오 편집이나 머신러닝과 같은 컴퓨팅 집약적 작업을 처리합니다.

### 11. [명령어의 구조?](https://velog.io/@hyunji015/%EC%BB%B4%ED%93%A8%ED%84%B0-%EA%B5%AC%EC%A1%B0-%EB%AA%85%EB%A0%B9%EC%96%B4%EC%9D%98-%EA%B5%AC%EC%A1%B0)

> 명령어는 명령 코드와 오퍼랜드로 구성되어 있습니다. 명령 코드는 명령어가 수행할 연산을 의미하고, 오퍼랜드는 연산에 사용할 데이터 또는 연산에 사용할 데이터가 저장된 위치를 의미합니다.

### 12. [슈퍼스칼라란?](https://bitbrainblog.co.kr/entry/CPU%EC%9D%98-%EA%B5%AC%EC%A1%B0%EC%99%80-%EA%B8%B0%EB%8A%A5-%EA%B0%95%EC%A2%8C-%EC%8A%88%ED%8D%BC%EC%8A%A4%EC%B9%BC%EB%9D%BCSuperscalar-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98)

> CPU의 처리 속도를 더욱 높이기 위해 내부에 2개 이상의 명령어 파이프라인들을 포함시킨 구조 입니다. 매 클럭 주기마다 각 명령어 파이프라인이 별도의 명령어를 인출하여 동시에 실행할 수 있기 때문에, 이론적으로 프로그램 처리 속도가 파이프라인의 수만큼 향상 가능합니다.

#### 슈퍼스칼라 아키텍처의 장점은?

> 한 클록 사이클당 처리할 수 있는 명령어 수가 증가하기 때문에 높은 IPC(Instructions Per Cycle)을 가집니다. 이를 통해 전체적인 처리량이 향상됩니다.
>
> 다양한 유형의 명령어를 동시에 처리할 수 있어서 효율성이 높아집니다. 이를 통해 지원 활용도가 증가합니다.

### 13. 저장 장치 계층 구조란?

> 메모리 계층 구조는 크게 위에서부터 레지스터, 캐시, 메모리, 하드디스크로 분류됩니다.
>
> 아래로 갈수록 용량이 크고, 가격이 싸며, 속도가 느리고, 낮은 빈도를 갖습니다.

#### 레지스터와 캐시 메모리의 차이점은?

> 레지스터와 캐시 메모리는 둘 다 CPU가 연산을 하기 위해 명령어와 데이터를 저장하는 공간입니다.
>
> 레지스터는 CPU가 가장 빠르게 접근이 가능한 저장 공간이며, CPU 내부에 위치합니다.
>
> 캐시 메모리는 메모리 접근 속도를 향상시키기 위해 중간 버퍼 역할을 하는 저장공간입니다. 일반적으로 L1 캐시와 L2 캐시는 코어 내부에 위치하며, L3 캐시는 코어 외부에 위치합니다.

### 14. 컴파일러와 인터프리터?

> 컴파일러는 프로그램 전체를 스캔하여 이를 모두 기계어로 번역합니다. 인터프리터는 프로그램 실행 시 한 번에 한 문장씩 번역합니다. 컴파일러는 실행 전 오류를 발견할 수 있고, 인터프리터는 프로그램을 실행해봐야지만 오류 발견이 가능합니다.

#### 장단점

> 컴파일러는 초기 스캔을 마치면 실행 파일을 만들어 놓고 다음 실행 시 이전 만들어 놓았던 실행 파일을 실행하기 때문에 인터프리터보다 전체 실행 시간이 빠르다는 장점을 가집니다. 하지만 인터프리터보다 많은 메모리를 사용하고, 초기 스캔 시간이 오래 걸린다는 단점이 있습니다.
>
> 인터프리터는 컴파일러에 비해 메모리 효율이 좋습니다. 하지만 컴파일러보다 실행 시간이 더 걸린다는 단점이 있습니다.

### 15. 주기억장치와 보조기억장치의 차이점은?

> 주기억장치는 컴퓨터 내부에서 현재 CPU가 처리하고 있는 내용을 저장하는 기억장치입니다.
>
> 보조기억장치는 물리적인 디스크가 연결되어 있는 기억장치입니다.

#### RAM과 HDD의 차이는?

> RAM은 주기억장치로 읽고 쓰기가 가능하며, 응용 프로그램이나 OS 등을 불러와 CPU가 작업할 수 있도록 하는 기억장치입니다. 전원이 끊어지면 데이터가 전부 지워지기 때문에 휘발성 메모리입니다.
>
> HDD는 보조기억장치로 물리적인 디스크를 고속으로 회전시켜 데이터를 저장하는 장치입니다. 전원이 끊어지더라도 저장된 데이터가 사라지지 않고 영구적으로 보관 가능합니다.

### 16. 입출력장치의 역할은?

> 입출력장치는 컴퓨터 외부에 연결되어 컴퓨터 내부와 정보를 교환하는 장치입니다.

#### 입출력장치는 주기억장치와 어떻게 연결되나요?

> 입출력장치는 주기억장치와 버스를 통해 연결됩니다. I/O 컨트롤러를 통해 CPU 및 주기억장치와 통신을 합니다. 또한, DMA 컨트롤러를 통해 CPU를 거치지 않고 주기억장치와 직접 통신을 할 수 있습니다.

### 17. 메모리 버스와 PCIe 버스의 차이점은?

> 메모리 버스는 CPU와 메모리 간 데이터 전송에 사용됩니다. PCIe 버스는 입출력 장치와 CPU 및 메모리 간 데이터 전송에 사용됩니다.
>
> 메모리 버스는 병렬 방식으로 동작하며 메모리의 주소, 데이터, 제어 신호를 전달합니다. PCIe 버스는 직렬 장식으로 동작하며, 각 레인이 독립적으로 동작하여 효율적으로 대역폭을 사용합니다.

### 18. 캐시 일관성이란?

> 캐시 일관성이란 멀티 코어 환경에서 메모리 내용을 갱신하게 되는 경우, 이 사실을 다른 코어들에게 전달하고 각 코어의 기존 데이터를 무효화하는 과정을 구현한 것입니다.

### 19. 파이프라인 해저드란?

> 파이프라인 해저드란 명령어 파이프라인이 CPU 성능 향상에 실패하는 경우입니다. 구조적 해저드, 데이터 해저드, 제어 해저드의 종류가 있습니다.
>
> 구조적 해저드란 하드웨어가 여러 명령들의 수행을 지원하지 않아 발생한 자원 충돌 경우입니다.
>
> 데이터 해저드는 명령어 간의 의존성에 의해 발생하는 경우입니다.
>
> 제어 해저드란 jump와 같은 분기 명령어에 의해 발생하는 경우입니다.

#### 파이프라인 해저드를 해결하는 방법은?

> 구조적 해저드의 경우는 HW 및 리소스를 추가하여 메모리에 동시에 접근하고, 데이터와 명령어의 메모리를 분리하므로써 해결이 가능합니다.
>
> 데이터 해저드의 경우는 NOP 명령어를 삽입하여 컴파일러 수준에서 해저드를 발견하고, 접근 중인 데이터와 관련 없는 명령어를 삽입해서 비순차 실행을 하므로써 해결이 가능합니다.
>
> 제어 해저드의 경우에는 명령이 분기하는지 미리 예측하고, 조건 분기를 최소화하므로써 해결이 가능합니다.

### 20. 인터럽트란?

> 인터럽트란 프로세스 실행 도중 예기치 않은 상황이 발생할 때 발생한 상황을 처리한 후 실행 중인 작업으로 복귀하는 것을 의미합니다.

#### 하드웨어 인터럽트와 소프트웨어 인터럽트의 차이점은?

> 하드웨어 인터럽트란 통상적인 인터럽트로, 컨트롤러 등 하드웨어 장치가 발생시킨 인터럽트입니다.
>
> 소프트웨어 인터럽트란 트랩이라고도 불리며, 주로 Exception이나 System Call과 같은 상황에서 발생하는 인터럽트입니다.
>
> Exception은 사용자 프로그램이 비정상적인 작업을 시도하거나, 자신의 메모리 영역 바깥에 접근하려는 시도 등 권한이 없는 작업을 시도할 때 이에 대한 처리를 위해 발생시키는 인터럽트입니다.
>
> 시스템 콜은 사용자 프로그램이 OS 내부에 정의된 코드를 실행하고 싶을 때 OS에 서비스를 요청하는 방법입니다. 예를 들어 프로그램 작성 중 키보드 입력이나 화면 출력 등의 입출력 작업이 필요한 경우, 이미 존재하는 커널의 코드를 호출하여 처리합니다.

#### ISR(Interrupt Service Routine)이란?

> ISR이란 해당 인터럽트가 발생한 경우 처리해야 할 일의 절차입니다.
>
> 주 프로그램 작업 수행 중 인터럽트가 발생하면 주 프로그램 상태 레지스터와 PC 등을 스택에 잠시 저장하고 ISR로 점프 후 처리합니다. 처리가 완료되면 다시 주 프로그램 작업으로 복귀하여 작업을 수행합니다.

### 21. 가상 메모리란?

> 가상 메모리란 OS에서 실제 물리 메모리보다 큰 주소 공간을 프로세스에 제공하기 위해 사용되는 기술입니다. 프로세스와 관련된 주소값을 저장하는 메모리 공간을 분리하여 관리하므로써, 컴퓨터의 메모리가 실제 메모리보다 많아 보이게 하는 기술입니다.

### 22. 플래그 레지스터(Flag Register)란?

> 플래그 레지스터란 산술 연산 결과의 상태를 보여주는 flag bit들이 모인 레지스터입니다.
> 부호 플래그, 제로 플래그, 캐리 플래그, 오버플로우 플래그, 인터럽트 플래그, 슈퍼바이저 플래그가 있습니다.
>
> 부호 플래그란 연산 결과의 부호를 나타냅니다.  
> 제로 플래그란 연산 결과가 0인지 여부를 나타냅니다.  
> 캐리 플래그란 연산 결과에 올림수나 발림수가 발생했는지를 나타냅니다.  
> 오버플로우 플래그란 오버 플로우가 발생했는지를 나타냅니다.  
> 인터럽트 플래그란 인터럽트가 가능한지를 나타냅니다.  
> 슈퍼바이저 플래그란 커널 모드로 실행 중인지, 사용자 모드로 실행 중인지를 나타냅니다.   
