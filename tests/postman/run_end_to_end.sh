#!/bin/sh

# Please, first refer to ./install.sh
sudo newman run BBM.postman_collection.json -e BBM.postman_environment.json --export-environment BBM.postman_collection.json --delay-request 1000 
