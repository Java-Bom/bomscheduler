# 기능명세서

1. 하나의 스케쥴 작업을 하나의 인스턴스가 실행할 수 있도록 보장해야한다.
2. 작업에 대한 TTL이 존재하여 일정시간 동안 수행된 이력이 없다면 다른 인스턴스에게 권한을 빼앗길 수 있다.
3. 스케쥴 작업을 주기적으로 DB에 등록하는 작업이 존재한다.
   1. v2. 위 작업을 스위치하는 기능이 필요하다.
4. 스케쥴 작업은 라이브러리에 정의한 어노테이션을 이용한다.
   1. v2. api로 작업을 제어할 수 있다.


---

# 클래스 역할 설계

- Job
   - 작업의 메타데이터
      - 작업의 주소 → 이름
      - 실행 주체에 대한 정보
         - TTL
      - 실행 주체의 만료시간
      - 버전정보
      - 마지막 실행 시간
   - 실행 주체에 대한 정보는 언제 변경되어야 하는가?
      - 최초로 Job이 등록될 때
      - Task가 정상적으로 수행됐을 때 만료시간의 갱신된다.
         - 만료시간이 넘어갔을 때 실행주체가 변경이 가능해진다.
- Task
   - 작업이 실행될 때 생성된다.
      - 작업이 종료되면 제거된다.
   - 작업의 실행 정보
      - Job의 참조
      - 작업의 실행 시작 시간
      - 작업을 실행중인 주체의 정보
   - 작업이 완료됐음을 알려줄 수 있는 콜백을 담당
      - 완료 시 Task 삭제
      - Job의 만료시간을 갱신
      - ex. Task.callBack {

        taskManager.complete()

        jobManager.refresh()

        taskRunner.refresh()


        }

- BomSchedule
   - 작업 정의 어노테이션
- JobManager
   - pending된 Job의 갱신
      - 만료시간이 지나면 실행주체 정보를 바꿔준다.
- JobCollection
   - Job에 대한 참조를 가지고 있다.
- TaskManager
   - 멤버변수
      - 각 Job의 마지막 실행시간
         - Map<Job, MetaData>
   - Job 메타데이터와 JobCollection를 참조하여 Task를 주기적으로 생성한다.
      - TaskManager에 이미 있으면 생성하지 않아야 한다.
      - Task의 마지막 실행시간과 스케쥴 시간을 계산해서 생성 가능한지 확인
   - JobCoordinator에게 Task를 읽어와서 TaskManager에게 전달
- JobCoordinator
   - 인프라 스트럭처와의 통신을 담당
- TaskRunner
   - Task를 실행할 수 있는 스레드풀 관리
   - 현재 실행가능한 Task의 목록을 알고있다.
   - 현재 실행가능한 Task를 소비한다.
   - 그레이스풀 셧다운
      - 조건 : 현재 메모리에 들고있는 task가 없다면 종료
      - 바로 메모리에서 제거하고 실행하면
         - task 실행중인데 없다고 판별할수 있다.
- BomScheduleInterceptor
   - TaskManager에게서 실행가능한 Task를 가져와서 실행이 가능한지 확인한다.
      - 현재 실행주체(1)와 Task에 정의된 실행주체(2)가 일치하는지 확인하고 pass
      - 확인후 Task의 작업완료 콜백 호출


---

# 스케쥴러 실행 플로우

1. 실행 시작 단계
   1. 코드에 정의된 메타데이터 수집
   2. 메타데이터중 신규 메타데이터만 Job에 등록
2. 실행 중 단계
   1. JobManger를 통해 pending Job을 뺏는다.
   2. TaskManager가 할당된 Job을 Task로 만들어 TaskRunner에게 발행한다.
3. 실행 종료 단계
   1. JobManager, TaskManager가 종료된다.
   2. TaskRunner의 Task는 전부 소진하고 종료