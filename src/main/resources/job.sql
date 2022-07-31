#------------------------------------------------------------------------------
#-- Table 명 : job_alloc (작업 할당)
#------------------------------------------------------------------------------
CREATE TABLE job_alloc
(
    id                         bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    job_name                   varchar(32) NOT NULL COMMENT '작업명',
    job_type                   varchar(32) NOT NULL COMMENT '작업타입(SINGLE)',
    alloc_id                   varchar(32) NOT NULL COMMENT '작업할당ID',
    alloc_system               varchar(32) NOT NULL COMMENT '작업할당시스템',
    alloc_start_time           datetime    NOT NULL COMMENT '할당시작시간',
    alloc_end_time             datetime    NOT NULL COMMENT '할당종료시간',

    CONSTRAINT pk_job_alloc PRIMARY KEY (id),
    CONSTRAINT uk_job_alloc UNIQUE KEY (job_name)
) ENGINE = InnoDB COMMENT ='작업 할당';

#------------------------------------------------------------------------------
#-- Table 명 : job_alloc_his (작업 할당 이력)
#------------------------------------------------------------------------------
CREATE TABLE job_alloc_his
(
    id                         bigint      NOT NULL AUTO_INCREMENT COMMENT 'ID',
    job_name                   varchar(32) NOT NULL COMMENT '작업명',
    alloc_id                   varchar(32) NOT NULL COMMENT '작업할당ID',
    alloc_system               varchar(32) NOT NULL COMMENT '작업할당시스템',
    alloc_start_time           datetime    NOT NULL COMMENT '할당시작시간',
    alloc_end_time             datetime    NOT NULL COMMENT '할당종료시간',

    CONSTRAINT pk_job_alloc_his PRIMARY KEY (id),
    INDEX idx_job_alloc_his (alloc_end_time)
) ENGINE = InnoDB COMMENT ='작업 할당 이력';





/*
1.스케줄러 작업의 고가용성
2.특정시간에 반드시 돌아야 하는 작업을 체킹/실행
*/


CREATE TABLE job_sequences (
                                sequence_name               varchar(  255)          NOT NULL COMMENT '시퀀스명',
                                last_sequence                    bigint                  NOT NULL COMMENT '시퀀스값',
                                job_alloc_id                bigint                  NOT NULL COMMENT '시퀀스값',

                                CONSTRAINT pk_job_sequences PRIMARY KEY (sequence_name),
                                index fk_job_sequences(job_alloc_id)
) ENGINE=InnoDB COMMENT='시퀀스 테이블';


/*

모니터링 스레드
job_alloc의 만료시간을 확인하고 같은 인스턴스일 경우 연장, 다른인스턴스일 경우 작업수행자 변경
alloc_id 조회했을때 없다면 신규작업 생성

작업 스레드
각 인스턴스별로 메시지 발행 작업 job_alloc에 job_alloc_detail을 추가생성한다.
메시지 발행 테이블 마지막 pk를 조회 pk값에 +1000 job_sequences의 last_sequence를 업데이트한다.
job_alloc_detail에 마지막 sequence를 기록 sequence사이값의 id를 메시지 발행처리
새로 인스턴스가 시작될때 만약 job_alloc_detail이 이미 있다면 마지막 시퀀스와 시작 시퀀스보다 작은 미발행 메시지만 조회해서 실행한다.

하우스키핑 스레드
리더 스레드는 하나의 인스턴스에만 존재하고 job_alloc에서 하우스키핑 단일작업을 부여받은 인스턴스만 동작한다.
하우스키핑 스레드는 job_alloc에서 멈춘 작업(스케일 인/비정상 종료작업/블루그린배포)을 탐색해 미실행 작업을 마무리하고 삭제처리한다.


*/