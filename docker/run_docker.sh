#!/bin/sh
# Параметр для окружения передётся при запуск скрипта, например sh ./run_docker_container.sh prod

docker build -t greeting-docker . && docker run --rm -dp 8080:8080 -e environment=$1 greeting-docker