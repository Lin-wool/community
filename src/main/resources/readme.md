#table sql
##user
```sql
create table user
(
	id bigint auto_increment,
	account_id varchar(100) null comment 'GitHub等平台用户ID',
	name varchar(50) null comment '用户名',
	token char(36) null comment '用户登录得验证密钥',
	gmt_create bigint null comment '创建时间',
	gmt_modified bigint null comment '修改时间',
	bio varchar(256) null comment '用户签名，简单描述',
	avatar_url varchar(256) null comment '用户头像地址',
	constraint table_name_pk
		primary key (id)
);
```


##question
```sql
create table question
(
	id bigint auto_increment,
	title varchar(100) null comment '问题标题',
	description text null comment '问题描述',
	gmt_create bigint null comment '创建时间',
	gmt_modified bigint null comment '修改时间',
	creator bigint null comment '创建人',
	comment_count bigint default 0 null comment '评论数',
	view_count bigint default 0 null comment '浏览数',
	like_count bigint default 0 null comment '喜欢数',
	dislike_count bigint default 0 null comment '不喜欢数',
	tag varchar(256) null comment '标签',
	constraint question_pk primary key (id)
);
```
##comment
```sql
create table comment
(
	id bigint auto_increment,
	parent_id bigint not null comment '评论的问题ID',
	type int null comment '评论的级别 1：问题回复 2：评论回复',
	commentator bigint null comment '评论人ID',
	gmt_create bigint null comment '创建时间',
	gmt_modified bigint null comment '修改时间',
	like_count bigint default 0 null comment '点赞数',
	constraint comment_pk primary key (id)
)
comment '问题评论表';
```

##mybatis generator脚本执行语句
````
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
````
