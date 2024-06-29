FROM python:3

WORKDIR /app

USER root

ADD . .

RUN chmod a+x ./main.py
RUN chmod a+x ./start.sh

ENTRYPOINT ["./start.sh"]
