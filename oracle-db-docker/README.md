# Oracel Database Docker Container

This process follows the [Oracle Office source for Docker](https://github.com/oracle/docker-images).

## Build Image


### Prepare

Oracel installer requires 18G of free space to install and fails if the installation is on a default 10G container. 

Increased the container size to 20G in following steps:

1. create /etc/docker/daemon.json file to set dockerd startup option with basesize of 20G
```json
{
    "debug": false,
    "storage-opt": [
        {"dm.basesize": "20G"}
    ]
}
```
2. **===============WARNING===============** ***!!!You are going to lose all of your containers in this step!!!***
    
    1. Please check if you need to persist any containers into images before the ```rm -rf``` step. There is no recovery of the containers from the previous step.
    1. Deleted the whole devicemapper directory, so docker recreate devicemapper with larger size.
    ```shell
    rm -rf /var/lib/docker/devicemapper/*
    ```

3. Download the Oracel DB installer from [Oracle Technology Network](http://www.oracle.com/technetwork/database/enterprise-edition/downloads/index.html) and put into the dockerfile. The download installer is form Enterprise Edition and Standard Edition. I was not able to use it to build 18c Express. 

4. Get Oracle source for dockerfile and buildscript for Single Instance DB. 

```console
[nzhang@oc6541620688 workspace]$ # find a place to clone oracle docker files and scripts
[nzhang@oc6541620688 workspace]$ mkdir ~/workspace/oracledbdocker
[nzhang@oc6541620688 workspace]$ cd ~/workspace/oracledbdocker/
[nzhang@oc6541620688 oracledbdocker]$ git clone https://github.com/oracle/docker-images.git
Cloning into 'docker-images'...
remote: Enumerating objects: 2, done.
remote: Counting objects: 100% (2/2), done.
remote: Compressing objects: 100% (2/2), done.
remote: Total 9056 (delta 0), reused 0 (delta 0), pack-reused 9054
Receiving objects: 100% (9056/9056), 9.99 MiB | 6.19 MiB/s, done.
Resolving deltas: 100% (5173/5173), done.
```

### Build

1. Use [create-oracle-183-std-image.sh](./create-oracle-183-std-image.sh) to create a 18.2 Standard Edition Database. The script needs one parameter for the path to the OracleDatabase/SingleInstance/dockerfiles directory that downloaded in previous step.

```console
[nzhang@oc6541620688 oracle-db-docker]$ ./create-oracle-183-std-image.sh \
~/workspace/oracledbdocker/docker-images/OracleDatabase/SingleInstance/dockerfiles/

Ignored MD5 checksum.
==========================
DOCKER info:
Containers: 1
 Running: 1
 Paused: 0
 Stopped: 0
Images: 17
Server Version: 18.09.0
Storage Driver: overlay2
...
...
```

1. Use [set-oracle-db-first-time.sh](./set-oracle-db-first-time.sh) to create a Oracle DB container from the image build previously. The script create the /opt/oracle/oradata/ORCLSE2 directory on the host and stores the container Oracle DB's data in that directory. The database is localhst:21521/ORCLSE2. SYS and SYSTEM passwords are "password". database name and passwords can be changed in the [set-oracle-db-first-time.sh](./set-oracle-db-first-time.sh).

```shell
DBNAME=ORCLSE2

DBPASSWD=password

echo "########## Creating this Oracle container's data volume on host ################"

DATAVOL=/opt/oracle/oradata/${DBNAME}
```
