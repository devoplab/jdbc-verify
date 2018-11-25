#!/bin/sh

###########################################################################################
# Base on https://github.com/oracle/docker-images/tree/master/OracleDatabase/SingleInstance
###########################################################################################

if [ "$EUID" -ne 0 ]
  then  cat << EOF

Please run as sudo or root

 * This script needs root permission to create a folder in /opt/oracle/oradata
 * and change owner to 54321:54321.

EOF
  exit
fi


#################################################
## Create the data volume that Oracle will use
#################################################

ORACLUID=54321

DBNAME=ORCLSE2

DBPASSWD=password

echo "########## Creating this Oracle container's data volume on host ################"

DATAVOL=/opt/oracle/oradata/${DBNAME}

mkdir -p $DATAVOL

chown -R ${ORACLUID}:${ORACLUID} $DATAVOL

echo "********** Order container's data volume ready. Starting docker now ************"


##################### Create Mount Point ###################################
##################### Different between SELinux host and not ###############
# Check if host is enfocing SELinux
SELINUXSTATUS=$(getenforce)

if [ "$SELINUXSTATUS" == "Enforcing" ]
then 
    # SELinux enforced. Need to use Z on the volume mount
    VOLMOUNT=${DATAVOL}:/opt/oracle/oradata:Z
else
    # SELinux not enforced. Mount the volume as is
    VOLMOUNT=${DATAVOL}:/opt/oracle/oradata
fi


docker run --name orcl18se \
-p 21521:1521 -p 25500:5500 \
-e ORACLE_SID=${DBNAME} \
-e ORACLE_PDB=ORCLPDB \
-e ORACLE_PWD=${DBPASSWD} \
-e ORACLE_CHARACTERSET=AL32UTF8 \
-v ${VOLMOUNT} \
oracle/database:18.3.0-se2
