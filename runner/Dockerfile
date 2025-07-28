FROM debian:bookworm-slim AS builder
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        build-essential git ca-certificates pkg-config \
        libcap-dev libsystemd-dev && \
    rm -rf /var/lib/apt/lists/*

RUN git clone --depth=1 https://github.com/ioi/isolate.git /src/isolate && \
    make -C /src/isolate isolate isolate-cg-keeper && \
    make -C /src/isolate install

FROM debian:bookworm-slim
RUN apt-get update && apt-get install -y --no-install-recommends \
    libcap2 libsystemd0 \
    python3 python3-pip \
    openjdk-17-jdk-headless \
    gcc g++ \
  && pip install requests fastapi pydantic uvicorn --break-system-packages \
  && rm -rf /var/lib/apt/lists/*

COPY --from=builder /usr/local/bin/isolate /usr/local/bin/
COPY --from=builder /usr/local/sbin/isolate-cg-keeper /usr/local/sbin/
COPY --from=builder /usr/local/etc/isolate /usr/local/etc/isolate

RUN sed -i '/^cg_root\s*=/d' /usr/local/etc/isolate && \
    echo 'cg_root = /sys/fs/cgroup' >> /usr/local/etc/isolate && \ 
    sed -i 's/^num_boxes *= *.*/num_boxes = 5000/' /usr/local/etc/isolate

RUN mkdir -p /var/local/lib/isolate

RUN setcap cap_sys_chroot+ep /usr/local/bin/isolate || true

RUN mkdir /vol

COPY ./execute /execute
COPY ./main.py /main.py

EXPOSE 5000
CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "5000"]

# docker run --rm -it -p 5000:5000 --privileged --cgroupns=host ioj
