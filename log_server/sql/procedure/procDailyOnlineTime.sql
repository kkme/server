use sevensoul_log;
delimiter //
/*
统计某天的登陆时长统计
    参数说明：
        cal_date:统计的业务日期，格式如：‘2012-11-11’，若为空字符，则默认统计当天的数据
        redo:是否重新统计该天的数据
*/
drop procedure if exists `procDailyOnlineTime`//
create procedure procDailyOnlineTime(cal_date VARCHAR(11),redo tinyint(1))
begin
    declare tbl_name VARCHAR(30);
    declare date_str VARCHAR(20);
    if length(cal_date)=0 then 
         set cal_date = date_format(sysdate(),'yyyy-MM-dd');         
    end if;
    if(redo=1) then
        delete from user_online_time where biz_date=cal_date;
    end if;
    select @flag:= count(id) from user_online_time where biz_date=cal_date;
    if(@flag=0) then
        set date_str = replace(cal_date,'-','_');
        set tbl_name = concat('basic_player_log_',date_str); 
        
        set @temp_sql = concat("insert into user_online_time (account_id,account_name,char_id,char_name,online_time,biz_date,create_time) select account_id,
            account_name,char_id,char_name,sum(online_time/60000),'",cal_date,"',now() from ",tbl_name," group by char_id");
        prepare cmd from @temp_sql; 
        execute cmd;
        deallocate prepare cmd;
    end if;
end
//
delimiter ;

