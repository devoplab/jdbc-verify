# JDBC Verify

This java console application provides users a quick way to verify jdbc connection and quickly recognize connectivity issues due to network or configuraiton.

## Build

Use ```maven install`` in the jdbcverify project to build the application in the *target/jdbcverify* directory. The directory can be zipped and put on the server that needs verification.

## Run

Symbolically link your jdbc driver into the lib directory before run ```jdbcverify.sh```. So the application can find the jdbc driver.

```console
[nzhang@oc6541620688 jdbcverify]$ cd lib
[nzhang@oc6541620688 jdbcverify]$ pwd
/home/nzhang/workspace/jdbc-verify/jdbcverify/target/jdbcverify/lib
[nzhang@oc6541620688 lib]$ ln -s ~/installer/development/jdbc/ojdbc8.jar
[nzhang@oc6541620688 lib]$ ls -lh
total 360K
-rw-r--r--. 1 nzhang users 118K Nov 24 14:40 junit-3.8.2.jar
lrwxrwxrwx. 1 nzhang users   50 Nov 24 15:08 ojdbc8.jar -> /home/nzhang/installer/development/jdbc/ojdbc8.jar
-rw-r--r--. 1 nzhang users 240K Nov 24 14:40 picocli-3.8.0.jar
```

An example of the running the application:

```console
[nzhang@oc6541620688 jdbcverify]$ pwd
/home/nzhang/workspace/jdbc-verify/jdbcverify/target/jdbcverify
[nzhang@oc6541620688 jdbcverify]$ ls -lh
total 16K
-rw-r--r--. 1 nzhang users 6.6K Nov 24 14:40 app.jar
-rwxr-xr-x. 1 nzhang users   76 Nov 24 14:48 jdbcverify.sh
drwxr-xr-x. 2 nzhang users 4.0K Nov 24 14:40 lib
[nzhang@oc6541620688 jdbcverify]$ cd lib
[nzhang@oc6541620688 jdbcverify]$ pwd
/home/nzhang/workspace/jdbc-verify/jdbcverify/target/jdbcverify/lib
[nzhang@oc6541620688 lib]$ ln -s ~/installer/development/jdbc/ojdbc8.jar 
[nzhang@oc6541620688 lib]$ ls -lh
total 360K
-rw-r--r--. 1 nzhang users 118K Nov 24 14:40 junit-3.8.2.jar
lrwxrwxrwx. 1 nzhang users   50 Nov 24 15:08 ojdbc8.jar -> /home/nzhang/installer/development/jdbc/ojdbc8.jar
-rw-r--r--. 1 nzhang users 240K Nov 24 14:40 picocli-3.8.0.jar
[nzhang@oc6541620688 lib]$ cd ..
[nzhang@oc6541620688 jdbcverify]$ ./jdbcverify.sh -c="jdbc:oracle:thin:@//locost:21521/ORCLSE2" -d="oracle.jdbc.driver.OracleDriver" -u=system
*******************************************
*    JDBC Verify     ~~~~~~~~~~~~~~~~~~~~~~
*******************************************
Please enter password [system]:password

Please eneter sql statment: Quit or Exit to exit
:select 'TEST' from dual;
< . -
=================================================================================
|'TEST'|
---------------------------------------------------------------------------------
|TEST|
=================================================================================
 * >
Please eneter sql statment: Quit or Exit to exit
:select * from help where rownum < 5;
< . -
=================================================================================
|TOPIC|SEQ|INFO|
---------------------------------------------------------------------------------
|@|1|null|
|@|2| @ ("at" sign)|
|@|3| -------------|
|@|4| Runs the SQL*Plus statements in the specified script. The script can be|
=================================================================================
 * >
Please eneter sql statment: Quit or Exit to exit
:quit 
===============================================
Bye
```