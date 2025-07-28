#!/bin/bash

BOX_ID=$1

isolate --cg --wait -b $BOX_ID --cleanup
