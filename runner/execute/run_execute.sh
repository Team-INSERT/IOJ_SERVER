#!/bin/bash

BOX_ID=$1
TIME_LIMIT=$2
MEMORY_LIMIT=$3
BASE_PATH=$4
INPUT_FILENAME=$5
LANG=$6
RESULT_PATH=$7
INDEX=$8

TESTCASE_PATH="${BASE_PATH}/testcases"
ISOLATE_PATH="/var/local/lib/isolate/${BOX_ID}/box"

if [ "$LANG" = "JAVA" ]; then
  cp "${BASE_PATH}"/main.class $ISOLATE_PATH
else
  cp "${BASE_PATH}/main" $ISOLATE_PATH
fi

ISOLATE_CMD=(
  isolate --cg --wait \
    -b $BOX_ID \
    --run \
    --chdir=. \
    --stdin="/testcases/${INPUT_FILENAME}" \
    --meta="${RESULT_PATH}/meta${INDEX}.txt" \
    --stdout="/results/stdout${INDEX}.txt" \
    --stderr="/results/stderr${INDEX}.txt" \
    --cg-mem=$MEMORY_LIMIT \
    --time $TIME_LIMIT \
    --wall-time=$TIME_LIMIT \
    --stack 1048576 \
    --fsize 1048576 \
    --processes=32 \
    --dir /script=/execute/$LANG:rw \
    --dir /testcases=$TESTCASE_PATH:rw \
    --dir /results=$RESULT_PATH:rw \
    -- /bin/sh /script/run.sh
)

if [ -n "$INPUT_FILENAME" ] && [ -f "${TESTCASE_PATH}/${INPUT_FILENAME}" ]; then
  ISOLATE_CMD+=(--stdin="/testcases/${INPUT_FILENAME}")
fi

"${ISOLATE_CMD[@]}"