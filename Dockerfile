FROM ubuntu:latest
LABEL authors="saalc"

ENTRYPOINT ["top", "-b"]