#!/bin/sh

echo "---- [WARNING] Requires Linux ---- ";
echo "---- Postman end-to-end testing. Pre-installation ----";
echo ">> Installing npm...";
sleep 1;
sudo apt-get install npm;
sleep 3;
echo ">> Done."
sleep 2.
echo ">> Installing newman...";
sudo /usr/bin/npm install -g newman
sleep 3;
echo ">> Done."
sleep 1;
echo ">> Launch your postman collection with \"sudo newman run mycollection.json\" or \"sh ./run_end_to_end.sh\"."
sleep 1;
echo ">> If you encounter difficulties. Please execute the following command and re-launch the script:"
sleep 1;
echo ">> \"sudo ln -s /usr/bin/nodejs /usr/bin/node\"."
echo "-----------------"
