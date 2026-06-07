
***<p style="text-align: center;">Login to docker</p>***

Command:
```bash
docker login -u <username>
```

***<p style="text-align: center;">Create the image</p>***

* create the image first
    
    here we are using the docker compose due to scope

command to build the individual image

```bash
 docker compose build <service>
```
command to tag an image

```bash
docker image tag <image_name_created> <reposiotoryname>/<image_name>:<tag_value>
```

add the image in docker 

```bash
 docker push <image_name>
```

port forwarding command 

```bash
kubectl port-forward  service/serviceregistory  -n payment-orch 5004:5004 --address=0.0.0.0 &
```