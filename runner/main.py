import os
import subprocess
import requests
import re
from queue import Queue
from fastapi import FastAPI
from pydantic import BaseModel
from typing import Literal
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor
from fastapi.responses import JSONResponse

app = FastAPI()

class Submission(BaseModel):
    submission_id: str
    time_limit: int
    memory_limit: int
    lang: Literal["C", "CPP", "JAVA", "PYTHON"]

VOLUME_PATH = os.getenv("VOLUME_PATH")
SCRIPT_PATH = os.getenv("SCRIPT_PATH")
SPRING_ENDPOINT = os.getenv("SPRING_ENDPOINT")
MAX_BOX_ID = 5000
box_pool = Queue()
for i in range(MAX_BOX_ID):
    box_pool.put(i)

MAX_WORKERS = 2

executor = ThreadPoolExecutor(max_workers=MAX_WORKERS)

def extract_number(filename):
    return int(re.search(r'\d+', filename).group())

def run_single_testcase(index, testcase_file, submission, base_path, result_path, script_path):
    
    box_id = box_pool.get()
    
    print(f"box_id: {box_id}")
    print(f"index: {index}")
    print(f"testcase_file: {testcase_file}")
    
    init_script = script_path / "init_execute.sh"
    run_script = script_path / "run_execute.sh"
    # clean_script = script_path / "clean_execute.sh"

    subprocess.run([str(init_script), str(box_id)])
    subprocess.run([
        str(run_script),
        str(box_id),
        str(submission.time_limit),
        str(submission.memory_limit),
        str(base_path),
        str(testcase_file),
        submission.lang,
        str(result_path),
        str(index)
    ])
    box_pool.put(box_id)
    # subprocess.run([str(clean_script), str(current_box_id)])


@app.post("/submission")
def submission_execute(submission: Submission):

    base_path = Path(VOLUME_PATH) / submission.submission_id
    script_path = Path(SCRIPT_PATH)
    testcase_path = base_path / "testcases"
    result_path = base_path / "results"
    
    print(f"base_path: {base_path}")
    print(f"script_path: {script_path}")
    print(f"testcase_path: {testcase_path}")
    print(f"result_path: {result_path}")

    os.makedirs(result_path, exist_ok=True)
    os.chmod(result_path, 0o777)

    # 1. 컴파일
    compile_script = script_path / f"{submission.lang}/compile.sh"
    compile_result = subprocess.run([str(compile_script), str(base_path), str(result_path)])
    
    print(f"compile_script: {compile_script}")
    print(f"compile exitcode: {compile_result.returncode}")

    if compile_result.returncode != 0:
        requests.get(f"{SPRING_ENDPOINT}/submissions/{submission.submission_id}/complete", params={"status": "compile"})
        return JSONResponse(content=None, status_code=200)

    testcases = sorted(os.listdir(testcase_path), key=extract_number)
    print(testcases)
    with ThreadPoolExecutor(max_workers=MAX_WORKERS) as executor:
        for i, testcase_file in enumerate(testcases):
            executor.submit(
              run_single_testcase,
              i,
              testcase_file,
              submission,
              base_path,
              result_path,
              script_path
            )

    requests.get(f"{SPRING_ENDPOINT}/submissions/{submission.submission_id}/complete", params={"status": "end"})
    return JSONResponse(content=None, status_code=200)