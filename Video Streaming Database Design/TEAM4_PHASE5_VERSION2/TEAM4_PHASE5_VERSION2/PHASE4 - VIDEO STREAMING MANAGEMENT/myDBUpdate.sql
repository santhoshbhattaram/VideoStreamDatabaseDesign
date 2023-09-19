------ 1. Update the Genre of movie Pushpa(Content Id : C_00001) to Action -------
update F21_S003_4_Content
set genre='Action'
where ContentId='C_00001';


--------2.Update  Language (Spanish)  to French-------------------------

update F21_S003_4_Content_Language
set Language='French'
where Language='Spanish';

------3. Update origin (India) to Russia ---------

update F21_S003_4_Content
set Origin='Russia'
where Origin='India';

------4. Update Country (India) to Russia-------

update F21_S003_4_Customers
set Country='Russia'
where Country='India';


