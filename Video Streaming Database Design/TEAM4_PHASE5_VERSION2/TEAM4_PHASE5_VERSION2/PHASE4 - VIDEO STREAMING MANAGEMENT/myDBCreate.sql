CREATE TABLE F21_S003_4_Content
(
ContentId VARCHAR(10),
Episodes  int not null,
Genre VARCHAR(30) not null,
ReleaseDate Date not null,
Duration NUMBER(10) not null,
Origin varchar(30) not null,
Title varchar(30) not null,
PRIMARY KEY (ContentId)
);

CREATE TABLE F21_S003_4_Customers
(
CustomerId Varchar(10),
ContactNumber INT,
Country varchar(30),
Email varchar(30) NOT NULL,
DateOfBirth Date NOT NULL,
PRIMARY KEY (CustomerId)
);


CREATE TABLE F21_S003_4_Subscription
(
SubscriptionId varchar(10),
CustomerId  varchar(10), 
SubscriptionStartDate Date,
foreign key (CustomerId) references   F21_S003_4_Customers(CustomerId)
ON DELETE CASCADE,
primary key(SubscriptionId,CustomerId)
);

create table F21_S003_4_Devices
(
DeviceId  varchar(10),
DeviceType varchar(10),
IpAddress varchar(15),
CustomerId varchar(10),
foreign key (CustomerId) references   F21_S003_4_Customers(CustomerId)
ON DELETE CASCADE,
primary key(DeviceId)
);

create table F21_S003_4_Stream
(
StreamId varchar(10),
ContentId varchar(10),
CustomerId varchar(10),
StreamRate Decimal(2,1),
StreamLength NUMBER(10),
primary key(StreamId),
foreign key (CustomerId) references   F21_S003_4_Customers(CustomerId)
ON DELETE CASCADE,
foreign key (ContentId) references   F21_S003_4_Content(ContentId)
ON DELETE CASCADE
);

create table F21_S003_4_LogInfo
(
LogId int,
LoggedInTime TIMESTAMP,
LoggedOutTime TIMESTAMP,
CustomerId varchar(10),
DeviceId varchar(10),
foreign key (CustomerId) references   F21_S003_4_Customers(CustomerId)
ON DELETE CASCADE,
foreign key (DeviceId) references   F21_S003_4_Devices(DeviceId)
ON DELETE CASCADE,
primary key(LogId)
);


create table F21_S003_4_Content_Language
(
Content_Id varchar(10),
Language varchar(10),
primary key(Content_Id,Language),
foreign key (Content_Id) references   F21_S003_4_Content(ContentId) ON DELETE CASCADE
);
create table F21_S003_4_Device_DeviceName
(
Device_Id varchar(10),
DeviceName varchar(30),
primary key(Device_Id,DeviceName)
);