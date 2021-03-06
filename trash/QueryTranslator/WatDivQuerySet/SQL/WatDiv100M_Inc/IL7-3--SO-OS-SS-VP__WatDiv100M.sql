SELECT tab1.v1 AS v1 , tab0.v0 AS v0 , tab5.v5 AS v5 , tab6.v7 AS v7 , tab4.v4 AS v4 , tab6.v6 AS v6 , tab3.v3 AS v3 , tab2.v2 AS v2 
 FROM    (SELECT obj AS v7 , sub AS v6 
	 FROM sorg__author$$7$$
	) tab6
 JOIN    (SELECT sub AS v5 , obj AS v6 
	 FROM wsdbm__likes$$6$$
	) tab5
 ON(tab6.v6=tab5.v6)
 JOIN    (SELECT obj AS v5 , sub AS v4 
	 FROM wsdbm__friendOf$$5$$
	
	) tab4
 ON(tab5.v5=tab4.v5)
 JOIN    (SELECT obj AS v4 , sub AS v3 
	 FROM rev__reviewer$$4$$
	
	) tab3
 ON(tab4.v4=tab3.v4)
 JOIN    (SELECT obj AS v3 , sub AS v2 
	 FROM rev__hasReview$$3$$
	
	) tab2
 ON(tab3.v3=tab2.v3)
 JOIN    (SELECT sub AS v1 , obj AS v2 
	 FROM gr__includes$$2$$
	) tab1
 ON(tab2.v2=tab1.v2)
 JOIN    (SELECT obj AS v1 , sub AS v0 
	 FROM gr__offers$$1$$
	) tab0
 ON(tab1.v1=tab0.v1)


++++++Tables Statistic
wsdbm__likes$$6$$	2	OS	wsdbm__likes/sorg__author
	VP	<wsdbm__likes>	1124672
	SO	<wsdbm__likes><wsdbm__friendOf>	1124672	1.0
	OS	<wsdbm__likes><sorg__author>	105725	0.09
------
gr__offers$$1$$	0	VP	gr__offers/
	VP	<gr__offers>	1420053
	OS	<gr__offers><gr__includes>	1420053	1.0
------
sorg__author$$7$$	1	SO	sorg__author/wsdbm__likes
	VP	<sorg__author>	40060
	SO	<sorg__author><wsdbm__likes>	38009	0.95
------
rev__reviewer$$4$$	2	OS	rev__reviewer/wsdbm__friendOf
	VP	<rev__reviewer>	1500000
	SO	<rev__reviewer><rev__hasReview>	1476843	0.98
	OS	<rev__reviewer><wsdbm__friendOf>	601949	0.4
------
gr__includes$$2$$	2	OS	gr__includes/rev__hasReview
	VP	<gr__includes>	900000
	SO	<gr__includes><gr__offers>	432735	0.48
	OS	<gr__includes><rev__hasReview>	179741	0.2
------
rev__hasReview$$3$$	1	SO	rev__hasReview/gr__includes
	VP	<rev__hasReview>	1476843
	SO	<rev__hasReview><gr__includes>	1437537	0.97
	OS	<rev__hasReview><rev__reviewer>	1476843	1.0
------
wsdbm__friendOf$$5$$	2	OS	wsdbm__friendOf/wsdbm__likes
	VP	<wsdbm__friendOf>	45092208
	SO	<wsdbm__friendOf><rev__reviewer>	13968837	0.31
	OS	<wsdbm__friendOf><wsdbm__likes>	10736619	0.24
------
