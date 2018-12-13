Group By的使用方法
======
** group by字面的意思就是按照一定的规则分组**，需要掌握的有以下几点：

* group by与聚合函数
* group by与Having
* 特别注意

group by与聚合函数
------
group by一般与聚合函数一起使用。这是因为group by之后结果集就变成了多个分组，而每个分组可能包含多条记录，要想针对每个分组进行操作，所以必须使用可以作用在多条记录上的聚合函数。比如，下面的示例就使用了group by和聚合函数查询每个组的总记录数，sql语句如下。
```sql
select s_id ,count(*)  from score group by s_id
```
group by与Having
------
要对分组后的结果进行筛选，一般使用having语句来实现。
```sql
select s_id ,count(*)  from score group by s_id having count(*)>2
```
特别注意
------

* **select语句指定的字段要么包含在group by语句中作为分组的依据；要么就要被包含在聚合函数中。**
* 注意where与having的区别，where是在分组前对结果集进行过滤，而having是对group by分组后的结果集进行过滤。
