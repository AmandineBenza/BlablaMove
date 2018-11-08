-- Postman-related infos --

[Newman launch]
Sadly, "newman" cannot yet update environment at runtime, as Postman do. We chose to let the scripts but as success requires environment update (passing offerID and transactionID to delayed requests), execution will procure errors. 

link: https://github.com/postmanlabs/newman/issues/1679

If you still want to try to launch our end-to-end tests on command line, please:
1. launch "install_end_to_end.sh"
2. launch "run_end_to_end.sh".

[Postman Launch]

Requirements:
- Postman software: https://www.getpostman.com/

1. Launch Postman.
2. import "BBM.postman_collection.json".
3. On the Postman UI, please check that you have the BBM environment.
3-bis. If not, import "BBM.postman_environment.json".
4. Run (delay=1000ms, Keep variable values=true) !

[Look to RESULTS]

Requirements:
- Postman software: https://www.getpostman.com/

1. Launch Postman.
2. Go to Postman Runner.
3. Import Test Run "BBM.postman_test_run.json".
4. Appreciate the green color :)
