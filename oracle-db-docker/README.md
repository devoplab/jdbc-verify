# Oracel Database Docker Container

## Build Image

### Prepare

Oracel installer requires 18G of free space to install and fails if the installation is on a default 10G container. 

I increased the container size in following steps:

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
    1. I deleted the whole devicemapper directory, so docker can recreate its devicemapper with larger size.
    ```shell
    rm -rf /var/lib/docker/devicemapper/*
    ```
    2. Please check if you need to persist any containers into images before the ```rm -rf``` step. There is no recovery of the containers from the previous step.

3. Download the Oracel DB installer from [Oracle Technology Network](http://www.oracle.com/technetwork/database/enterprise-edition/downloads/index.html) and put into the dockerfile. The download installer is form Enterprise Edition and Standard Edition. I was not able to use it to build 18c Express. 