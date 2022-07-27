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