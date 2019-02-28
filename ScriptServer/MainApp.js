const express = require("express");
const shell = require("shelljs");
const fs = require("fs");
const app = express();
const server = require("http").Server(app);
const port = process.env.PORT || 8090;
const bodyParser = require("body-parser");
var AppStatus = false;
var database_state = true;

const DOCKER_MASTERDATABASE_NAME = "dbnouveau";
const APP_NAME = "application"
// var multer = require('multer');
// var multerData = multer();

app.use(express.static(__dirname + "/public"));
app.use(express.static(__dirname + "/UI"));
app.use(express.static(__dirname + "/Script"));
app.use(express.static(__dirname + "/Output"));
app.use(express.static(__dirname + "../Outputs"));


app.use(bodyParser.urlencoded({ extended: false }));

app.use(bodyParser.json());

app.use(function (req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  res.header("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
  next();
});

server.listen(port, function () { console.log("Server launched on 8090") });

/*
  ---------------------------------------
  --         Main-related           -
  ---------------------------------------
*/

app.route("/index").get(function (req, res) {
  res.sendFile("/UI/admin.html");
});

app.route("/API/Database").get(function (req, res) {
  res.sendFile("/UI/DatabaseStatus.html", { root: "." });
});

app.route("/API/EXECUTE").get(function (req, res) {
  console.log(req.params.name)
  console.log(shell.exec('/' + req.params.name));
  res.sendFile("/UI/DatabaseStatus.html", { root: "." });
});

app.route("/SCRIPT").get(function (req, res) {
  //run command
});


/*
  ---------------------------------------
  --         Database-related           -
  ---------------------------------------
*/
app.route("/DATABASE/KILL").post(function () {
  shell.exec("sh ./scripts/killDatabase.sh " + DOCKER_MASTERDATABASE_NAME + " > ./logs/database-related.log");
  // database_state = false;
  // shell.exec("docker-compose kill " + DOCKER_MASTERDATABASE_NAME)
})

app.route("/DATABASE/START").post(function () {
  shell.exec("sh ./scripts/restartDatabase.sh " + DOCKER_MASTERDATABASE_NAME + " > ./logs/database-related.log");
  // database_state = true;
  // shell.exec("docker-compose start " + DOCKER_MASTERDATABASE_NAME)
})

// TODO
app.route("/DATABASE/CHECK").post(function () {
  database_state = shell.exec("sh ./scripts/checkDatabase.sh > ./logs/database-related.log").trim();
  // var elem = document.getElementById('CheckDatabase');
  // elem.style.color = "red";
})

app.route("/DATABASE/CLEAN").post(function () {
  shell.exec("sh ./scripts/cleanDatabase.sh > ./logs/database-related.log");
  // database_state = true;
  // shell.exec("docker-compose start " + DOCKER_MASTERDATABASE_NAME)
})

app.route("/DATABASE/PRERUN").get(function () {
  displayOutput("./logs/prerun.log");
  shell.exec("sh ../src/main/resources/requests/pre-run/pre_run_Alice_Bob.sh > ./logs/prerun.log");
  shell.exec("sh ../src/main/resources/requests/pre-run/pre_run_last_step.sh >> ./logs/prerun.log");
})

app.route("/DATABASE/RUN").get(function () {
  displayOutput("./logs/run.log");
  shell.exec("sh ../src/main/resources/requests/run/run.sh 2 > ./logs/run.log", { async: true });
})

app.route("/DATABASE/RUN2").get(function () {
  displayOutput("./logs/run.log");
  shell.exec("sh ../src/main/resources/requests/run/run2.sh 2 > ./logs/run.log", { async: true });
})

app.route("/DATABASE/RUN22").get(function () {
  shell.exec("sh ../src/main/resources/requests/run/run22.sh 2 >> ./logs/run.log", { async: true });
})


/*
  ---------------------------------------
  --         Server-related           -
  ---------------------------------------
*/
app.route("/SERVER/KILL").post(function () {
  shell.exec("docker-compose kill " + APP_NAME)
})

app.route("/SERVER/START").post(function () {
  shell.exec("docker-compose start " + APP_NAME)
})



/*
  ---------------------------------------
  --         Others things              -
  ---------------------------------------
*/
app.route("/BASH/DISPLAY").post(function (req, res) {
  shell.exec('gnome-terminal -e "tail -f' + req.params.name + '"')
})

function displayOutput(outputFile) {
  var x = shell.exec("echo $PWD");
  x = x.substr(0, x.length - 1)
  if (process.platform != "darwin") {
    shell.exec("gnome-terminal -e 'bash -c \"tail -f -n 200 " + outputFile + ";read\"'", { async: true });
  } else {
    shell.exec("osascript -e 'tell app \"Terminal\" \n do script \" cd " + x + " && tail -f -n 200 " + outputFile + " \" \n end tell\'", { async: true })
  }
}

async function x(req, res) {
  if (!AppStatus) {
    // console.log(shell.exec("ls"))
    shell.exec('cd .. && sudo docker-compose up > ./ScriptServer/logs/server.log', { async: true }).stdout.pipe(res);
    displayOutput("./logs/server.log");
  } else {
    shell.exec("docker kill $(docker ps -q)");
    shell.exec("cd .. && sudo docker-compose up");
  }
};

app.route("/MAIN").get(x);