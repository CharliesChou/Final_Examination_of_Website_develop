package com.charles.idol.mapper;
import java.util.HashMap;
import java.util.List;
/*
 * <mapper namespace="com.charles.idol.mapper.NoticeMapper">
	<insert id="postNotice" parameterType="com.charles.idol.pojo.Notice">
		insert into notice values(#{sender},#{content},#{sendtime})
	</insert>
	<delete id="delNoticeById" parameterType="HashMap">
		delete from notice where noticeid=#{noticeid}
	</delete>
	<select id="getAllNotice" resultType="com.charles.idol.pojo.Notice" parameterType="HashMap">
		select * from notice LIMIT #{pageNo},#{pageSize}
	</select>
	<update id="updateNoticeById" parameterType="HashMap">
		update notice set content=#{content} where noticeid=#{noticeid}
	</update>
 * */
import com.charles.idol.pojo.Notice;
public interface NoticeMapper {
	public boolean  postNotice(Notice notice);
	public boolean delNoticeById(HashMap<String,String> id);
	public List<Notice> getAllNotice(HashMap<String,Integer> page);
	public boolean updateNoticeById(HashMap<String,String> para);
}
