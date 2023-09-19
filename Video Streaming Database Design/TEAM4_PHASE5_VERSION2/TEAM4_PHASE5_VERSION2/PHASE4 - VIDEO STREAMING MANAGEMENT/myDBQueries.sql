--1. To know the interest of customers, we shall generate a report of viewership of each Genre videos in a specific language, 
--so that we can release similar content in that language with atleast 10 streams(Report order by most viewed Genre).
-----first query Business Goal--------


select con.Genre as Genre,lan.Language, Count(st.StreamId) as ViewershipRate
from F21_S003_4_Content con
inner join
F21_S003_4_Content_Language lan
on lan.Content_Id=con.ContentId
inner join F21_S003_4_Stream st
on st.ContentId=con.ContentId
group by con.genre,lan.language
having  Count(st.StreamId)>=10
order  by ViewershipRate desc;



---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--Business Goal 2-------------
---Generate a report of most viewed(top 5) foreign content(user watching other than native originated content)  in each country for the past year; 
----to identify the interest of local customers towards different region content, to further release similar content.(top 5)



select Origin as ForeignCountry,Country as UserRegion,title as ForeignContent,count(st.streamId) as StreamCount from
F21_S003_4_Stream st
inner join 
F21_S003_4_Content con
on st.ContentId=con.ContentId
inner join 
F21_S003_4_Customers cus
on cus.CustomerId=st.CustomerId
where 
st.ContentId in
(
select c.ContentId from F21_S003_4_Content c where cus.Country<>Origin
)
group by Country,Origin,Title
order by count(st.streamId) desc fetch first 5 rows only;


--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

---Business Goal 3----
--Generate yearly report of total customers, who didn’t renew the subscription with respect to each region/country; so that more strategies can be employed to increase the sales (Total & Each Region).



select Country,count(sub.CustomerId) as InactiveRenewal from 
F21_S003_4_Subscription sub
inner join 
F21_S003_4_Customers cus
on sub.CustomerId=cus.customerId
where 
sysDate-sub.SubscriptionStartDate>365
group by rollup(cus.Country) order  by count(sub.CustomerId) desc;


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


---business Goal 4---
--Generate a report of Total views of each genre in each country; based on which we can know the interests of the subscribers

select Country,con.Genre as Genre,Count(st.StreamId) as TotalViews from
F21_S003_4_Content con
inner join 
F21_S003_4_Stream st
on st.ContentId=con.ContentId
inner join 
F21_S003_4_Customers cus 
on cus.CustomerId=st.CustomerId
group by con.genre,Country
order  by TotalViews desc;

--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


--- Business Goal 5 Top viewed title in each genre---- 
----Generate a report of top viewed content(title) in each genre 


with cte as(
select  StreamCount,Genre,Title, rank() OVER (PARTITION BY genre ORDER BY StreamCount desc)as  Rank from( 
select 
Distinct count(st.StreamId) over (partition by con.genre,con.Title)as StreamCount,
con.Genre as Genre,con.Title
from F21_S003_4_Content con
inner join F21_S003_4_Stream st
on st.ContentId=con.ContentId))
select Genre,Title,StreamCount from cte where rank=1 order by StreamCount desc ;


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

--- business goal 6----

--To avoid servers getting hung, maintaining infrastructure scalability of servers for smooth video streaming and good quality; we will get the analysis of active devices logged per year


select 
Distinct extract(Year from LoggedInTime) as Year,
count(li.DeviceId) over (partition by extract(Year from LoggedInTime)) as LogCount
from 
F21_S003_4_LogInfo li
inner join 
F21_S003_4_Devices dev 
on
li.DeviceId=dev.DeviceId;
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


----business Goal 7---
--We will get the report of which type of device the users are using to watch /subscribe. With this data we shall know user with what age is comfortable with which kind of device.  (0-18 define as Teen, 19-45 as Adult,
-->45 as Senior Adults)



select DeviceType,AgeGroup,count(DeviceType) as DeviceCount from 
(select  dev.DeviceType
,case when  trunc(months_between(sysdate,cus.DateofBirth)/12) <=18
then 
'Teen Age'
when  trunc(months_between(sysdate,cus.DateofBirth)/12) <=45 and trunc(months_between(sysdate,cus.DateofBirth)/12) >18 then
'Adults'
when trunc(months_between(sysdate,cus.DateofBirth)/12) >45 then
'Senior Adults'   end as AgeGroup
from
F21_S003_4_Devices dev 
inner join
F21_S003_4_Customers cus
on
dev.CustomerId=cus.CustomerId)
group by DeviceType,AgeGroup
order by DeviceCount desc;

----------------------------------------------------------------------------------------------------------------------------------------------------------



---------------business goal 8 ---------------------------------------------------
--Generate a report of Total views of each genre based on age groups; to know the tastes of different sections of people so that similar videos of their interest can be released.

select genre,AgeGroup,count(streamId) as ViewerShipRate from 
(select str.streamId ,con.genre
,case when  trunc(months_between(sysdate,cus.DateofBirth)/12) <=18
then 
'Teen Age'
when  trunc(months_between(sysdate,cus.DateofBirth)/12) <=45 and trunc(months_between(sysdate,cus.DateofBirth)/12) >18 then
'Adults'
when trunc(months_between(sysdate,cus.DateofBirth)/12) >45 then
'Senior Adults'   end as AgeGroup
from
F21_S003_4_Stream str
inner join
F21_S003_4_Customers cus
on
str.CustomerId=cus.CustomerId
inner join 
F21_S003_4_Content con
on con.contentId = str.contentId
)
group by genre,AgeGroup
order by ViewerShipRate  desc;
----------------------------------------------------------------------------------------------------------------------------------------------------------


-----------------------business goal 9-------------------------------------------------------------------------
--Generate a report of active users  who uses more than 2 different devices; to further restrict number of devices based on the analysis

select cus.CustomerId as CustomerId,Email as UserName,count(DeviceId) as DeviceCount
from F21_S003_4_Devices dev
inner join
F21_S003_4_Subscription sub
on sub.CustomerId=dev.customerId
inner join 
F21_S003_4_Customers cus 
on cus.CustomerId=sub.CustomerId
where sysDate-sub.SubscriptionStartDate<365
group by cus.CustomerId,Email
having count(DeviceId) >2;

-------------------------------------------------------------------------------------------------------------------------------------------------------------
----------business goal 10----------------
--Generate a report of ratings of each genres, each movie in particular genre & total rating of all movies(avg of all user rating) based on user’s rating

select 
genre,Title,avg(StreamRate) as AverageRating
from F21_S003_4_Content con
inner join F21_S003_4_Stream st
on st.ContentId=con.ContentId
group by rollup(con.genre,Title);

-------------------------------------------------------------------------------------------------------------------------------------------------------------
--Business Goal 11
--Generate a report of viewership rate of each language, genre and both language and genre


select con.Genre as Genre,lan.Language, Count(st.StreamId) as ViewershipRate
from F21_S003_4_Content con
inner join
F21_S003_4_Content_Language lan
on lan.Content_Id=con.ContentId
inner join F21_S003_4_Stream st
on st.ContentId=con.ContentId
group by cube(con.genre,lan.language);