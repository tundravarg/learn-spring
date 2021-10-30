# Run Docker container with DB

1. Run the container: `./run.sh`
2. If it is first run, init DB: `./init.sh`
    * NOTE: If DB exists, it will be recreated, all existing data will be lost.
3. Connect to DB: `./connect.sh`


# Stop container

1. See runnning containers: `docker ps`
2. Remove container: `docker rm -f <container-id>`, where `<container-id>` is the id found in previous step.
