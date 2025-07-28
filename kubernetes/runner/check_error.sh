#!/bin/bash

# 네임스페이스가 있다면 -n 옵션 추가 필요
NAMESPACE=default  # 필요 시 변경

echo "[+] Runner Pod 로그에서 'properly' 포함된 내용 추출 중..."

# runner로 시작하는 pod 이름만 추출
pods=$(kubectl get pods -n $NAMESPACE --no-headers | awk '/^runner-/ {print $1}')

for pod in $pods; do
    echo -e "\n--- 📄 Logs from $pod ---"
    kubectl logs -n $NAMESPACE "$pod" | grep "properly"
done

